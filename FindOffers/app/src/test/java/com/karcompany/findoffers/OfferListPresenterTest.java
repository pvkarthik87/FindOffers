package com.karcompany.findoffers;

/**
 * Created by pvkarthik on 2017-01-25.
 *
 * Presenter unit test cases.
 */

import com.karcompany.findoffers.config.AppConfig;
import com.karcompany.findoffers.config.Constants;
import com.karcompany.findoffers.models.GetOffersApiResponse;
import com.karcompany.findoffers.networking.ApiRepo;
import com.karcompany.findoffers.presenters.OfferListPresenter;
import com.karcompany.findoffers.presenters.OfferListPresenterImpl;
import com.karcompany.findoffers.views.OfferListView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import rx.Subscription;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@RunWith(RobolectricGradleTestRunner.class)
// Change what is necessary for your project
@Config(constants = BuildConfig.class, sdk = 21, manifest = "/src/main/AndroidManifest.xml")
public class OfferListPresenterTest {

	@Mock
	private ApiRepo model;

	@Mock
	private AppConfig appConfig;

	@Mock
	private OfferListView view;

	private OfferListPresenter presenter;

	private String mGAdId = "kick";

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);

		presenter = new OfferListPresenterImpl(model, appConfig);
		presenter.setView(view);

		doAnswer(new Answer() {
			@Override
			public String answer(InvocationOnMock invocation) throws Throwable {
				return Constants.SERVER_APP_ID;
			}
		}).when(appConfig).getAppId();

		doAnswer(new Answer() {
			@Override
			public String answer(InvocationOnMock invocation) throws Throwable {
				return Constants.USER_ID;
			}
		}).when(appConfig).getUserId();

		doAnswer(new Answer() {
			@Override
			public String answer(InvocationOnMock invocation) throws Throwable {
				return Constants.SERVER_API_KEY;
			}
		}).when(appConfig).getToken();

		/*
			Define the desired behaviour.

			Queuing the action in "doAnswer" for "when" is executed.
			Clear and synchronous way of setting reactions for actions (stubbing).
			*/
		doAnswer(new Answer() {
			@Override
			public Object answer(InvocationOnMock invocation) throws Throwable {
				GetOffersApiResponse imageSearchApiResponse = new GetOffersApiResponse();
				((ApiRepo.GetOffersApiCallback) presenter).onSuccess(imageSearchApiResponse, 1);
				return Mockito.mock(Subscription.class);
			}
		}).when(model).getOffers(Constants.SERVER_APP_ID, Constants.USER_ID, Constants.SERVER_API_KEY, 1, mGAdId, false, (ApiRepo.GetOffersApiCallback) presenter);
	}

	/**
	 Verify if model.getOffers was called once.
	 Verify if view.onDataReceived is called once with the specified object
	 */
	@Test
	public void testFetchOffersWhenAdvertisingAvailable() {
		presenter.setAdVertisingDetails(mGAdId, false);
		presenter.loadOffers(1);
		// verify can be called only on mock objects
		verify(model, times(1)).getOffers(eq(Constants.SERVER_APP_ID), eq(Constants.USER_ID), eq(Constants.SERVER_API_KEY), eq((long) 1), eq(mGAdId), eq(false), eq((ApiRepo.GetOffersApiCallback) presenter));
		verify(view, times(1)).onDataReceived(any(GetOffersApiResponse.class), eq((long) 1));
	}

	/**
	 Verify if model.getOffers was never called.
	 Verify if view.onDataReceived is never called.
	 */
	@Test
	public void testFetchOffersWhenAdvertisingDetailsNotAvailable() {
		presenter.loadOffers(1);
		// verify can be called only on mock objects
		verify(model, times(0)).getOffers(eq(Constants.SERVER_APP_ID), eq(Constants.USER_ID), eq(Constants.SERVER_API_KEY), eq((long) 1), eq(mGAdId), eq(false), eq((ApiRepo.GetOffersApiCallback) presenter));
		verify(view, times(0)).onDataReceived(any(GetOffersApiResponse.class), eq((long) 1));
	}

}
