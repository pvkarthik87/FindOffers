package com.karcompany.findoffers.config;

import retrofit2.http.Query;

/**
 * Created by pvkarthik on 2017-01-25.
 *
 * Main constants file entire application.
 * Any app related constants should be added here.
 */

public class Constants {

	public static final String SERVER_BASE_URL = "http://api.fyber.com/";

	public static final String SERVER_APP_ID = "2070";

	public static final String USER_ID = "spiderman";

	public static final String SERVER_API_KEY = "1c915e3b5d42d05136185030892fbb846c278927";

	public static final String SERVER_OFFER_TYPE = "112";

	public static final String CLIENT_IP_ADDRESS = "109.235.143.113";

	public static final String CLIENT_LOCALE = "de";

	public static final String API_PARAM_APPID = "appid";

	public static final String API_PARAM_GOOGLE_ADID = "google_ad_id";

	public static final String API_PARAM_GOOGLE_ADID_ISTRACKING_ENABLED = "google_ad_id_limited_tracking_enabled";

	public static final String API_PARAM_FORMAT = "format";

	public static final String API_PARAM_IP = "ip";

	public static final String API_PARAM_LOCALE = "locale";

	public static final String API_PARAM_OFFER_TYPES = "offer_types";

	public static final String API_PARAM_OSVERSION = "os_version";

	public static final String API_PARAM_PAGE = "page";

	public static final String API_PARAM_TIMESTAMP = "timestamp";

	public static final String API_PARAM_USERID = "uid";

	public static final String API_PARAM_HASHKEY = "hashkey";

	public static class GAdInfo {

		public String adId;
		public boolean isAdTrackingEnabled;

	}

	public static final String API_RESPONSE_HEADER_SEC_KEY = "X-Sponsorpay-Response-Signature";

	public static final String API_RESPONSE_OK = "OK";

	public static final String API_RESPONSE_NOCONTENT = "NO_CONTENT";

}
