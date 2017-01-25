package com.karcompany.findoffers.views.adapters;

/**
 * Created by pvkarthik on 2017-01-25.
 *
 * Recycler view adapter which displays offer list data.
 */

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.karcompany.findoffers.FindOffersApplication;
import com.karcompany.findoffers.R;
import com.karcompany.findoffers.config.Constants;
import com.karcompany.findoffers.config.ViewType;
import com.karcompany.findoffers.events.RxBus;
import com.karcompany.findoffers.models.GetOffersApiResponse;
import com.karcompany.findoffers.models.Offer;
import com.karcompany.findoffers.presenters.OfferListPresenter;
import com.karcompany.findoffers.utils.GlideUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.inject.Inject;

public class OfferListAdapter extends RecyclerView.Adapter<ImageListItemViewHolder> {

	private Map<Long, Offer> mOffersDataMap;
	private List<Long> mOfferIdList;
	private ViewType mCurrentViewType;
	private Random mRandom = new Random();

	private final long PAGE_COUNT_UNKNOWN = -1;
	private final long PAGE_COUNT_ZERO = 0;

	private long mPagesRemained = PAGE_COUNT_UNKNOWN;
	private long mCurrentPage;

	@Inject
	RxBus mEventBus;

	@Inject
	OfferListPresenter mBrowseImagesPresenter;

	private int VIEW_TYPE_ITEM = 1;
	private int VIEW_TYPE_PROGRESS = 2;

	private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View view) {
			int itemPosition = (Integer) view.getTag();
			Long id = mOfferIdList.get(itemPosition);
		}
	};

	public OfferListAdapter() {
		FindOffersApplication.getApplicationComponent().inject(this);
		mOffersDataMap = new LinkedHashMap<>();
		mOfferIdList = new ArrayList<>(4);
	}

	@Override
	public ImageListItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = null;
		if(viewType == VIEW_TYPE_ITEM) {
			switch (mCurrentViewType) {
				case LIST: {
					view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_image_row_item_list, parent, false);
				}
				break;

				case GRID: {
					view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_image_row_item_grid, parent, false);
				}
				break;

				case STAGGERED: {
					view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_image_row_item_staggered, parent, false);
				}
				break;
			}
		} else {
			view = LayoutInflater.from(parent.getContext()).inflate(R.layout.loading_progress, parent, false);
		}
		return new ImageListItemViewHolder(view);
	}

	@Override
	public void onBindViewHolder(ImageListItemViewHolder holder, int position) {
		if(getItemViewType(position) == VIEW_TYPE_ITEM) {
			if (mCurrentViewType == ViewType.STAGGERED) {
				holder.imageImgView.getLayoutParams().height = getRandomIntInRange(300, 200);
			}
			holder.imageTitleTxtView.setText("");
			Glide.clear(holder.imageImgView);
			holder.imageImgView.setImageDrawable(null);
			if (position < mOfferIdList.size()) {
				Offer image = mOffersDataMap.get(mOfferIdList.get(position));
				if (image != null) {
					if (!TextUtils.isEmpty(image.getTitle())) {
						holder.imageTitleTxtView.setText(image.getTitle());
					}
					if (mCurrentViewType == ViewType.STAGGERED || mCurrentViewType == ViewType.GRID) {
						GlideUtils.loadImage(holder.itemView.getContext(), image.getThumbnail().getHires(), holder.imageImgView);
					} else {
						GlideUtils.loadImage(holder.itemView.getContext(), image.getThumbnail().getLowres(), holder.imageImgView);
					}
				}
				holder.itemView.setTag(position);
				holder.itemView.setOnClickListener(mOnClickListener);
			}
		}
	}

	// Custom method to get a random number between a range
	protected int getRandomIntInRange(int max, int min){
		return mRandom.nextInt((max-min)+min)+min;
	}

	@Override
	public void onViewRecycled(ImageListItemViewHolder holder) {
		if(holder.imageImgView != null) {
			Glide.clear(holder.imageImgView);
			holder.imageImgView.setImageDrawable(null);
		}
		super.onViewRecycled(holder);
	}

	@Override
	public int getItemCount() {
		if(isDataLoadCompleted()) {
			return mOfferIdList.size();
		} else {
			return mOfferIdList.size() + 1;
		}
	}

	public void clearData() {
		mOfferIdList.clear();
		mOffersDataMap.clear();
	}

	public void setViewMode(ViewType viewType) {
		mCurrentViewType = viewType;
	}

	public ArrayList<Offer> getImageList() {
		ArrayList<Offer> imageList = new ArrayList<>();
		Iterator<Map.Entry<Long, Offer>> iterator = mOffersDataMap.entrySet().iterator();
		while (iterator.hasNext()) {
			Map.Entry<Long, Offer> entry = iterator.next();
			imageList.add(entry.getValue());
		}
		return imageList;
	}

	public void loadData() {
		clearData();
		mPagesRemained = -1;
		mCurrentPage = 0;
		notifyDataSetChanged();
		loadMore();
	}

	public void addData(GetOffersApiResponse response, long pageNo) {
		if(response != null) {
			if(response.getCode().equals(Constants.API_RESPONSE_OK)) {
				mPagesRemained = response.getPages();
				mCurrentPage = pageNo;
				if (response.getOffers() != null) {
					for (Offer image : response.getOffers()) {
						mOffersDataMap.put(image.getOfferId(), image);
					}
					int oldSize = mOfferIdList.size();
					mOfferIdList.clear();
					mOfferIdList.addAll(mOffersDataMap.keySet());
					int newSize = mOfferIdList.size();
					if (oldSize > 0) {
						notifyItemRangeInserted(oldSize, newSize - oldSize);
						if (mCurrentPage >= mPagesRemained) {
							notifyItemRemoved(mOfferIdList.size());
						}
					} else {
						notifyDataSetChanged();
					}
				}
			} else if(response.getCode().equals(Constants.API_RESPONSE_NOCONTENT)) {
				mPagesRemained = PAGE_COUNT_ZERO;
				if (mCurrentPage >= mPagesRemained) {
					notifyItemRemoved(mOfferIdList.size());
				}
			}
		}
	}

	public void addData(ArrayList<Offer> imageList) {

	}

	private long getNextPageNumber() {
		return mCurrentPage + 1;
	}

	private boolean isDataLoadCompleted() {
		if(mPagesRemained == PAGE_COUNT_UNKNOWN) return false;
		if(mPagesRemained == PAGE_COUNT_ZERO) return true;
		return mPagesRemained <= mCurrentPage;
	}

	public void loadMore() {
		if(!isDataLoadCompleted()) {
			mBrowseImagesPresenter.loadOffers(getNextPageNumber());
		}
	}

	@Override
	public int getItemViewType(int position) {
		if(mPagesRemained == PAGE_COUNT_UNKNOWN && position == 0)
			return VIEW_TYPE_PROGRESS;
		if(mPagesRemained > mCurrentPage && position == mOfferIdList.size()){
			return VIEW_TYPE_PROGRESS;
		}
		return VIEW_TYPE_ITEM;
	}

	public boolean isLoadingPos(int position) {
		return getItemViewType(position) == VIEW_TYPE_PROGRESS;
	}
}
