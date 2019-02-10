package com.a2k.languagespeedup.modelviews;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;


import com.a2k.languagespeedup.database.AppRepository;
import com.a2k.languagespeedup.database.entities.Deck;

import java.util.List;

public class MainMenuMV extends AndroidViewModel {

    //--------------------------------------------------------------------------------
    //-----------------------------PRIVATE-MEMBERS------------------------------------
    //--------------------------------------------------------------------------------

    private LiveData<List<Deck>> allDecks;

    //--------------------------------------------------------------------------------
    //------------------------------PUBLIC-METHODS------------------------------------
    //--------------------------------------------------------------------------------

    public MainMenuMV(@NonNull Application application) {
        super(application);
        AppRepository repository = new AppRepository(application);
        allDecks = repository.getAllDecks();
    }

    public LiveData<List<Deck>> getAllDecks() {
        return allDecks;
    }

}

