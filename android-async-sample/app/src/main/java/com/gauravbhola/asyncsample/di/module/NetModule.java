package com.gauravbhola.asyncsample.di.module;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.gauravbhola.asyncsample.data.remote.ApiService;
import com.gauravbhola.asyncsample.util.AppExecutors;

import org.greenrobot.eventbus.EventBus;

import android.app.Application;

import java.util.concurrent.Executors;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetModule {
    private String mBaseUrl;

    public NetModule(String url) {
        mBaseUrl = url;
    }

    @Provides
    @Singleton
    Gson provideGson(Application application) {
        return new GsonBuilder().create();
    }

    @Provides
    @Singleton
    ApiService provideApiService(Application application, Gson gson) {
        return new Retrofit.Builder()
                .baseUrl(mBaseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(ApiService.class);
    }

    @Provides
    @Singleton
    AppExecutors provideAppExecutors() {
        return new AppExecutors(Executors.newSingleThreadExecutor(), Executors.newFixedThreadPool(3), new AppExecutors.MainThreadExecutor());
    }

    @Provides
    @Singleton
    EventBus provideEventBus() {
        return EventBus.getDefault();
    }
}
