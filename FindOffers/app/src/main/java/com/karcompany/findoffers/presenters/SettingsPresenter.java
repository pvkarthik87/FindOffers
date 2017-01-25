package com.karcompany.findoffers.presenters;

import com.karcompany.findoffers.mvputils.Presenter;
import com.karcompany.findoffers.views.SettingsView;

/**
 * Created by pvkarthik on 2017-01-25.
 *
 * Presenter interface which helps in getting offers from server.
 *
 */

public interface SettingsPresenter extends Presenter {

	void setView(SettingsView settingsView);

	void save(String appId, String userId, String token);

}
