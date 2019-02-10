package com.a2k.languagespeedup.modelviews;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.a2k.languagespeedup.database.AppRepository;
import com.a2k.languagespeedup.database.entities.Deck;

public class AddNewDeckVM extends AndroidViewModel {

    //--------------------------------------------------------------------------------
    //-----------------------------PRIVATE-MEMBERS------------------------------------
    //--------------------------------------------------------------------------------

    private AppRepository repository;

    //--------------------------------------------------------------------------------
    //------------------------------PUBLIC-METHODS------------------------------------
    //--------------------------------------------------------------------------------

    public AddNewDeckVM(@NonNull Application application) {
        super(application);
        repository = new AppRepository(application);
    }

    public void insert(Deck deck) {
        repository.insertDeck(deck);
    }

}