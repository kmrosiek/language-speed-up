package com.a2k.languagespeedup.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.a2k.languagespeedup.database.daos.ForeignPhraseDao;
import com.a2k.languagespeedup.database.daos.DeckDao;
import com.a2k.languagespeedup.database.daos.SentenceDao;
import com.a2k.languagespeedup.database.entities.ForeignPhrase;
import com.a2k.languagespeedup.database.entities.Deck;
import com.a2k.languagespeedup.database.entities.Sentence;


@Database(entities = {Deck.class, ForeignPhrase.class, Sentence.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract DeckDao deckDao();
    public abstract ForeignPhraseDao foreignTextDao();
    public abstract SentenceDao sentenceDao();

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

        private PopulateDbAsyncTask(AppDatabase db) {
            deckDao = db.deckDao();
            foreignPhraseDao = db.foreignTextDao();
            sentenceDao = db.sentenceDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            deckDao.insert(new Deck("Benjamin Franklin", Deck.ENGLISH));
            deckDao.insert(new Deck("Becoming", Deck.ENGLISH));
            deckDao.insert(new Deck("Amazing story of our country", Deck.ENGLISH));
            foreignPhraseDao.insert(new ForeignPhrase(1, "Faith"));
            foreignPhraseDao.insert(new ForeignPhrase(1, "Robot"));
            foreignPhraseDao.insert(new ForeignPhrase(1, "Car"));
            sentenceDao.insert((new Sentence(1 , "I have faith in her to do the right thing.",
                    "Ufam, że ona zrobi to co właściwe.")));
            sentenceDao.insert((new Sentence(1 , "It's a matter of faith.",
                    "To jest kwestia wiary")));
            sentenceDao.insert((new Sentence(1 , "Have faith, my child.",
                    "Miej ufność, moje dziecko.")));
            sentenceDao.insert((new Sentence(2 , "The robot used to make me a really great breakfast.",
                    "Ten automat robił dla mnie pyszne śniadania.")));
            sentenceDao.insert((new Sentence(2 , "It is a robot and he will do what I say",
                    "To jest robot i zrobi, co mu każę")));
            sentenceDao.insert((new Sentence(3 , "He got struck by a car and is in hospital now.",
                    "On został potrącony przez samochód i jest teraz w szpitalu.")));
            return null;
        }
    }



}

