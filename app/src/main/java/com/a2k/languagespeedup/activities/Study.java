package com.a2k.languagespeedup.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

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
    private int deckId;

    //--------------------------------------------------------------------------------
    //-----------------------------PRIVATE-METHODS------------------------------------
    //--------------------------------------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study);

        setUpToolbar();

        retrieveDeckIdFromMainActivity();

        setupViewModel();

        setupSentencesListView();
    }

    private void retrieveDeckIdFromMainActivity() {
        Intent mainActivity = getIntent();
        final int DECK_ID_NOT_PROVIDED = -1;
        deckId = mainActivity.getIntExtra(getString(R.string.EXTRA_DECK_ID), DECK_ID_NOT_PROVIDED);
        if (deckId == DECK_ID_NOT_PROVIDED) {
            Toast.makeText(this, "List not provided!", Toast.LENGTH_LONG).show();
            Log.e(TAG, "Starting study activity, list id was not provided.");
        } else {
            Log.d(TAG, "Starting study activity, list id: " + Integer.toString((deckId)));
        }
    }

    private void setupViewModel() {
        final StudyVM studyVM = ViewModelProviders.of(this).get(StudyVM.class);
        studyVM.setFilter(deckId);
    }

    private void setupSentencesListView() {
        ListView sentencesListView = findViewById(R.id.study_sentences_list);

        List<SentencePair> sentences = new ArrayList<>();
        sentences.add(new SentencePair("Find 10 mistakes in the sentences and correct them.", "Znajdź 10 błędów w zdaniach i popraw je."));
        sentences.add(new SentencePair("He wrote just a few sentences explaining where he was.", "Napisał tylko parę zdań wyjaśniających, gdzie był."));
        sentences.add(new SentencePair("Tomatoes are red.", "Pomidory są czerwone"));

        StudySentencesListView studySentencesListView = new StudySentencesListView(this, sentences);


        sentencesListView.setAdapter(studySentencesListView);
    }

    //--------------------------------------------------------------------------------
    //-----------------------------TOOLBAR-METHODS------------------------------------
    //--------------------------------------------------------------------------------

    private void setUpToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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
                Intent intent = new Intent(this, AddNewDeck.class);
                startActivity(intent);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
