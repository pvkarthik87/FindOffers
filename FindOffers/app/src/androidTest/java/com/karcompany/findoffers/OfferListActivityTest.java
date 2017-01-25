package com.karcompany.findoffers;

/**
 * Created by pvkarthik on 2017-01-25.
 *
 * Full scenario instrumentation tests for OfferList screen.
 */

import android.content.Context;
import android.os.SystemClock;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.karcompany.findoffers.presenters.OfferListPresenter;
import com.karcompany.findoffers.views.activities.OfferListActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.scrollToPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
public class OfferListActivityTest {

	@Rule
	public ActivityTestRule<OfferListActivity> activityRule = new ActivityTestRule<>(OfferListActivity.class);

	private IdlingResource idlingResource;

	@Inject
	OfferListPresenter mOfferListPresenter;

	@Before
	public void setUp() {
		mOfferListPresenter = ((FindOffersApplication) activityRule.getActivity().getApplication()).getApplicationComponent()
				.offerListPresenter();
	}

	@Test
	public void testLoadingInVisibleAfterLoadfinished() {
		onView(withId(R.id.progressView)).check(matches(isDisplayed()));
		idlingResource = startTiming(3000);
		onView(withId(R.id.progressView)).check(matches(not(isDisplayed())));
	}

	@Test
	public void testLoadOffers() {
		onView(withId(R.id.progressView)).check(matches(isDisplayed()));
		idlingResource = startTiming(3000);
		onView(withId(R.id.offer_list)).perform(scrollToPosition(10));
		SystemClock.sleep(3000);
		onView(withId(R.id.fabbutton)).perform(click());
		SystemClock.sleep(3000);
		stopTiming(idlingResource);
	}

	@Test
	public void testLoadMoreOffers() {
		onView(withId(R.id.progressView)).check(matches(isDisplayed()));
		idlingResource = startTiming(3000);
		onView(withId(R.id.offer_list)).perform(scrollToPosition(25));
		SystemClock.sleep(3000);
		onView(withId(R.id.offer_list)).perform(scrollToPosition(35));
		SystemClock.sleep(3000);
		stopTiming(idlingResource);
	}

	@Test
	public void testLoadOfferErrors() {
		idlingResource = startTiming(3000);
		stopTiming(idlingResource);
		onView(withId(R.id.action_settings)).perform(click());
		onView(withId(R.id.applicationId)).perform(typeText("pvkarthik"));
		onView(withId(R.id.saveBtn)).perform(click());
		idlingResource = startTiming(3000);
		stopTiming(idlingResource);
		onView(withId(R.id.noOffersView)).check(matches(isDisplayed()));
		Context context = getContext();
		onView(withId(R.id.noOffersView)).check(matches(withText(context.getString(R.string.error))));
	}

	private static Context getContext() {
		return InstrumentationRegistry.getTargetContext();
	}

	public IdlingResource startTiming(long time) {
		IdlingResource idlingResource = new ElapsedTimeIdlingResource(time);
		Espresso.registerIdlingResources(idlingResource);
		return idlingResource;
	}

	public void stopTiming(IdlingResource idlingResource) {
		Espresso.unregisterIdlingResources(idlingResource);
	}

	public class ElapsedTimeIdlingResource implements IdlingResource {
		private long startTime;
		private final long waitingTime;
		private ResourceCallback resourceCallback;

		public ElapsedTimeIdlingResource(long waitingTime) {
			this.startTime = System.currentTimeMillis();
			this.waitingTime = waitingTime;
		}

		@Override
		public String getName() {
			return ElapsedTimeIdlingResource.class.getName() + ":" + waitingTime;
		}

		@Override
		public boolean isIdleNow() {
			long elapsed = System.currentTimeMillis() - startTime;
			boolean idle = (elapsed >= waitingTime);
			if (idle && !mOfferListPresenter.isLoading()) {
				resourceCallback.onTransitionToIdle();
			}
			return idle && !mOfferListPresenter.isLoading();
		}

		@Override
		public void registerIdleTransitionCallback(ResourceCallback resourceCallback) {
			this.resourceCallback = resourceCallback;
		}
	}

}
