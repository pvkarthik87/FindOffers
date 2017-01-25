package com.karcompany.findoffers.networking;

/**
 * Created by pvkarthik on 2017-01-25.
 *
 * Dagger module which is responsible for providing objects injected variables.
 */

import com.karcompany.findoffers.FindOffersApplication;
import com.karcompany.findoffers.config.Constants;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

@Module
public class NetworkModule {
	private final FindOffersApplication application;

	public NetworkModule(FindOffersApplication application) {
		this.application = application;
	}

	@Provides
	@Singleton
	Retrofit provideCall() {
		OkHttpClient okHttpClient = new OkHttpClient.Builder()
				.readTimeout(5, TimeUnit.SECONDS)
				.connectTimeout(5, TimeUnit.SECONDS)
				.build();

		return new Retrofit.Builder()
				.baseUrl(Constants.SERVER_BASE_URL)
				.client(okHttpClient)
				.addConverterFactory(GsonConverterFactory.create())
				.addConverterFactory(ScalarsConverterFactory.create())
				.addCallAdapterFactory(RxJavaCallAdapterFactory.create())
				.build();
	}

	@Provides
	@Singleton
	@SuppressWarnings("unused")
	public RestService providesRestService(
			Retrofit retrofit) {
		return retrofit.create(RestService.class);
	}

	@Provides
	@Singleton
	@SuppressWarnings("unused")
	public ApiRepo providesService(
			RestService networkService) {
		return new ApiRepo(networkService);
	}

}

