package com.gauravbhola.asyncsample.di.module;


import com.gauravbhola.asyncsample.ui.fragments.AddContactsFragment;
import com.gauravbhola.asyncsample.ui.fragments.ViewContactsFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentBindingModule {

    @ContributesAndroidInjector
    abstract AddContactsFragment contributesContactsFragment();

    @ContributesAndroidInjector
    abstract ViewContactsFragment contributesViewContactsFragment();

}
