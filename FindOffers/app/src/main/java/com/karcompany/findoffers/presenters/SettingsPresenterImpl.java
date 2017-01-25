package com.karcompany.findoffers.presenters;

import android.text.TextUtils;

import com.karcompany.findoffers.config.AppConfig;
import com.karcompany.findoffers.config.Constants;
import com.karcompany.findoffers.models.GetOffersApiResponse;
import com.karcompany.findoffers.networking.ApiRepo;
import com.karcompany.findoffers.networking.NetworkError;
import com.karcompany.findoffers.views.OfferListView;
import com.karcompany.findoffers.views.SettingsView;

import javax.inject.Inject;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by pvkarthik on 2017-01-25.
 *
 * Presenter implementation which handles core features (fetches offers from server).
 */

public class SettingsPresenterImpl implements SettingsPresenter {

	private SettingsView mView;
	private AppConfig mAppConfig;

	@Inject
	public SettingsPresenterImpl(AppConfig appConfig) {
		mAppConfig = appConfig;
	}

	@Override
	public void setView(SettingsView settingsView) {
		mView = settingsView;
	}

	@Override
	public void onStart() {
		loadSettings();
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
	}

	private void loadSettings() {
		if(mView != null) {
			mView.setDetails(mAppConfig.getAppId(), mAppConfig.getUserId(), mAppConfig.getToken());
		}
	}

	@Override
	public void save(String appId, String userId, String token) {
		if(!TextUtils.isEmpty(appId)) {
			mAppConfig.setAppId(appId);
		}
		if(!TextUtils.isEmpty(userId)) {
			mAppConfig.setUserId(userId);
		}
		if(!TextUtils.isEmpty(token)) {
			mAppConfig.setToken(token);
		}
	}
}
