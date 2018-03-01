package com.gauravbhola.asyncsample.di.module;



import com.gauravbhola.asyncsample.ui.activities.AddContactsActivity;
import com.gauravbhola.asyncsample.ui.activities.ViewContactsActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBindingModule {

    @ContributesAndroidInjector(modules = FragmentBindingModule.class)
    abstract AddContactsActivity contributeAddContactsActivity();

    @ContributesAndroidInjector(modules = FragmentBindingModule.class)
    abstract ViewContactsActivity contributeViewContactsActivity();

}
