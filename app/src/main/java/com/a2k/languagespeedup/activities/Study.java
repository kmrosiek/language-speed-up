package com.a2k.languagespeedup.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.a2k.languagespeedup.Card;
import com.a2k.languagespeedup.R;
import com.a2k.languagespeedup.SentencePair;
import com.a2k.languagespeedup.adapters.StudySentencesListView;
import com.a2k.languagespeedup.modelviews.StudyVM;

import java.util.ArrayList;
import java.util.List;

public class Study extends AppCompatActivity {

    //--------------------------------------------------------------------------------
    //-----------------------------PRIVATE-MEMBERS------------------------------------
    //--------------------------------------------------------------------------------

    private static final String TAG = "StudyActivityDD";
    private StudySentencesListView sentencesListViewAdapter;
    private ArrayAdapter<String> meaningsListViewAdapter;
    private List<Card> cards = new ArrayList<>();
    private List<SentencePair> displayedSentencePairs = new ArrayList<>();
    private ListView meaningsListView;
    private StudyVM studyVM;
    private TextView toolbarTitle;

    //--------------------------------------------------------------------------------
    //-----------------------------PRIVATE-METHODS------------------------------------
    //--------------------------------------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study);

        setUpToolbar();

        final long deckId = retrieveDeckIdFromMainActivity();

        initSentencesListViewAdapter();

        initSentencesListViewOnItemClickListener();

        initMeaningsListViewAdapter();

        initMeaningsListViewOnItemClickListener();

        initViewModel(deckId);

        initTranslateFloatingButton();

        initForwardFloatingButton();

        initBackwardFloatingButton();

