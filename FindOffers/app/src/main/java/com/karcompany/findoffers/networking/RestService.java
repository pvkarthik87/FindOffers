package com.karcompany.findoffers.networking;

import com.karcompany.findoffers.config.Constants;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by pvkarthik on 2017-01-25.
 * <p>
 * REST service interface which Retrofit uses to communicate to a rest end point.
 */

public interface RestService {

	@GET("feed/v1/offers.json")
	Observable<retrofit2.Response<ResponseBody>> getOffers(@Query(Constants.API_PARAM_APPID) String appId, @Query(Constants.API_PARAM_GOOGLE_ADID) String gAdId, @Query(Constants.API_PARAM_GOOGLE_ADID_ISTRACKING_ENABLED) boolean gIsLimitAdTracking, @Query("ip") String ipAddress, @Query("locale") String locale, @Query("offer_types") String offerTypes, @Query("os_version") String osVersion, @Query(Constants.API_PARAM_PAGE) long pageNo, @Query(Constants.API_PARAM_TIMESTAMP) long timeStamp, @Query(Constants.API_PARAM_USERID) String userId, @Query(Constants.API_PARAM_HASHKEY) String hashKey);

}