package com.a2k.languagespeedup;

import com.a2k.languagespeedup.activities.MainActivity;

import org.junit.Rule;
import org.junit.Test;

import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

public class StudyActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void deckWithMultipleCards() {
        final String deckName = "Benjamin Franklin";
        onView(withText(deckName)).perform(click());

        String meaning = "zaufanie";
        onView(withText(meaning)).check(doesNotExist());

        String nativeSentence = "To jest kwestia wiary.";
        onView(withText(nativeSentence)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.INVISIBLE)));

        onView(withId(R.id.study_translate_fab)).perform(click());

        onView(withText(meaning)).check(matches(isDisplayed()));
        onView(withText(nativeSentence)).check(matches(isDisplayed()));

        onView(withId(R.id.study_forward_button)).perform(click());
        onView(withId(R.id.study_forward_button)).perform(click());
        onView(withId(R.id.study_forward_button)).perform(click());

        meaning = "samochód";
        onView(withText(meaning)).check(matches(isDisplayed()));
        nativeSentence = "On został potrącony przez samochód i jest teraz w szpitalu.";
        onView(withText(nativeSentence)).check(matches(isDisplayed()));

        onView(withId(R.id.study_backward_button)).perform(click());
        onView(withId(R.id.study_backward_button)).perform(click());
        onView(withId(R.id.study_backward_button)).perform(click());

        final String foreignPhrase = "Faith";
        onView(withText(foreignPhrase)).check(matches(isDisplayed()));

    }

    @Test
    public void deckWithSingleForeignPhrase() {
        final String deckName = "Single Foreign Phrase";
        onView(withText(deckName)).perform(click());

        final String meaning = "basen";
        onView(withText(meaning)).check(doesNotExist());
        final String nativeSentence = "Basen jest otoczony płotem.";
        onView(withText(nativeSentence)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.INVISIBLE)));
        final String foreignSentence = "The swimming pool is surrounded by a fence.";
        onView(withText(foreignSentence)).check(matches(isDisplayed()));
        final String foreignPhrase = "pool";
        onView(withText(foreignPhrase)).check(matches(isDisplayed()));

        onView(withId(R.id.study_backward_button)).perform(click());
        onView(withId(R.id.study_forward_button)).perform(click());
        onView(withId(R.id.study_forward_button)).perform(click());
        onView(withId(R.id.study_translate_fab)).perform(click());

        onView(withText(meaning)).check(matches(isDisplayed()));
        onView(withText(nativeSentence)).check(matches(isDisplayed()));
        onView(withText(foreignSentence)).check(matches(isDisplayed()));
        onView(withText(foreignPhrase)).check(matches(isDisplayed()));

        onView(withId(R.id.toolbar_title)).check(matches(withText("1/1")));
    }

    @Test
    public void emptyDeck() {
        final String deckName = "Empty Deck";
        onView(withText(deckName)).perform(click());

        onView(withId(R.id.study_no_cards_info)).check(matches(isDisplayed()));
    }
}
