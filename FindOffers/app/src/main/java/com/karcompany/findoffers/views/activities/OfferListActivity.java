package com.karcompany.findoffers.views.activities;

/**
 * Created by pvkarthik on 2017-01-25.
 *
 * Displays list of offers available at the moment.
 */

import android.os.Bundle;

import com.karcompany.findoffers.R;
import com.karcompany.findoffers.di.HasComponent;
import com.karcompany.findoffers.di.components.ApplicationComponent;
import com.karcompany.findoffers.logging.DefaultLogger;

public class OfferListActivity extends BaseActivity implements HasComponent<ApplicationComponent> {

	private static final String TAG = DefaultLogger.makeLogTag(OfferListActivity.class);

	@Override
	protected void injectComponent(ApplicationComponent component) {
		component.inject(this);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_offer_list);
		setTitle(getString(R.string.app_name));
	}

	@Override
	public ApplicationComponent getComponent() {
		return getApplicationComponent();
	}

}
