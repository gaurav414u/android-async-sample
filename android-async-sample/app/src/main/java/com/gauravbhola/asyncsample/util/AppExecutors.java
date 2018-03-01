package com.gauravbhola.asyncsample.util;


import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.util.concurrent.Executor;

public class AppExecutors {
    private final Executor networkIO;
    private final Executor diskIO;
    private final Executor mainThread;

    public AppExecutors(Executor networkIO, Executor diskIO, Executor mainThread) {
        this.networkIO = networkIO;
        this.diskIO = diskIO;
        this.mainThread = mainThread;
    }

    public static class MainThreadExecutor implements Executor {
        Handler mHandler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(@NonNull Runnable runnable) {
            mHandler.post(runnable);
        }
    }


    public Executor networkIO() {
        return networkIO;
    }

    public Executor diskIO() {
        return diskIO;
    }

    public Executor mainThread() {
        return mainThread;
    }
}
