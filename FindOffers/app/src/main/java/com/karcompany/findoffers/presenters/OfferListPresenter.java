package com.karcompany.findoffers.presenters;

import com.karcompany.findoffers.mvputils.Presenter;
import com.karcompany.findoffers.views.OfferListView;

/**
 * Created by pvkarthik on 2017-01-25.
 *
 * Presenter interface which helps in getting offers from server.
 *
 */

public interface OfferListPresenter extends Presenter {

	void setView(OfferListView offerListView);

	void loadOffers(long pageNo);

	boolean isLoading();

	void setAdVertisingDetails(String gAdId, boolean isTrackingEnabled);

}
