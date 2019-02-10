
package com.a2k.languagespeedup;

import com.a2k.languagespeedup.activities.MainActivity;

import org.junit.Rule;
import org.junit.Test;

import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;


public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void createNewDeck() {
        onView(withId(R.id.add)).perform(click());

        final String NEW_DECK_NAME = "This is a test book";
        onView(withId(R.id.edit_text_deck_name)).perform(typeText(NEW_DECK_NAME),
                closeSoftKeyboard());
        onView(withId(R.id.add_deck_button)).perform(click());

        onView(withText(NEW_DECK_NAME)).check(matches(isDisplayed()));
    }
}
