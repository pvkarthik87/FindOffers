package com.karcompany.findoffers.views.activities;

/**
 * Created by pvkarthik on 2017-01-25.
 *
 * Displays list of offers available at the moment.
 */

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_offers, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getItemId() == R.id.action_settings) {
			gotToSettingsScreen();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void gotToSettingsScreen() {
		Intent intent = new Intent(this, AppSettingsActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		startActivity(intent);
	}
}
