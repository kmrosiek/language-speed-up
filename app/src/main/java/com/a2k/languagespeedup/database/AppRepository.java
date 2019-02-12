package com.a2k.languagespeedup.database;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;
import android.util.Log;
import android.util.LongSparseArray;

import com.a2k.languagespeedup.AsyncResultsForRepository;
import com.a2k.languagespeedup.Card;
import com.a2k.languagespeedup.database.daos.DeckDao;
import com.a2k.languagespeedup.database.daos.ForeignPhraseDao;
import com.a2k.languagespeedup.database.daos.MeaningDao;
import com.a2k.languagespeedup.database.daos.SentenceDao;
import com.a2k.languagespeedup.database.entities.Deck;
import com.a2k.languagespeedup.database.entities.ForeignPhrase;
import com.a2k.languagespeedup.database.entities.Meaning;
import com.a2k.languagespeedup.database.entities.Sentence;

import java.util.ArrayList;
import java.util.List;

public class AppRepository implements AsyncResultsForRepository {

    //--------------------------------------------------------------------------------
    //-----------------------------PRIVATE-MEMBERS------------------------------------
    //--------------------------------------------------------------------------------
    private static final String TAG = "AppRepositorytyDD";

    //-------------------------------DAOs-CLASSES-------------------------------------
    private DeckDao deckDao;
    private ForeignPhraseDao foreignPhraseDao;
    private SentenceDao sentenceDao;
    private MeaningDao meaningDao;

    //-----------------------------------DATA----------------------------------------
    private LiveData<List<Deck>> allDecks;
    private MutableLiveData<List<Card>> lifeCards = new MutableLiveData<>();
    private LongSparseArray<Card> cardsMap = new LongSparseArray<>();

    //--------------------------------------------------------------------------------
    //------------------------------PUBLIC-METHODS------------------------------------
    //--------------------------------------------------------------------------------

