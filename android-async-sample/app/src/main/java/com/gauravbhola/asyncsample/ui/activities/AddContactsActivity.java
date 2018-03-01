package com.gauravbhola.asyncsample.ui.activities;

import com.gauravbhola.asyncsample.R;
import com.gauravbhola.asyncsample.ui.fragments.AddContactsFragment;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import butterknife.ButterKnife;

public class AddContactsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        ButterKnife.bind(this);
        if (savedInstanceState == null) {
            attachAddContactsFragment();
        }
    }

    private void attachAddContactsFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.fragment_container, AddContactsFragment.getInstance());
        ft.commit();
    }
}
