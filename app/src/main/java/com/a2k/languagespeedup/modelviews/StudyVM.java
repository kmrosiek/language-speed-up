package com.a2k.languagespeedup.modelviews;


import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.a2k.languagespeedup.Card;
import com.a2k.languagespeedup.SentencePair;
import com.a2k.languagespeedup.database.AppRepository;
import com.a2k.languagespeedup.database.entities.ForeignPhrase;

import java.util.ArrayList;
import java.util.List;

public class StudyVM extends AndroidViewModel {

    //--------------------------------------------------------------------------------
    //-----------------------------PRIVATE-MEMBERS------------------------------------
    //--------------------------------------------------------------------------------

    private AppRepository repository;
    private LiveData<List<Card>> cards;
    private int displayedCardPointer = 0;
    private List<SentencePair> displayedSentencePairs = new ArrayList<>();
    private boolean translationsAreDisplayed = false;

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

    public Card getDisplayedCard() {
        if(cards.getValue() == null)
            return new Card(new ForeignPhrase(1, "NULL"));

        return cards.getValue().get(displayedCardPointer);
    }

    public boolean isCardsCollectionEmpty() {
        return cards.getValue() == null || cards.getValue().size() == 0;
    }

    public int getCardsSize() {
        if (cards.getValue() == null)
            return 0;

        return cards.getValue().size();
    }

    public List<SentencePair> getDisplayedSentencePairs() {
        return displayedSentencePairs;
    }

    public boolean getTranslationsAreDisplayed() {
        return translationsAreDisplayed;
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

    public void toggleShouldTranslationsBeDisplayed() {
        translationsAreDisplayed = !translationsAreDisplayed;
    }

}

