package com.karcompany.findoffers.di.modules;

import android.content.Context;

import com.karcompany.findoffers.FindOffersApplication;
import com.karcompany.findoffers.events.RxBus;
import com.karcompany.findoffers.networking.ApiRepo;
import com.karcompany.findoffers.presenters.OfferListPresenter;
import com.karcompany.findoffers.presenters.OfferListPresenterImpl;

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
	OfferListPresenter provideOfferListPresenter(ApiRepo apiRepo) {
		return new OfferListPresenterImpl(apiRepo);
	}

	@Provides @Singleton
	RxBus provideRxBus() {
		return new RxBus();
	}
}