    public AppRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        deckDao = database.deckDao();
        foreignPhraseDao = database.foreignTextDao();
        sentenceDao = database.sentenceDao();
        meaningDao = database.meaningDao();
        allDecks = deckDao.getAllDecks();
    }

    public void insertDeck(Deck newDeck) {
        new InsertNewDeckAsyncTask(deckDao).execute(newDeck);
    }

    //---------------------------------GETTERS----------------------------------------

    public LiveData<List<Deck>> getAllDecks(){
        return allDecks;
    }

    public LiveData<List<Card>> getCards(final long deckId) {
        Log.d(TAG, "getCards: ");
        GetForeignPhraseByDeckIdAsyncTask task;
        task = new GetForeignPhraseByDeckIdAsyncTask(foreignPhraseDao);
        task.delegate = this;
        task.execute(deckId);

        return lifeCards;
    }

    //-------------------------------ASYNC-RESPONSES----------------------------------

    @Override
    public void ForeignPhraseSelectionAsyncFinished(List<ForeignPhrase> foreignPhrases) {
        List<Long> foreignPhrasesIds = new ArrayList<>();
        for(ForeignPhrase foreignPhrase : foreignPhrases) {
            foreignPhrasesIds.add(foreignPhrase.getId());
            cardsMap.append(foreignPhrase.getId(), new Card(foreignPhrase));
        }

        final int NUMBER_OF_ASYNC_TASKS = 2;
        selectionAsyncTasksSynchronizer = NUMBER_OF_ASYNC_TASKS;

        GetSentencesByForeignPhraseIdAsyncTask sentencesTask;
        sentencesTask = new GetSentencesByForeignPhraseIdAsyncTask(sentenceDao);
        sentencesTask.delegate = this;
        sentencesTask.execute(foreignPhrasesIds);

        GetMeaningsByForeignPhraseIdAsyncTask meaningsTask;
        meaningsTask = new GetMeaningsByForeignPhraseIdAsyncTask(meaningDao);
        meaningsTask.delegate = this;
        meaningsTask.execute(foreignPhrasesIds);
    }

    @Override
    public void SentencesSelectionAsyncFinished(List<Sentence> sentences) {
        for(Sentence sentence : sentences)
            cardsMap.get(sentence.getForeignPhraseId()).addSentence(sentence);

        updateLifeCardsIfAllAsyncTasksFinished();
    }

    private int selectionAsyncTasksSynchronizer = 0;


    @Override
    public void MeaningsSelectionAsyncFinished(List<Meaning> meanings) {
        for(Meaning meaning : meanings)
            cardsMap.get(meaning.getForeignPhraseId()).addMeaning(meaning.getNativePhrase());
        updateLifeCardsIfAllAsyncTasksFinished();
    }

    //--------------------------------------------------------------------------------
    //------------------------------PRIVATE-METHODS-----------------------------------
    //--------------------------------------------------------------------------------

    private void updateLifeCardsIfAllAsyncTasksFinished() {
        final int ALL_ASYNC_TASKS_FINISHED = 0;
        if(--selectionAsyncTasksSynchronizer == ALL_ASYNC_TASKS_FINISHED) {
            List<Card> cards = convertMapToList(cardsMap);
            lifeCards.setValue(cards);
        }
    }

    //--------------------------------UTILITIES---------------------------------------

    private List<Card> convertMapToList(LongSparseArray<Card> cardsMap) {
        List<Card> cards = new ArrayList<>();
        for(int i = 0; i < cardsMap.size() ; i++) {
            Card card = cardsMap.valueAt(i);
            cards.add(card);
        }
        return cards;
    }

    //--------------------------------------------------------------------------------
    //-------------------------------ASYNC-METHODS------------------------------------
    //--------------------------------------------------------------------------------

    private static class InsertNewDeckAsyncTask extends AsyncTask<Deck, Void, Void> {

        private DeckDao deckDao;

        private InsertNewDeckAsyncTask(DeckDao deckDao) {
            this.deckDao = deckDao;
        }

        @Override
        protected Void doInBackground(Deck... newDecks) {
            deckDao.insert(newDecks[0]);
            return null;
        }
    }

    private static class GetSentencesByForeignPhraseIdAsyncTask
            extends AsyncTask<List<Long>, Void, List<Sentence>> {

        private SentenceDao sentenceDao;
        private AppRepository delegate = null;

        @Override
        protected void onPostExecute(List<Sentence> sentences) {
            delegate.SentencesSelectionAsyncFinished(sentences);
        }

        private GetSentencesByForeignPhraseIdAsyncTask(SentenceDao sentenceDao) {
            this.sentenceDao = sentenceDao;
        }

        @SafeVarargs
        @Override
        protected final List<Sentence> doInBackground(List<Long>... foreignPhrasesIds) {
            return sentenceDao.getSentencesByForeignPhraseId(foreignPhrasesIds[0]);
        }
    }

    private static class GetForeignPhraseByDeckIdAsyncTask
            extends AsyncTask<Long, Void, List<ForeignPhrase>> {

        private ForeignPhraseDao foreignPhraseDao;
        private AppRepository delegate = null;

        @Override
        protected void onPostExecute(List<ForeignPhrase> foreignPhrases) {
            delegate.ForeignPhraseSelectionAsyncFinished(foreignPhrases);
        }

        private GetForeignPhraseByDeckIdAsyncTask(ForeignPhraseDao foreignPhraseDao) {
            this.foreignPhraseDao = foreignPhraseDao;
        }

        @Override
        protected List<ForeignPhrase> doInBackground(Long... decksIds) {
            return foreignPhraseDao.getForeignPhrasesByDeckId(decksIds[0]);
        }
    }

    private static class GetMeaningsByForeignPhraseIdAsyncTask
            extends AsyncTask<List<Long>, Void, List<Meaning>> {

        private MeaningDao meaningDao;
        private AppRepository delegate = null;

        @Override
        protected void onPostExecute(List<Meaning> meanings) {
            delegate.MeaningsSelectionAsyncFinished(meanings);
        }

        private GetMeaningsByForeignPhraseIdAsyncTask(MeaningDao meaningDao) {
            this.meaningDao = meaningDao;
        }

        @SafeVarargs
        @Override
        protected final List<Meaning> doInBackground(List<Long>... foreignPhrasesIds) {
            return meaningDao.getMeaningsByForeignPhraseId(foreignPhrasesIds[0]);
        }
    }
}
