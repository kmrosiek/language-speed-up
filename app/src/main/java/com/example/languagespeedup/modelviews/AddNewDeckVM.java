package com.example.languagespeedup.modelviews;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.example.languagespeedup.database.AppRepository;
import com.example.languagespeedup.database.entities.Deck;

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