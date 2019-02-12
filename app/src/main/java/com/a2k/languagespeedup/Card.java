package com.a2k.languagespeedup;

import com.a2k.languagespeedup.database.entities.ForeignPhrase;
import com.a2k.languagespeedup.database.entities.Sentence;

import java.util.ArrayList;
import java.util.List;

public class Card {

    private long id;
    private List<Sentence> sentences = new ArrayList<>();
    private ForeignPhrase foreignPhrase;

    public String print() {

        String cardDescription = "My ID: " + id + ". I say: ";

        for(Sentence sentence : sentences)
                cardDescription += sentence.getForeignSentence() + " ||| ";

        cardDescription += "\n";
        return cardDescription;
    }

    public Card(final long id, final Sentence sentence){
        this.id = id;
    }

    public Card(final ForeignPhrase foreignPhrase) {
        this.foreignPhrase = foreignPhrase;
        id = foreignPhrase.getId();
    }

    public void addSentence(final Sentence sentence) {
        sentences.add(sentence);
    }

    public List<SentencePair> getSentences() {
        List<SentencePair> sentencePairs = new ArrayList<>();
        for(Sentence sentence : sentences) {
            sentencePairs.add(new SentencePair(sentence.getForeignSentence(),
                    sentence.getNativeSentence()));
        }

        return sentencePairs;
    }

    public List<String> getMeanings() {
        List<String> meanings = new ArrayList<>();
        meanings.add("One meaning");
        meanings.add("another meaning");
        meanings.add("one more");

        return meanings;
    }

    public String getForeignPhrase() {
        return foreignPhrase.getForeignText();
    }
}
