package com.gauravbhola.asyncsample.ui.activities;

import com.gauravbhola.asyncsample.R;
import com.gauravbhola.asyncsample.ui.fragments.ViewContactsFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import butterknife.ButterKnife;

public class ViewContactsActivity extends BaseActivity implements ViewContactsFragment.ViewContactsCallbacks {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_contacts);
        ButterKnife.bind(this);
        attachViewContactsFragment();
    }

    private void attachViewContactsFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.fragment_container, ViewContactsFragment.getInstance());
        ft.commit();
    }


    @Override
    public void onAddContactRequest() {
        startActivity(new Intent(this, AddContactsActivity.class));
    }
}
