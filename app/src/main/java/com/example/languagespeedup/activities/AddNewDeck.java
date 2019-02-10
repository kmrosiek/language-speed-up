package com.example.languagespeedup.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.languagespeedup.R;
import com.example.languagespeedup.database.entities.Deck;
import com.example.languagespeedup.modelviews.AddNewDeckVM;

public class AddNewDeck extends AppCompatActivity {

    private static final String TAG = "AddDecktyDD";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_deck);

        setUpToolbar();
        setupAddButton();
    }

    private void setupAddButton() {

        Button addNewDeckButton = findViewById(R.id.add_deck_button);

        addNewDeckButton.setOnClickListener(v -> addDeck());
    }

    private void addDeck() {
        EditText deckNameEditText = findViewById(R.id.edit_text_deck_name);
        String deckName = deckNameEditText.getText().toString();

        if (deckName.trim().isEmpty()) {
            Toast.makeText(this, "Please enter the deck name.", Toast.LENGTH_SHORT).show();
            return;
        }

        AddNewDeckVM addNewDeckVM = ViewModelProviders.of(this).get(AddNewDeckVM.class);
        Deck newDeck = new Deck(deckName);
        addNewDeckVM.insert(newDeck);

        finish();

    }

    //--------------------------------------------------------------------------------
    //-----------------------------TOOLBAR-METHODS------------------------------------
    //--------------------------------------------------------------------------------

    private void setUpToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            setTitle("Enter a new deck name");
        } else
            Log.e(TAG, "setUpToolbar: getSupportActionBar equals NULL");
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
