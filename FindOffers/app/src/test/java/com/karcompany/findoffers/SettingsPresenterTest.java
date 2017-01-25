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
import com.karcompany.findoffers.presenters.SettingsPresenter;
import com.karcompany.findoffers.presenters.SettingsPresenterImpl;
import com.karcompany.findoffers.views.OfferListView;
import com.karcompany.findoffers.views.SettingsView;

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
public class SettingsPresenterTest {

	@Mock
	private AppConfig appConfig;

	@Mock
	private SettingsView view;

	private SettingsPresenter presenter;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);

		presenter = new SettingsPresenterImpl(appConfig);
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
	}

	/**
	 Verify if view.setDetails is called once with the specified object
	 */
	@Test
	public void testSettingsDetails() {
		presenter.onStart();
		// verify can be called only on mock objects
		verify(view, times(1)).setDetails(eq(Constants.SERVER_APP_ID), eq(Constants.USER_ID), eq(Constants.SERVER_API_KEY));
	}

}
