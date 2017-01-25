package com.karcompany.findoffers;

/**
 * Created by pvkarthik on 2017-01-25.
 *
 * Full scenario instrumentation tests for settings screen.
 */

import android.content.Context;
import android.os.SystemClock;
import android.support.design.widget.TextInputLayout;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import com.karcompany.findoffers.views.activities.AppSettingsActivity;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.internal.matchers.TypeSafeMatcher;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.core.AllOf.allOf;

@RunWith(AndroidJUnit4.class)
public class SettingsActivityTest {

	@Rule
	public ActivityTestRule<AppSettingsActivity> activityRule = new ActivityTestRule<>(AppSettingsActivity.class);

	@Test
	public void testSaveAppIdEmptyScenario() {
		onView(withId(R.id.applicationId)).perform(clearText());
		onView(withId(R.id.saveBtn)).perform(click());
		SystemClock.sleep(3000);
		Context context = getContext();
		onView(allOf(withId(R.id.applicationIdLyt), isDisplayed()))
				.check(matches(hasTextInputLayoutErrorText(
						context.getString(R.string.field_mandatory))));
	}

	@Test
	public void testSaveUserIdEmptyScenario() {
		onView(withId(R.id.userId)).perform(clearText());
		onView(withId(R.id.saveBtn)).perform(click());
		SystemClock.sleep(3000);
		Context context = getContext();
		onView(allOf(withId(R.id.userIdLyt), isDisplayed()))
				.check(matches(hasTextInputLayoutErrorText(
						context.getString(R.string.field_mandatory))));
	}

	@Test
	public void testSaveTokenEmptyScenario() {
		onView(withId(R.id.token)).perform(clearText());
		onView(withId(R.id.saveBtn)).perform(click());
		SystemClock.sleep(3000);
		Context context = getContext();
		onView(allOf(withId(R.id.tokenLyt), isDisplayed()))
				.check(matches(hasTextInputLayoutErrorText(
						context.getString(R.string.field_mandatory))));
	}

	private static Context getContext() {
		return InstrumentationRegistry.getTargetContext();
	}

	public static Matcher<View> hasTextInputLayoutErrorText(final String expectedErrorText) {
		return new TypeSafeMatcher<View>() {

			@Override
			public boolean matchesSafely(View view) {
				if (!(view instanceof TextInputLayout)) {
					return false;
				}

				CharSequence error = ((TextInputLayout) view).getError();

				if (error == null) {
					return false;
				}

				String hint = error.toString();

				return expectedErrorText.equals(hint);
			}

			@Override
			public void describeTo(Description description) {
			}
		};
	}

}
