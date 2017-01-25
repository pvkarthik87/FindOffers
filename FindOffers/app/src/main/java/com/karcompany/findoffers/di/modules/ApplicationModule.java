package com.karcompany.findoffers.di.modules;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.karcompany.findoffers.FindOffersApplication;
import com.karcompany.findoffers.config.AppConfig;
import com.karcompany.findoffers.events.RxBus;
import com.karcompany.findoffers.networking.ApiRepo;
import com.karcompany.findoffers.presenters.OfferListPresenter;
import com.karcompany.findoffers.presenters.OfferListPresenterImpl;
import com.karcompany.findoffers.presenters.SettingsPresenter;
import com.karcompany.findoffers.presenters.SettingsPresenterImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Dagger module that provides objects which will live during the application lifecycle.
 */
@Module
public class ApplicationModule {
	private final FindOffersApplication application;

	public ApplicationModule(FindOffersApplication application) {
		this.application = application;
	}

	@Provides @Singleton
	Context provideApplicationContext() {
		return this.application;
	}

	@Provides @Singleton
	RxBus provideRxBus() {
		return new RxBus();
	}

	@Provides @Singleton
	SharedPreferences providesSharedPreferences() {
		return PreferenceManager.getDefaultSharedPreferences(this.application);
	}

	@Provides @Singleton
	AppConfig provideAppConfig(SharedPreferences sharedPreferences) {
		return new AppConfig(sharedPreferences);
	}

	@Provides @Singleton
	SettingsPresenter provideSettingsPresenter(AppConfig appConfig) {
		return new SettingsPresenterImpl(appConfig);
	}

	@Provides @Singleton
	OfferListPresenter provideOfferListPresenter(ApiRepo apiRepo, AppConfig appConfig) {
		return new OfferListPresenterImpl(apiRepo, appConfig);
	}
}
