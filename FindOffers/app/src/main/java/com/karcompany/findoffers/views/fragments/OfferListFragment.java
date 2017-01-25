package com.karcompany.findoffers.views.fragments;

/**
 * Created by pvkarthik on 2017-01-25.
 *
 * Offers fragment which displays server data in a recycler view.
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.karcompany.findoffers.R;
import com.karcompany.findoffers.config.Constants;
import com.karcompany.findoffers.config.ViewType;
import com.karcompany.findoffers.di.components.ApplicationComponent;
import com.karcompany.findoffers.logging.DefaultLogger;
import com.karcompany.findoffers.models.GetOffersApiResponse;
import com.karcompany.findoffers.models.Offer;
import com.karcompany.findoffers.presenters.OfferListPresenter;
import com.karcompany.findoffers.views.OfferListView;
import com.karcompany.findoffers.views.adapters.OfferListAdapter;

import java.io.IOException;
import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func0;
import rx.schedulers.Schedulers;

public class OfferListFragment extends BaseFragment implements OfferListView {

	private static final String TAG = DefaultLogger.makeLogTag(OfferListFragment.class);

	private static final String CURRENT_VIEW_TYPE = "CURRENT_VIEW_TYPE";
	private static final String CURRENT_IMAGE_LIST = "CURRENT_IMAGE_LIST";

	@Bind(R.id.offer_list)
	RecyclerView mOffersRecyclerView;

	@Inject
	OfferListPresenter mBrowseImagesPresenter;

	@Bind(R.id.fabbutton)
	FloatingActionButton mFabBtn;

	private OfferListAdapter mAdapter;
	private ViewType mCurrentViewType = ViewType.LIST;

	private LinearLayoutManager mLayoutManager;
	private GridLayoutManager mGridLayoutManager;
	private StaggeredGridLayoutManager mStaggeredGridLayoutManager;

	@Bind(R.id.noOffersView)
	TextView noOffersView;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_offer_list, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		setUpUI(savedInstanceState);
	}

	private void setUpUI(Bundle savedInstanceState) {
		if (savedInstanceState != null) {
			if(savedInstanceState.containsKey(CURRENT_VIEW_TYPE)) {
				mCurrentViewType = ViewType.get(savedInstanceState.getString(CURRENT_VIEW_TYPE, ViewType.LIST.toString()));
			}
		}
		setUpPresenter();
		setUpFabBtn();
		mAdapter = new OfferListAdapter();
		setUpRecyclerView();
		if (savedInstanceState != null) {
			if(savedInstanceState.containsKey(CURRENT_IMAGE_LIST)) {
				ArrayList<Offer> imageList = savedInstanceState.getParcelableArrayList(CURRENT_IMAGE_LIST);
				mAdapter.addData(imageList);
			}
		}
		mOffersRecyclerView.addOnScrollListener(mScrollListener);
	}

	private void setUpPresenter() {
		mBrowseImagesPresenter.setView(this);
	}

	private void setUpRecyclerView() {
		switch(mCurrentViewType) {
			case LIST: {
				mLayoutManager = new LinearLayoutManager(
						getActivity(), LinearLayoutManager.VERTICAL, false);
				mOffersRecyclerView.setLayoutManager(mLayoutManager);
			}
			break;

			case GRID: {
				mGridLayoutManager = new GridLayoutManager(
						getActivity(), 2);
				mOffersRecyclerView.setLayoutManager(mGridLayoutManager);
				mGridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
					@Override
					public int getSpanSize(int position) {
						return (mAdapter.isLoadingPos(position)) ? mGridLayoutManager.getSpanCount() : 1;
					}
				});
			}
			break;

			case STAGGERED: {
				mStaggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
				mOffersRecyclerView.setLayoutManager(mStaggeredGridLayoutManager);
			}
			break;
		}
		mOffersRecyclerView.setAdapter(mAdapter);
		mAdapter.setViewMode(mCurrentViewType);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getComponent(ApplicationComponent.class).inject(this);
	}

	@Override
	public void onStart() {
		super.onStart();
		mBrowseImagesPresenter.onStart();
		fetchAdDetails();
	}

	@Override
	public void onResume() {
		super.onResume();
		mBrowseImagesPresenter.onResume();
	}

	@Override
	public void onPause() {
		super.onPause();
		mBrowseImagesPresenter.onPause();
	}

	@Override
	public void onStop() {
		super.onStop();
		mBrowseImagesPresenter.onStop();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mBrowseImagesPresenter.onDestroy();
		mAdapter.clearData();
	}

	@Override
	public void onDestroyView() {
		mOffersRecyclerView.setAdapter(null);
		mOffersRecyclerView.removeOnScrollListener(mScrollListener);
		super.onDestroyView();
	}

	@Override
	public void onDataReceived(GetOffersApiResponse response, long pageNo) {
		mAdapter.addData(response, pageNo);
		if(response != null) {
			if (response.getCount() > 0) {
				mFabBtn.setVisibility(View.VISIBLE);
			} else {
				mFabBtn.setVisibility(View.GONE);
			}

			if(response.getCode().equals(Constants.API_RESPONSE_NOCONTENT)) {
				noOffersView.setVisibility(View.VISIBLE);
				mOffersRecyclerView.setVisibility(View.GONE);
			} else if(response.getCode().equals(Constants.API_RESPONSE_OK)) {
				noOffersView.setVisibility(View.GONE);
				mOffersRecyclerView.setVisibility(View.VISIBLE);
			}
		}

	}

	@Override
	public void onFailure(String errorMsg) {
		noOffersView.setVisibility(View.VISIBLE);
		noOffersView.setText(R.string.error);
		mOffersRecyclerView.setVisibility(View.GONE);
	}

	@OnClick(R.id.fabbutton)
	public void onToogleViewClicked() {
		switch(mCurrentViewType) {
			case LIST: {
				mCurrentViewType = ViewType.GRID;
			}
			break;

			case GRID: {
				mCurrentViewType = ViewType.STAGGERED;
			}
			break;

			case STAGGERED: {
				mCurrentViewType = ViewType.LIST;
			}
			break;
		}
		setUpRecyclerView();
		setUpFabBtn();
	}

	private void setUpFabBtn() {
		switch(mCurrentViewType) {
			case LIST: {
				mFabBtn.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_grid_on_white_36dp));
			}
			break;

			case GRID: {
				mFabBtn.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_dashboard_white_36dp));
			}
			break;

			case STAGGERED: {
				mFabBtn.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_view_list_white_36dp));
			}
			break;
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString(CURRENT_VIEW_TYPE, mCurrentViewType.getCode());
		outState.putParcelableArrayList(CURRENT_IMAGE_LIST, mAdapter.getImageList());
	}

	private RecyclerView.OnScrollListener mScrollListener = new RecyclerView.OnScrollListener() {
		@Override
		public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
			super.onScrolled(recyclerView, dx, dy);
			int totalItemCount = 0;
			int lastVisibleItem = 0;
			long visibleThreshold = 5;

			switch (mCurrentViewType) {
				case LIST: {
					totalItemCount = mLayoutManager.getItemCount();
					lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
				}
				break;

				case GRID: {
					totalItemCount = mGridLayoutManager.getItemCount();
					lastVisibleItem = mGridLayoutManager.findLastVisibleItemPosition();
				}
				break;

				case STAGGERED: {
					totalItemCount = mStaggeredGridLayoutManager.getItemCount();
					int[] positions = new int[2];
					positions = mStaggeredGridLayoutManager.findLastVisibleItemPositions(positions);
					lastVisibleItem = positions[1] > positions[0] ? positions[1] : positions[0];
				}
				break;
			}
			if (!mBrowseImagesPresenter.isLoading()
					&& totalItemCount <= (lastVisibleItem + visibleThreshold)) {
				//End of the items
				mAdapter.loadMore();
			}
		}
	};

	private Observable<Constants.GAdInfo> mAdDetailsObservable = Observable.defer(new Func0<Observable<Constants.GAdInfo>>() {

		@Override
		public Observable<Constants.GAdInfo> call() {

			AdvertisingIdClient.Info idInfo = null;
			try {
				idInfo = AdvertisingIdClient.getAdvertisingIdInfo(getContext());
			} catch (GooglePlayServicesNotAvailableException e) {
				e.printStackTrace();
			} catch (GooglePlayServicesRepairableException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if(idInfo != null) {
				Constants.GAdInfo gAdInfo = new Constants.GAdInfo();
				try {
					gAdInfo.adId = idInfo.getId();
					gAdInfo.isAdTrackingEnabled = idInfo.isLimitAdTrackingEnabled();
				} catch (NullPointerException e) {
					e.printStackTrace();
				}
				return Observable.just(gAdInfo);
			}
			return Observable.just(null);
		}
	});

	private void fetchAdDetails() {
		mAdDetailsObservable
				.subscribeOn(Schedulers.newThread())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Subscriber<Constants.GAdInfo>() {
					@Override
					public void onCompleted() {

					}

					@Override
					public void onError(Throwable e) {

					}

					@Override
					public void onNext(Constants.GAdInfo gAdInfo) {
						onAdDetailsReceived(gAdInfo);
					}
				});
	}

	private void onAdDetailsReceived(Constants.GAdInfo gAdInfo) {
		if(gAdInfo != null) {
			mBrowseImagesPresenter.setAdVertisingDetails(gAdInfo.adId, gAdInfo.isAdTrackingEnabled);
			mAdapter.loadData();
		}
	}
}
