package com.karcompany.findoffers;

/**
 * Created by pvkarthik on 2017-01-25.
 *
 * Android Application.
 */

import android.app.Application;

import com.karcompany.findoffers.di.components.ApplicationComponent;
import com.karcompany.findoffers.di.components.DaggerApplicationComponent;
import com.karcompany.findoffers.di.modules.ApplicationModule;
import com.karcompany.findoffers.networking.NetworkModule;


public class FindOffersApplication extends Application {

	private static ApplicationComponent applicationComponent;

	@Override
	public void onCreate() {
		super.onCreate();
		initializeInjector();
	}

	private void initializeInjector() {
		applicationComponent = DaggerApplicationComponent.builder()
				.applicationModule(new ApplicationModule(this))
				.networkModule(new NetworkModule(this))
				.build();
		applicationComponent.inject(this);
	}

	public static ApplicationComponent getApplicationComponent() {
		return applicationComponent;
	}
}

