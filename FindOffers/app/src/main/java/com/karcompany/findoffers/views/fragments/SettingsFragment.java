package com.karcompany.findoffers.views.fragments;


/**
 * Created by pvkarthik on 2017-01-18.
 *
 * User login fragment which displays multiple login options (Google / Facebook / Account).
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.karcompany.findoffers.R;
import com.karcompany.findoffers.di.components.ApplicationComponent;
import com.karcompany.findoffers.events.RxBus;
import com.karcompany.findoffers.logging.DefaultLogger;
import com.karcompany.findoffers.presenters.SettingsPresenter;
import com.karcompany.findoffers.views.SettingsView;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by pvkarthik on 2017-01-16.
 */
public class SettingsFragment extends BaseFragment implements SettingsView, Validator.ValidationListener {

	private static final String TAG = DefaultLogger.makeLogTag(SettingsFragment.class);

	@Inject
	RxBus mEventBus;

	@Inject
	SettingsPresenter mSettingsPresenter;

	@NotEmpty (messageResId = R.string.field_mandatory)
	@Bind(R.id.applicationId)
	EditText mApplicationIdView;

	@NotEmpty (messageResId = R.string.field_mandatory)
	@Bind(R.id.userId)
	EditText mUserIdView;

	@NotEmpty (messageResId = R.string.field_mandatory)
	@Bind(R.id.token)
	EditText mTokenView;

	private Validator mValidator;

	@Bind(R.id.applicationIdLyt)
	TextInputLayout mApplicationIdLyt;

	@Bind(R.id.userIdLyt)
	TextInputLayout mUserIdLyt;

	@Bind(R.id.tokenLyt)
	TextInputLayout mTokenLyt;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
							 @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_settings, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		setUpUI();
		setPresenterView();
	}

	@Override
	public void onResume() {
		super.onResume();
		mSettingsPresenter.onResume();
	}

	@Override
	public void onStart() {
		super.onStart();
		mSettingsPresenter.onStart();
	}

	@Override
	public void onPause() {
		super.onPause();
		mSettingsPresenter.onPause();
	}

	private void setUpUI() {
		mValidator = new Validator(this);
		mValidator.setValidationListener(this);
		mTokenView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView view, int actionId, KeyEvent event) {
				int result = actionId & EditorInfo.IME_MASK_ACTION;
				switch(result) {
					case EditorInfo.IME_ACTION_DONE:
						// done stuff
						onSaveClicked();
						return true;
				}
				return false;
			}
		});
	}

	private void setPresenterView() {
		mSettingsPresenter.setView(this);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mSettingsPresenter.onDestroy();
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getComponent(ApplicationComponent.class).inject(this);
	}

	@OnClick(R.id.saveBtn)
	public void onSaveClicked() {
		clearErrors();
		mValidator.validate();
	}

	@Override
	public void onValidationSucceeded() {
		mSettingsPresenter.save(mApplicationIdView.getText().toString(), mUserIdView.getText().toString(), mTokenView.getText().toString());
		getActivity().finish();
	}

	@Override
	public void onValidationFailed(List<ValidationError> errors) {
		for (ValidationError error : errors) {
			View view = error.getView();
			String message = error.getCollatedErrorMessage(getContext());

			// Display error messages ;)
			if (view instanceof EditText) {
				switch (view.getId()) {
					case R.id.applicationId: {
						mApplicationIdLyt.setError(message);
					}
					break;

					case R.id.userId: {
						mUserIdLyt.setError(message);
					}
					break;

					case R.id.token: {
						mTokenLyt.setError(message);
					}
					break;
				}
				//((EditText) view).setError(message);
			} else {
				Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
			}
		}
	}

	private void clearErrors() {
		mApplicationIdLyt.setError(null);
		mUserIdLyt.setError(null);
		mTokenLyt.setError(null);
	}

	@Override
	public void setDetails(String appId, String userId, String token) {
		if(!TextUtils.isEmpty(appId)) {
			mApplicationIdView.setText(appId);
		}
		if(!TextUtils.isEmpty(userId)) {
			mUserIdView.setText(userId);
		}
		if(!TextUtils.isEmpty(token)) {
			mTokenView.setText(token);
		}
	}
}
