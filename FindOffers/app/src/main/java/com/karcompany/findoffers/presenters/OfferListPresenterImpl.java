package com.karcompany.findoffers.presenters;

import android.text.TextUtils;

import com.karcompany.findoffers.config.Constants;
import com.karcompany.findoffers.models.GetOffersApiResponse;
import com.karcompany.findoffers.networking.ApiRepo;
import com.karcompany.findoffers.networking.NetworkError;
import com.karcompany.findoffers.views.OfferListView;

import javax.inject.Inject;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by pvkarthik on 2017-01-25.
 *
 * Presenter implementation which handles core features (fetches offers from server).
 */

public class OfferListPresenterImpl implements OfferListPresenter, ApiRepo.GetOffersApiCallback {

	private OfferListView mView;

	@Inject
	ApiRepo mApiRepo;

	private boolean mIsLoading;
	private CompositeSubscription subscriptions;

	private String mGAdId;
	private boolean mGAdTrackingEnabled;

	@Inject
	public OfferListPresenterImpl(ApiRepo apiRepo) {
		mApiRepo = apiRepo;
	}

	@Override
	public void setView(OfferListView browseUsersView) {
		mView = browseUsersView;
		subscriptions = new CompositeSubscription();
	}

	@Override
	public void onStart() {

	}

	@Override
	public void onResume() {

	}

	@Override
	public void onPause() {

	}

	@Override
	public void onStop() {
	}

	@Override
	public void onDestroy() {
		mView = null;
		if(subscriptions != null) {
			subscriptions.unsubscribe();
			subscriptions = null;
		}
	}

	@Override
	public void loadOffers(long pageNo) {
		if(TextUtils.isEmpty(mGAdId)) return;
		mIsLoading = true;
		Subscription subscription = mApiRepo.getOffers(Constants.SERVER_APP_ID, Constants.USER_ID, pageNo, mGAdId, mGAdTrackingEnabled, this);
		subscriptions.add(subscription);
	}

	@Override
	public void onSuccess(GetOffersApiResponse response, long pageNo) {
		mIsLoading = false;
		if (mView != null) {
			mView.onDataReceived(response, pageNo);
		}
	}

	@Override
	public void onError(NetworkError networkError) {
		mIsLoading = false;
		if (mView != null) {
			mView.onFailure(networkError.getAppErrorMessage());
		}
	}

	@Override
	public boolean isLoading() {
		return mIsLoading;
	}

	@Override
	public void setAdVertisingDetails(String gAdId, boolean isTrackingEnabled) {
		mGAdId = gAdId;
		mGAdTrackingEnabled = isTrackingEnabled;
	}
}
