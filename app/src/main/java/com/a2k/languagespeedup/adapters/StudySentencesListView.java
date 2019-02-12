package com.a2k.languagespeedup.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.a2k.languagespeedup.R;
import com.a2k.languagespeedup.SentencePair;

import java.util.List;

public class StudySentencesListView extends ArrayAdapter<SentencePair> {

    //--------------------------------------------------------------------------------
    //-----------------------------PRIVATE-MEMBERS------------------------------------
    //--------------------------------------------------------------------------------

    private Context context;
    private List<SentencePair> sentencePairs;
    private boolean translationIsDisplayed = false;

    //--------------------------------------------------------------------------------
    //------------------------------PUBLIC-METHODS------------------------------------
    //--------------------------------------------------------------------------------

    public StudySentencesListView(@NonNull Context context, List<SentencePair> sentencePairs) {
        super(context, R.layout.study_sentence_list, sentencePairs);
        this.context = context;
        this.sentencePairs = sentencePairs;
    }

    public void setSentencePairs(List<SentencePair> sentencePairs) {
        this.sentencePairs.clear();
        this.sentencePairs.addAll(sentencePairs);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.study_sentence_list, null);
        TextView foreignSentence = convertView.findViewById(R.id.study_sentence_foreign);
        foreignSentence.setText(sentencePairs.get(position).getForeign());

        TextView nativeSentence = convertView.findViewById(R.id.studY_sentence_native);
        nativeSentence.setText(sentencePairs.get(position).getNative());
        if(!translationIsDisplayed)
            nativeSentence.setVisibility(View.INVISIBLE);
        else
            nativeSentence.setVisibility(View.VISIBLE);

        return convertView;
    }

    public void toggleTranslationIsDisplayed() {
        translationIsDisplayed = !translationIsDisplayed;
    }
}
