package com.gauravbhola.asyncsample.ui.fragments;

import com.gauravbhola.asyncsample.R;
import com.gauravbhola.asyncsample.data.ContactsManager;
import com.gauravbhola.asyncsample.data.model.Contact;
import com.gauravbhola.asyncsample.data.model.event.AddContactEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import android.os.Bundle;
import android.support.annotation.MainThread;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.support.AndroidSupportInjection;

public class AddContactsFragment extends Fragment {
    @Inject
    ContactsManager mContactsManager;

    @Inject
    EventBus mEventBus;

    @BindView(R.id.et_name)
    TextInputEditText mNameView;

    @BindView(R.id.et_email)
    TextInputEditText mEmailView;

    @BindView(R.id.et_phone)
    TextInputEditText mPhoneView;

    @BindView(R.id.tv_error)
    TextView mErrorView;

    @BindView(R.id.btn_add)
    Button mAddButton;

    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;

    public static AddContactsFragment getInstance() {
        return new AddContactsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidSupportInjection.inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_contact, null);
        ButterKnife.bind(this, v);
        return v;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onContactEvent(AddContactEvent event) {
        switch (event.getStatus()) {
            case ERROR:
                showError(event.getMessage());
                break;
            case LOADING:
                showLoading();
                break;
            case SUCCESS:
                success();
                break;
            default:
        }
    }

    @OnClick(R.id.btn_add)
    public void onAddClicked() {
        if (TextUtils.isEmpty(mNameView.getText())) {
            mNameView.setError("Please enter a name");
            return;
        }
        if (TextUtils.isEmpty(mPhoneView.getText())) {
            mPhoneView.setError("Please enter a valid phone number");
            return;
        }
        if (TextUtils.isEmpty(mEmailView.getText())) {
            mEmailView.setError("Please enter a valid email address");
            return;
        }
        Contact c = new Contact();
        c.setEmail(mEmailView.getText().toString());
        c.setName(mNameView.getText().toString());
        c.setPhone(mPhoneView.getText().toString());
        mContactsManager.addContact(c);
    }

    public void setInteractionsEnabled(boolean enabled) {
        mNameView.setEnabled(enabled);
        mPhoneView.setEnabled(enabled);
        mEmailView.setEnabled(enabled);
        mAddButton.setEnabled(enabled);
    }

    @MainThread
    public void showError(String message) {
        setInteractionsEnabled(true);
        mErrorView.setText(message);

        mErrorView.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);
    }

    @MainThread
    public void showLoading() {
        setInteractionsEnabled(false);
        mErrorView.setText("");

        mErrorView.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);
    }

    public void success() {
        setInteractionsEnabled(true);

        mProgressBar.setVisibility(View.GONE);
        mErrorView.setVisibility(View.GONE);
//        Toast.makeText(getActivity(), "Contact successfully added", Toast.LENGTH_SHORT).show();
        Snackbar.make(getView(), "Contact successfully added", Snackbar.LENGTH_SHORT).show();
    }


    @Override
    public void onStart() {
        super.onStart();
        mEventBus.register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        mEventBus.unregister(this);
    }
}
