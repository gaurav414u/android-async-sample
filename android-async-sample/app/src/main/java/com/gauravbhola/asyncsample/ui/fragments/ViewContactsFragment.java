package com.gauravbhola.asyncsample.ui.fragments;

import com.gauravbhola.asyncsample.R;
import com.gauravbhola.asyncsample.data.ContactsManager;
import com.gauravbhola.asyncsample.data.model.Contact;
import com.gauravbhola.asyncsample.data.model.event.FetchContactsEvent;
import com.gauravbhola.asyncsample.ui.adapters.ViewContactsAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.MainThread;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.support.AndroidSupportInjection;

public class ViewContactsFragment extends Fragment {
    public static interface ViewContactsCallbacks {
        public void onAddContactRequest();
    }
    private ViewContactsCallbacks mCallbacks;

    @Inject
    EventBus mEventBus;

    @Inject
    ContactsManager mContactsManager;

    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;

    @BindView(R.id.error_view)
    LinearLayout mErrorView;

    @BindView(R.id.error_text)
    TextView mErrorTextView;

    @BindView(R.id.btn_retry)
    Button retryButton;

    @BindView(R.id.contacts_list_view)
    RecyclerView mRecyclerView;

    ViewContactsAdapter mContactsAdapter;
    List<Contact> mContactList;

    public static ViewContactsFragment getInstance() {
        return new ViewContactsFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ViewContactsCallbacks) {
            mCallbacks = (ViewContactsCallbacks)context;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidSupportInjection.inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_view_contacts, null);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity().getApplicationContext(), DividerItemDecoration.VERTICAL));
    }

    @OnClick(R.id.btn_add_contact)
    public void onAddContactClicked() {
        if (mCallbacks != null) {
            mCallbacks.onAddContactRequest();
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        mEventBus.register(this);
        mContactsManager.fetchContacts();
    }

    @Override
    public void onStop() {
        super.onStop();
        mEventBus.unregister(this);
    }

    @OnClick(R.id.btn_retry)
    public void onRetryClicked() {
        mContactsManager.fetchContacts();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onContactEvent(FetchContactsEvent event) {
        switch (event.getStatus()) {
            case ERROR:
                showError(event.getMessage());
                break;
            case LOADING:
                showLoading();
                break;
            case SUCCESS:
                showData(event.getData());
                break;
            default:
        }

    }

    public void showError(String message) {
        mErrorTextView.setText(message);
        mErrorView.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.GONE);
    }

    @MainThread
    public void showLoading() {
        mErrorView.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);
    }

    public void showData(List<Contact> contacts) {
        mErrorTextView.setText("");

        mErrorView.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);

        if (mContactList == null) {
            mContactList = new ArrayList<>();
            mContactsAdapter = new ViewContactsAdapter(getActivity().getApplicationContext(), mContactList);
            mRecyclerView.setAdapter(mContactsAdapter);
        }
        mContactList.clear();
        mContactList.addAll(contacts);
        mContactsAdapter.notifyDataSetChanged();
    }
}
