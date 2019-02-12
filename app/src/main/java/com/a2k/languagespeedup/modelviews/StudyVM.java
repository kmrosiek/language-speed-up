package com.a2k.languagespeedup.modelviews;


import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.a2k.languagespeedup.database.AppRepository;
import com.a2k.languagespeedup.database.entities.ForeignPhrase;

import java.util.List;

public class StudyVM extends AndroidViewModel {

    private AppRepository repository;
    private LiveData<List<ForeignPhrase>> foreignPhrasesForSelectedDeck;

    public StudyVM(@NonNull Application application) {
        super(application);
        repository = new AppRepository(application);
    }

    public void initWithDeckId(final int deckId) {
        if(foreignPhrasesForSelectedDeck != null)
            return;

        foreignPhrasesForSelectedDeck = repository.getForeignPhrasesForDeckId(deckId);
    }

    public LiveData<List<ForeignPhrase>> getForeignPhrasesForSelectedDeck() {
        return foreignPhrasesForSelectedDeck;
    }

}