        initForeignPhraseButton();
    }

    private long retrieveDeckIdFromMainActivity() {
        Intent mainActivity = getIntent();
        final long DECK_ID_NOT_PROVIDED = -1;
        final long deckId = mainActivity.getLongExtra(getString(R.string.EXTRA_DECK_ID), DECK_ID_NOT_PROVIDED);
        Log.d(TAG, "retrieveDeckIdFromMainActivity: deckid:" + deckId);
        if (deckId == DECK_ID_NOT_PROVIDED) {
            Toast.makeText(this, "Deck Id not provided!", Toast.LENGTH_LONG).show();
            Log.e(TAG, "Starting study activity, deck id was not provided.");
        } else {
            Log.d(TAG, "Starting study activity, deck id: " + Long.toString((deckId)));
        }

        return deckId;
    }

    private void initViewModel(final long deckId) {
        studyVM = ViewModelProviders.of(this).get(StudyVM.class);
        studyVM.initWithDeckId(deckId);
        studyVM.getCardsForSelectedDeck().observe(this, cards -> {
            this.cards = cards;
            //todo what happens when list cards is empty?
            renderContent();

        });
    }

    private void initSentencesListViewAdapter() {
        final ListView sentencesListView = findViewById(R.id.study_sentences_list);
        sentencesListViewAdapter = new StudySentencesListView(this, displayedSentencePairs);
        sentencesListView.setAdapter(sentencesListViewAdapter);
    }

    private void initSentencesListViewOnItemClickListener() {
        final ListView sentencesListView = findViewById(R.id.study_sentences_list);
        sentencesListView.setOnItemClickListener((adapterView, view, i, l) -> {
            SentencePair textFromClicked = (SentencePair) adapterView.getItemAtPosition(i);
            Toast.makeText(Study.this, textFromClicked.getForeign(), Toast.LENGTH_SHORT).show();
        });

    }

    private void initMeaningsListViewOnItemClickListener() {
        final ListView meaningsListView = findViewById(R.id.study_meanings_list);
        meaningsListView.setOnItemClickListener((adapterView, view, i, l) -> {
            String textFromClicked = (String) adapterView.getItemAtPosition(i);
            Toast.makeText(Study.this, textFromClicked, Toast.LENGTH_SHORT).show();
        });

    }

    private void initMeaningsListViewAdapter() {
        meaningsListView = findViewById(R.id.study_meanings_list);
        meaningsListViewAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1);
        meaningsListView.setAdapter(meaningsListViewAdapter);
        meaningsListView.setVisibility(View.INVISIBLE);
    }

    private void renderForeignPhrase(final String foreignPhrase) {
            final Button foreignPhraseButton = findViewById(R.id.study_foreign_phrase_button);
            foreignPhraseButton.setText(foreignPhrase);
    }

    private void renderSentences(List<SentencePair> sentencePairs) {
        sentencesListViewAdapter.setSentencePairs(sentencePairs);
    }

    private void renderMeanings(List<String> meanings) {
        meaningsListViewAdapter.clear();
        meaningsListViewAdapter.addAll(meanings);
    }

    private void renderContent() {
        if(cards.size() == 0) {
            renderNoCardsInfo();
            hideFloatingButtons();
        } else {
            renderSentences(cards.get(studyVM.getDisplayedCardPointer()).getSentences());
            renderMeanings(cards.get(studyVM.getDisplayedCardPointer()).getMeanings());
            renderForeignPhrase(cards.get(studyVM.getDisplayedCardPointer()).getForeignPhrase());
            renderActivityTitle();
        }
    }

    private void renderNoCardsInfo() {
        TextView noCardsInfo = findViewById(R.id.study_no_cards_info);
        noCardsInfo.setVisibility(View.VISIBLE);
    }

    private void renderActivityTitle() {
        final String activityTitle = Integer.toString(studyVM.getDisplayedCardPointer() + 1)
                    + '/' + Integer.toString(cards.size());
        toolbarTitle.setText(activityTitle);
    }

    private void hideFloatingButtons() {
        FloatingActionButton translateButton = findViewById(R.id.study_translate_fab);
        translateButton.setVisibility(View.GONE);
        FloatingActionButton forwardButton = findViewById(R.id.study_forward_button);
        forwardButton.setVisibility(View.GONE);
        FloatingActionButton backwardButton = findViewById(R.id.study_backward_button);
        backwardButton.setVisibility(View.GONE);
    }

    private void initTranslateFloatingButton() {
        FloatingActionButton translateButton = findViewById(R.id.study_translate_fab);
        translateButton.setOnClickListener(
                view -> {
                    sentencesListViewAdapter.toggleTranslationIsDisplayed();
                    sentencesListViewAdapter.notifyDataSetChanged();
                    toggleMeaningsListViewVisibility();
                });
    }

    private void initForwardFloatingButton() {
        FloatingActionButton forwardButton = findViewById(R.id.study_forward_button);
        forwardButton.setOnClickListener(
                view -> {
                    if(cards.size() == 0)
                        return;

                    studyVM.increaseDisplayedCardPointer();
                    renderContent();
                });
    }

    private void initBackwardFloatingButton() {
        FloatingActionButton backwardButton = findViewById(R.id.study_backward_button);
        backwardButton.setOnClickListener(
                view -> {
                    if(cards.size() == 0)
                        return;

                    studyVM.decreaseDisplayedCardPointer();
                    renderContent();
                });
    }

    private void initForeignPhraseButton() {
        final Button foreignPhrase = findViewById(R.id.study_foreign_phrase_button);
        
        foreignPhrase.setOnClickListener(view -> {
            //todo What if there are no cards in cards list.
            Toast.makeText(this, "Sound for word: " +
                    cards.get(studyVM.getDisplayedCardPointer()).getForeignPhrase(), Toast.LENGTH_SHORT).show();
        });
    }

    private void toggleMeaningsListViewVisibility() {
        if(meaningsListView.getVisibility() == View.INVISIBLE)
            meaningsListView.setVisibility(View.VISIBLE);
        else
            meaningsListView.setVisibility(View.INVISIBLE);
    }

    //--------------------------------------------------------------------------------
    //-----------------------------TOOLBAR-METHODS------------------------------------
    //--------------------------------------------------------------------------------

    private void setUpToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null)
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbarTitle = findViewById(R.id.toolbar_title);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add: {
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
