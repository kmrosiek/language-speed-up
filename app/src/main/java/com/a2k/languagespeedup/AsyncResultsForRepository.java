package com.a2k.languagespeedup;

import com.a2k.languagespeedup.database.entities.ForeignPhrase;
import com.a2k.languagespeedup.database.entities.Meaning;
import com.a2k.languagespeedup.database.entities.Sentence;

import java.util.List;

public interface AsyncResultsForRepository {
    void SentencesSelectionAsyncFinished(List<Sentence> sentences);
    void ForeignPhraseSelectionAsyncFinished(List<ForeignPhrase> foreignPhrases);
    void MeaningsSelectionAsyncFinished(List<Meaning> meanings);
}
