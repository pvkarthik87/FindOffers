package com.karcompany.findoffers.views;

import com.karcompany.findoffers.models.GetOffersApiResponse;

/**
 * Created by pvkarthik on 2017-01-25.
 *
 * View interface which notifies presenter to perform some operations.
 */

public interface OfferListView {

	void onDataReceived(GetOffersApiResponse response, long pageNo);

	void onFailure(String errorMsg);

}
