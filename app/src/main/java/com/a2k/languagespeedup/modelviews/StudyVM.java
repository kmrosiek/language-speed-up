package com.a2k.languagespeedup.modelviews;


import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.a2k.languagespeedup.Card;
import com.a2k.languagespeedup.database.AppRepository;

import java.util.List;

public class StudyVM extends AndroidViewModel {

    //--------------------------------------------------------------------------------
    //-----------------------------PRIVATE-MEMBERS------------------------------------
    //--------------------------------------------------------------------------------

    private AppRepository repository;
    private LiveData<List<Card>> cards;
    private int displayedCardPointer = 0;

    //--------------------------------------------------------------------------------
    //------------------------------PUBLIC-METHODS------------------------------------
    //--------------------------------------------------------------------------------

    public StudyVM(@NonNull Application application) {
        super(application);
        repository = new AppRepository(application);
    }

    public void initWithDeckId(final long deckId) {
        cards = repository.getCards(deckId);
    }

    public LiveData<List<Card>> getCardsForSelectedDeck() {
        return cards;
    }

    //---------------------------------GETTERS----------------------------------------
    public int getDisplayedCardPointer() {
        return displayedCardPointer;
    }

    //--------------------------------MODIFIERS---------------------------------------
    public void decreaseDisplayedCardPointer() {
        if(--displayedCardPointer < 0)
            displayedCardPointer = 0;
    }

    public void increaseDisplayedCardPointer() {
        if(cards.getValue() == null)
            return;

        if(++displayedCardPointer >= cards.getValue().size())
            displayedCardPointer = cards.getValue().size() - 1;
    }

}

