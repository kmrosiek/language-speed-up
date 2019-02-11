package com.a2k.languagespeedup.modelviews;


import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.support.annotation.NonNull;


import com.a2k.languagespeedup.database.AppRepository;
import com.a2k.languagespeedup.database.entities.ForeignPhrase;

import java.util.List;

public class StudyVM extends AndroidViewModel {

    private AppRepository repository;
    private LiveData<List<ForeignPhrase>> cardsForSelectedDeck;
    private MutableLiveData<Integer> filterFlashcards = new MutableLiveData<Integer>();

    public StudyVM(@NonNull Application application) {
        super(application);
        repository = new AppRepository(application);
        cardsForSelectedDeck = Transformations.switchMap(filterFlashcards,
                id -> repository.getCardsByDeckId(id));
    }

    public void setFilter(Integer filter) { filterFlashcards.setValue(filter);}

    public LiveData<List<ForeignPhrase>> getCardsForSelectedDeck() {
        return cardsForSelectedDeck;
    }

    public void fetchCardsByDeckId(final int id){
        repository.fetchCardsByDeckId(id);
    }

}

