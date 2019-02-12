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
import com.a2k.languagespeedup.database.daos.SentenceDao;
import com.a2k.languagespeedup.database.entities.Deck;
import com.a2k.languagespeedup.database.entities.ForeignPhrase;
import com.a2k.languagespeedup.database.entities.Sentence;

import java.util.ArrayList;
import java.util.List;

public class AppRepository implements AsyncResultsForRepository {

    //--------------------------------------------------------------------------------
    //-----------------------------PRIVATE-MEMBERS------------------------------------
    //--------------------------------------------------------------------------------
    private static final String TAG = "AppRepositorytyDD";

    private DeckDao deckDao;
    private ForeignPhraseDao foreignPhraseDao;
    private SentenceDao sentenceDao;
    private LiveData<List<Deck>> allDecks;
    private List<Card> cards = new ArrayList<>();
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
        allDecks = deckDao.getAllDecks();
    }

    public LiveData<List<Deck>> getAllDecks(){
        return allDecks;
    }

    public void insertDeck(Deck newDeck) {
        new InsertNewDeckAsyncTask(deckDao).execute(newDeck);
    }

//    public LiveData<List<ForeignPhrase>> getForeignPhrasesForDeckId(Long deckId) {
//        LiveData<List<ForeignPhrase>> foreignPhrases = foreignPhraseDao.getForeignPhrasesByDeckId(deckId);
//        Log.d(TAG, "getForeignPhrasesForDeckId: " + foreignPhrases.toString());
//
//        if(sentences != null)
//        Log.d(TAG, "getForeignPhrasesForDeckId: " + sentences.toString());
//        else
//            Log.d(TAG, "getForeignPhrasesForDeckId: NULL!!!");
//        cards.add(new Card(1));
//        cards.add(new Card(2));
//        cards.add(new Card(3));
//        lifeCards.setValue(cards);
//        return foreignPhraseDao.getForeignPhrasesByDeckId(deckId);
//    }

    public LiveData<List<Card>> getCards(final long deckId) {
        Log.d(TAG, "getCards: ");
        GetForeignPhraseByDeckIdAsyncTask task = new GetForeignPhraseByDeckIdAsyncTask(foreignPhraseDao);
        task.delegate = this;
        task.execute(deckId);

        return lifeCards;
    }

    public void getSentencesByForeignPhraseId(final long foreignPhraseId) {
        GetSentencesByForeignPhraseIdAsyncTask task = new GetSentencesByForeignPhraseIdAsyncTask(sentenceDao);
        task.delegate = this;
        Log.d(TAG, "getSentencesByForeignPhraseId: ");
//        task.execute(foreignPhraseId);
    }

    @Override
    public void SentencesSelectionAsyncFinished(List<Sentence> sentences) {
        Log.d(TAG, "SentencesSelectionAsyncFinished: no of sentences" + sentences.size());
        for(Sentence sentence : sentences)
           cardsMap.get(sentence.getForeignPhraseId()).addSentence(sentence);



        cards = convertMapToList(cardsMap);
        lifeCards.setValue(cards);
    }

    @Override
    public void ForeignPhraseSelectionAsyncFinished(List<ForeignPhrase> foreignPhrases) {
        Log.d(TAG, "ForeignPhraseSelectionAsyncFinished: list size: " + foreignPhrases.size());
        List<Long> foreignPhrasesIds = new ArrayList<>();
        for(ForeignPhrase foreignPhrase : foreignPhrases) {
            foreignPhrasesIds.add(foreignPhrase.getId());
            cardsMap.append(foreignPhrase.getId(), new Card(foreignPhrase));
        }

        GetSentencesByForeignPhraseIdAsyncTask task = new GetSentencesByForeignPhraseIdAsyncTask(sentenceDao);
        task.delegate = this;
        task.execute(foreignPhrasesIds);
    }

    // utilities---------------------------

    private List<Card> convertMapToList(LongSparseArray<Card> cardsMap) {
        List<Card> cards = new ArrayList<>();
        for(int i = 0; i < cardsMap.size() ; i++) {
            Card card = cardsMap.valueAt(i);
            cards.add(card);
        }
        return cards;
    }

    //--------------------------------------------------------------------------------
    //------------------------------PRIVATE-METHODS-----------------------------------
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

    //--------------------------------------------------------------------------------
    //-------------------------------ASYNC-METHODS------------------------------------
    //--------------------------------------------------------------------------------

    private static class GetSentencesByForeignPhraseIdAsyncTask extends AsyncTask<List<Long>, Void, List<Sentence>> {

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

    private static class GetForeignPhraseByDeckIdAsyncTask extends AsyncTask<Long, Void, List<ForeignPhrase>> {

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
}
