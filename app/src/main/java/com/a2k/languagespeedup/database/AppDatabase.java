package com.a2k.languagespeedup.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.a2k.languagespeedup.database.daos.DeckDao;
import com.a2k.languagespeedup.database.daos.ForeignPhraseDao;
import com.a2k.languagespeedup.database.daos.MeaningDao;
import com.a2k.languagespeedup.database.daos.SentenceDao;
import com.a2k.languagespeedup.database.entities.Deck;
import com.a2k.languagespeedup.database.entities.ForeignPhrase;
import com.a2k.languagespeedup.database.entities.Meaning;
import com.a2k.languagespeedup.database.entities.Sentence;


@Database(entities = {Deck.class, ForeignPhrase.class, Sentence.class, Meaning.class}, version = 1,
        exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract DeckDao deckDao();
    public abstract ForeignPhraseDao foreignTextDao();
    public abstract SentenceDao sentenceDao();
    public abstract MeaningDao meaningDao();

    //--------------------------------------------------------------------------------
    //-----------------------------PRIVATE-MEMBERS------------------------------------
    //--------------------------------------------------------------------------------

    private static final Object LOCK = new Object();
    private static AppDatabase instance;
    private static final String DATABASE_NAME = "dictionary_db";

    //--------------------------------------------------------------------------------
    //------------------------------PUBLIC-METHODS------------------------------------
    //--------------------------------------------------------------------------------

    static AppDatabase getInstance(Context context) {
        if(instance == null) {
            synchronized (LOCK) {
                context.deleteDatabase(DATABASE_NAME);
                instance = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class, DATABASE_NAME).fallbackToDestructiveMigration()
                        .addCallback(roomCallback)
                        .build();
            }
        }

        return instance;
    }

    //--------------------------------------------------------------------------------
    //------------------------------PRIVATE-METHODS-----------------------------------
    //--------------------------------------------------------------------------------

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private DeckDao deckDao;
        private ForeignPhraseDao foreignPhraseDao;
        private SentenceDao sentenceDao;
        private  MeaningDao meaningDao;

        private PopulateDbAsyncTask(AppDatabase db) {
            deckDao = db.deckDao();
            foreignPhraseDao = db.foreignTextDao();
            sentenceDao = db.sentenceDao();
            meaningDao = db.meaningDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            //-----------------------------------DECKS----------------------------------------------
            deckDao.insert(new Deck("Benjamin Franklin", Deck.ENGLISH));
            deckDao.insert(new Deck("Single Foreign Phrase", Deck.ENGLISH));
            deckDao.insert(new Deck("Single Empty Foreign Phrase", Deck.ENGLISH));
            deckDao.insert(new Deck("First and Last Empty", Deck.ENGLISH));
            deckDao.insert(new Deck("Empty Deck", Deck.ENGLISH));

            //-------------------------------FOREIGN-PHRASES----------------------------------------
            foreignPhraseDao.insert(new ForeignPhrase(1, "Faith"));
            foreignPhraseDao.insert(new ForeignPhrase(1, "Robot"));
            foreignPhraseDao.insert(new ForeignPhrase(1, "Car"));
            foreignPhraseDao.insert(new ForeignPhrase(2, "pool"));
            foreignPhraseDao.insert(new ForeignPhrase(3, "emptiness"));
            foreignPhraseDao.insert(new ForeignPhrase(4, "first"));
            foreignPhraseDao.insert(new ForeignPhrase(4, "intermediate"));
            foreignPhraseDao.insert(new ForeignPhrase(4, "last"));

            //---------------------------------SENTENCES--------------------------------------------
            sentenceDao.insert((new Sentence(1 ,
                    "I have faith in her to do the right thing.",
                    "Ufam, że ona zrobi to co właściwe.")));
            sentenceDao.insert((new Sentence(1 ,
                    "It's a matter of faith.",
                    "To jest kwestia wiary")));
            sentenceDao.insert((new Sentence(1 ,
                    "Have faith, my child.",
                    "Miej ufność, moje dziecko.")));
            sentenceDao.insert((new Sentence(2 ,
                    "The robot used to make me a really great breakfast.",
                    "Ten automat robił dla mnie pyszne śniadania.")));
            sentenceDao.insert((new Sentence(2 ,
                    "It is a robot and he will do what I say",
                    "To jest robot i zrobi, co mu każę")));
            sentenceDao.insert((new Sentence(3 ,
                    "He got struck by a car and is in hospital now.",
                    "On został potrącony przez samochód i jest teraz w szpitalu.")));
            sentenceDao.insert((new Sentence(4 ,
                    "He got struck by a car and is in hospital now.",
                    "On został potrącony przez samochód i jest teraz w szpitalu.")));
            sentenceDao.insert((new Sentence(7 ,
                    "Here we can compare an intermediate with a finished product.",
                    "Tutaj możemy porównać produkt pośredni z produktem końcowym.")));

            //---------------------------------MEANINGS--------------------------------------------
            meaningDao.insert(new Meaning(1, "ufność"));
            meaningDao.insert(new Meaning(1, "zaufanie"));
            meaningDao.insert(new Meaning(1, "wiara"));
            meaningDao.insert(new Meaning(2, "automat"));
            meaningDao.insert(new Meaning(2, "robot"));
            meaningDao.insert(new Meaning(3, "samochód"));
            meaningDao.insert(new Meaning(4, "basen"));
            meaningDao.insert(new Meaning(7, "mediator"));
            meaningDao.insert(new Meaning(7, "produkt pośredni"));

            return null;
        }
    }



}

