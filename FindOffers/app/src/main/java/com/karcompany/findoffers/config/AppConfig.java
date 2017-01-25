package com.karcompany.findoffers.config;

import android.content.SharedPreferences;
import android.text.TextUtils;

/**
 * Created by sammyer on 2017-01-25.
 */

public class AppConfig {

	private SharedPreferences mSharedPreferences;

	public AppConfig(SharedPreferences sharedPreferences) {
		mSharedPreferences = sharedPreferences;
	}

	public String getAppId() {
		return mSharedPreferences.getString(Constants.KEY_APP_ID, Constants.SERVER_APP_ID);
	}

	public void setAppId(String appId) {
		if(!TextUtils.isEmpty(appId)) {
			mSharedPreferences.edit().putString(Constants.KEY_APP_ID, appId).apply();
		}
	}

	public String getUserId() {
		return mSharedPreferences.getString(Constants.KEY_USER_ID, Constants.USER_ID);
	}

	public void setUserId(String userId) {
		if(!TextUtils.isEmpty(userId)) {
			mSharedPreferences.edit().putString(Constants.KEY_USER_ID, userId).apply();
		}
	}

	public String getToken() {
		return mSharedPreferences.getString(Constants.KEY_TOKEN, Constants.SERVER_API_KEY);
	}

	public void setToken(String token) {
		if(!TextUtils.isEmpty(token)) {
			mSharedPreferences.edit().putString(Constants.KEY_TOKEN, token).apply();
		}
	}

}
