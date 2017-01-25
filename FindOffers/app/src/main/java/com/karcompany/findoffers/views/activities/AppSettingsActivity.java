package com.karcompany.findoffers.views.activities;

/**
 * Created by pvkarthik on 2017-01-25.
 *
 * Rating Activity which allows user to rate a recipe.
 *
 */

import android.os.Bundle;

import com.karcompany.findoffers.R;
import com.karcompany.findoffers.di.HasComponent;
import com.karcompany.findoffers.di.components.ApplicationComponent;


public class AppSettingsActivity extends BaseActivity implements HasComponent<ApplicationComponent> {

	@Override
	protected void injectComponent(ApplicationComponent component) {
		//component.inject(this);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_app_settings);
		setTitle(getString(R.string.settings));
	}

	@Override
	public ApplicationComponent getComponent() {
		return getApplicationComponent();
	}
}
