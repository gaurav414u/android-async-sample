package com.gauravbhola.asyncsample.di;


import com.gauravbhola.asyncsample.ContactsApplication;
import com.gauravbhola.asyncsample.di.module.ActivityBindingModule;
import com.gauravbhola.asyncsample.di.module.AppModule;
import com.gauravbhola.asyncsample.di.module.NetModule;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjectionModule;

@Singleton
@Component(modules= {AppModule.class, NetModule.class, ActivityBindingModule.class, AndroidInjectionModule.class})
public interface AppComponent {
    public void inject(ContactsApplication app);
}
