package com.example.languagespeedup.activities;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.languagespeedup.R;
import com.example.languagespeedup.adapters.MainMenuRecyclerView;
import com.example.languagespeedup.modelviews.MainMenuMV;

public class MainActivity extends AppCompatActivity {

    //--------------------------------------------------------------------------------
    //-----------------------------PRIVATE-MEMBERS------------------------------------
    //--------------------------------------------------------------------------------

    private static final String TAG = "MainActivityDD";


    //--------------------------------------------------------------------------------
    //-----------------------------PRIVATE-METHODS------------------------------------
    //--------------------------------------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpToolbar();

        setupRecyclerViewAndViewModel();

    }

    private void setupRecyclerViewAndViewModel() {
        RecyclerView recyclerView = findViewById(R.id.main_menu_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final MainMenuRecyclerView adapter = new MainMenuRecyclerView();
        recyclerView.setAdapter(adapter);

        final MainMenuMV decksMainMenuMV = ViewModelProviders.of(this).get(MainMenuMV.class);
        decksMainMenuMV.getAllDecks().observe(this, adapter::setDecks);

        adapter.setOnDeckClickListener(deck -> {
//                switchToStudyActivity(deck.getId());
        });
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
