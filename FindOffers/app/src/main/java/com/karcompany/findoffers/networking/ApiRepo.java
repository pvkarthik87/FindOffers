package com.karcompany.findoffers.networking;


import android.text.TextUtils;

import com.karcompany.findoffers.config.Constants;
import com.karcompany.findoffers.logging.DefaultLogger;
import com.karcompany.findoffers.models.GetOffersApiResponse;
import com.karcompany.findoffers.utils.SecurityUtils;
import com.karcompany.findoffers.utils.json.JSONParser;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by pvkarthik on 2017-01-25.
 * <p>
 * REST Client which communicates to server to perform some operations
 */

public class ApiRepo {

	private static final String TAG = DefaultLogger.makeLogTag(ApiRepo.class);

	public interface GetOffersApiCallback {
		void onSuccess(GetOffersApiResponse response, long pageNo);

		void onError(NetworkError networkError);

		void onError();
	}

	private final RestService mRestService;

	public ApiRepo(RestService restService) {
		this.mRestService = restService;
	}

	/*
	 *   Retrieves offers from server
	 */
	public Subscription getOffers(String appId, String userId, final String token, final long pageNo, String gAdId, boolean isAdTrackingLimited, final GetOffersApiCallback callback) {
		long timeStamp = System.currentTimeMillis() / 1000;
		String osVersion = android.os.Build.VERSION.RELEASE;
		StringBuilder paramString = new StringBuilder("");
		paramString.append(Constants.API_PARAM_APPID + "=" + appId);
		paramString.append("&");
		paramString.append(Constants.API_PARAM_GOOGLE_ADID + "=" + gAdId);
		paramString.append("&");
		paramString.append(Constants.API_PARAM_GOOGLE_ADID_ISTRACKING_ENABLED + "=" + isAdTrackingLimited);
		paramString.append("&");
		paramString.append(Constants.API_PARAM_IP + "=" + Constants.CLIENT_IP_ADDRESS);
		paramString.append("&");
		paramString.append(Constants.API_PARAM_LOCALE + "=" + Constants.CLIENT_LOCALE);
		paramString.append("&");
		paramString.append(Constants.API_PARAM_OFFER_TYPES + "=" + Constants.SERVER_OFFER_TYPE);
		paramString.append("&");
		paramString.append(Constants.API_PARAM_OSVERSION + "=" + osVersion);
		paramString.append("&");
		paramString.append(Constants.API_PARAM_PAGE + "=" + pageNo);
		paramString.append("&");
		paramString.append(Constants.API_PARAM_TIMESTAMP + "=" + timeStamp);
		paramString.append("&");
		paramString.append(Constants.API_PARAM_USERID + "=" + userId);
		paramString.append("&");
		paramString.append(token);
		String hashKey = "";
		try {
			hashKey = SecurityUtils.SHA1(paramString.toString());
		} catch (NoSuchAlgorithmException ex) {
			DefaultLogger.e(TAG, "Error occured while generating hashkey");
			return null;
		} catch (UnsupportedEncodingException ex) {
			DefaultLogger.e(TAG, "Error occured while generating hashkey");
			return null;
		}
		if (TextUtils.isEmpty(hashKey)) return null;
		return mRestService.getOffers(appId, gAdId, isAdTrackingLimited, Constants.CLIENT_IP_ADDRESS, Constants.CLIENT_LOCALE, Constants.SERVER_OFFER_TYPE, osVersion, pageNo, timeStamp, userId, hashKey)
				.observeOn(AndroidSchedulers.mainThread())
				.subscribeOn(Schedulers.newThread())
				.onErrorResumeNext(new Func1<Throwable, Observable<? extends retrofit2.Response<ResponseBody>>>() {
					@Override
					public Observable<? extends retrofit2.Response<ResponseBody>> call(Throwable throwable) {
						return Observable.error(throwable);
					}
				})
				.subscribe(new Subscriber<retrofit2.Response<ResponseBody>>() {
					@Override
					public void onCompleted() {
						DefaultLogger.d(TAG, "onCompleted");
					}

					@Override
					public void onError(Throwable e) {
						NetworkError ne = new NetworkError(e);
						callback.onError(ne);
						DefaultLogger.d(TAG, "onError " + ne.getAppErrorMessage());
					}

					@Override
					public void onNext(retrofit2.Response<ResponseBody> rawResponse) {
						if (rawResponse != null && rawResponse.isSuccessful()) {
							try {
								String jsonResponse = new String(rawResponse.body().bytes());
								StringBuilder responseStr = new StringBuilder(jsonResponse);
								responseStr.append(token);
								String hashKey = "";
								try {
									hashKey = SecurityUtils.SHA1(responseStr.toString());
								} catch (NoSuchAlgorithmException ex) {
									DefaultLogger.e(TAG, "Error occured while generating response hashkey");
								} catch (UnsupportedEncodingException ex) {
									DefaultLogger.e(TAG, "Error occured while generating response hashkey");
								}
								if (!TextUtils.isEmpty(hashKey)) {
									String serverHashKey = rawResponse.headers().get(Constants.API_RESPONSE_HEADER_SEC_KEY);
									if (!TextUtils.isEmpty(serverHashKey)) {
										GetOffersApiResponse response = JSONParser.parse(jsonResponse, GetOffersApiResponse.class);
										DefaultLogger.d(TAG, "onNext -->count-->" + response.getCount() + " page-->" + pageNo);
										if (response.getCode().equals(Constants.API_RESPONSE_OK) || response.getCode().equals(Constants.API_RESPONSE_NOCONTENT)) {
											callback.onSuccess(response, pageNo);
										}
									}
								}
							} catch (IOException e) {
								DefaultLogger.e(TAG, e.getMessage());
							}
						} else {
							if(rawResponse != null) {
								callback.onError();
							}
						}
					}
				});
	}

}
