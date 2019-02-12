package com.a2k.languagespeedup.modelviews;


import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.a2k.languagespeedup.Card;
import com.a2k.languagespeedup.activities.Study;
import com.a2k.languagespeedup.database.AppRepository;
import com.a2k.languagespeedup.database.entities.ForeignPhrase;
import com.a2k.languagespeedup.database.entities.Sentence;

import java.util.List;

public class StudyVM extends AndroidViewModel {

    private static final String TAG = "StudyVMActivityDD";
    private AppRepository repository;
    private LiveData<List<ForeignPhrase>> foreignPhrasesForSelectedDeck;
    private LiveData<List<Sentence>> sentencesForSelectedDeck;
    private LiveData<List<Card>> cards;

    public StudyVM(@NonNull Application application) {
        super(application);
        repository = new AppRepository(application);
    }

    public void initWithDeckId(final long deckId) {
        if(foreignPhrasesForSelectedDeck != null)
            return;

        cards = repository.getCards(deckId);

//        foreignPhrasesForSelectedDeck = repository.getForeignPhrasesForDeckId(deckId);
    }

    public LiveData<List<Card>> getCardsForSelectedDeck() {
        return cards;
    }

}

