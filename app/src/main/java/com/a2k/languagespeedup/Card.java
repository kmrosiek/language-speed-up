package com.a2k.languagespeedup;

import com.a2k.languagespeedup.database.entities.ForeignPhrase;
import com.a2k.languagespeedup.database.entities.Sentence;

import java.util.ArrayList;
import java.util.List;

public class Card {

    //--------------------------------------------------------------------------------
    //-----------------------------PRIVATE-MEMBERS------------------------------------
    //--------------------------------------------------------------------------------

    private List<Sentence> sentences = new ArrayList<>();
    private List<String> meanings = new ArrayList<>();
    private ForeignPhrase foreignPhrase;

    //--------------------------------------------------------------------------------
    //------------------------------PUBLIC-METHODS------------------------------------
    //--------------------------------------------------------------------------------

    public Card(final ForeignPhrase foreignPhrase) {
        this.foreignPhrase = foreignPhrase;
    }

    //--------------------------------MODIFIERS---------------------------------------
    public void addSentence(final Sentence sentence) {
        sentences.add(sentence);
    }

    public void addMeaning(final String meaning) {
        meanings.add(meaning);
    }

    //---------------------------------GETTERS----------------------------------------
    public List<SentencePair> getSentences() {
        List<SentencePair> sentencePairs = new ArrayList<>();
        for(Sentence sentence : sentences) {
            sentencePairs.add(new SentencePair(sentence.getForeignSentence(),
                    sentence.getNativeSentence()));
        }
        return sentencePairs;
    }

    public List<String> getMeanings() {
        return meanings;
    }

    public String getForeignPhrase() {
        return foreignPhrase.getForeignText();
    }
}
