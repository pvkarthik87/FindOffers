package com.karcompany.findoffers.di.components;

import android.content.Context;

import com.karcompany.findoffers.FindOffersApplication;
import com.karcompany.findoffers.di.modules.ApplicationModule;
import com.karcompany.findoffers.networking.NetworkModule;
import com.karcompany.findoffers.views.activities.BaseActivity;
import com.karcompany.findoffers.views.activities.OfferListActivity;
import com.karcompany.findoffers.views.adapters.OfferListAdapter;
import com.karcompany.findoffers.views.fragments.OfferListFragment;
import com.karcompany.findoffers.views.fragments.SettingsFragment;

import javax.inject.Singleton;

import dagger.Component;


/**
 * A component whose lifetime is the life of the application.
 */
@Singleton // Constraints this component to one-per-application or unscoped bindings.
@Component(modules = {ApplicationModule.class, NetworkModule.class})
public interface ApplicationComponent {

	void inject(FindOffersApplication findOffersApplication);

	void inject(BaseActivity baseActivity);

	void inject(OfferListAdapter offerListAdapter);

	void inject(OfferListFragment offerListFragment);

	void inject(OfferListActivity offerListActivity);

	void inject(SettingsFragment settingsFragment);
	
	//Exposed to sub-graphs.
	Context context();
}
