package com.gauravbhola.asyncsample.ui.fragments;

import com.gauravbhola.asyncsample.R;
import com.gauravbhola.asyncsample.data.ContactsManager;
import com.gauravbhola.asyncsample.data.model.AddContactsResponse;
import com.gauravbhola.asyncsample.data.model.Contact;

import android.os.AsyncTask;
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

import java.lang.ref.WeakReference;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.support.AndroidSupportInjection;

public class AddContactsFragment extends Fragment {
    @Inject
    ContactsManager mContactsManager;

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

    private static class AddContactTask extends AsyncTask<Contact, Void, AddContactsResponse> {
        WeakReference<AddContactsFragment> mFragmentInstance;
        ContactsManager mContactsManager;

        public AddContactTask(ContactsManager manager, AddContactsFragment fragment) {
            super();
            mContactsManager = manager;
            mFragmentInstance = new WeakReference<AddContactsFragment>(fragment);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (mFragmentInstance.get() == null) {
                // Fragment not present, skip
                return;
            }
            mFragmentInstance.get().showLoading();
        }

        @Override
        protected void onPostExecute(AddContactsResponse response) {
            super.onPostExecute(response);
            if (mFragmentInstance.get() == null) {
                // Fragment not present, skip
                return;
            }
            if (response == null) {
                mFragmentInstance.get().showError("Some error!");
            } else {
                mFragmentInstance.get().success();
            }
        }

        @Override
        protected AddContactsResponse doInBackground(Contact... contacts) {
            return mContactsManager.addContact(contacts[0]);
        }
    }

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
        new AddContactTask(mContactsManager, this).execute(c);
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
}
