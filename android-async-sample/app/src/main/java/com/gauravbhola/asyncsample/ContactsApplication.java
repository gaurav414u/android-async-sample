package com.gauravbhola.asyncsample;


import com.gauravbhola.asyncsample.di.DaggerAppComponent;
import com.gauravbhola.asyncsample.di.module.AppModule;
import com.gauravbhola.asyncsample.di.module.NetModule;
import com.gauravbhola.asyncsample.util.Const;

import android.app.Activity;
import android.app.Application;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

public class ContactsApplication extends Application implements HasActivityInjector {
    @Inject
    DispatchingAndroidInjector<Activity> mInjector;

    @Override
    public void onCreate() {
        super.onCreate();

        DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .netModule(new NetModule(Const.API_BASE_URL))
                .build()
                .inject(this);
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return mInjector;
    }
}
