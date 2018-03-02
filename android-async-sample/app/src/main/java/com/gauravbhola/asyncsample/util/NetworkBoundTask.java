package com.gauravbhola.asyncsample.util;



import com.gauravbhola.asyncsample.data.model.event.FetchContactsEvent;
import com.gauravbhola.asyncsample.data.model.event.ResourceEvent;

import org.greenrobot.eventbus.EventBus;

import android.support.annotation.WorkerThread;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

public abstract class NetworkBoundTask<ResponseType, EventType extends ResourceEvent<ResponseType>> implements Runnable {
    EventBus mEventBus;

    public NetworkBoundTask(EventBus eventBus) {
        mEventBus = eventBus;
        mEventBus.post((EventType) getNewEventWithCachedData());
    }

    @Override
    public void run() {
        EventType t = (EventType) getNewEventWithCachedData().setStatus(ResourceEvent.Status.LOADING);
        mEventBus.post(t);

        try {
            Call<ResponseType> call = createCall();
            Response<ResponseType> res = call.execute();

            if (res.isSuccessful()) {
                t = (EventType) getNewEventWithCachedData().setStatus(ResourceEvent.Status.SUCCESS).setData(res.body());
                saveCallResult(t.getData());
                mEventBus.post(t);
            } else {
                t = (EventType) getNewEventWithCachedData().setStatus(ResourceEvent.Status.ERROR).setMessage(res.message());
                mEventBus.post(t);
            }
        } catch (IOException e) {
            t = (EventType) getNewEventWithCachedData().setStatus(ResourceEvent.Status.ERROR).setMessage(e.getMessage());
            mEventBus.post(t);
        }
    }

    @WorkerThread
    public abstract Call<ResponseType> createCall();

    public abstract EventType getNewEventWithCachedData();

    @WorkerThread
    public abstract void saveCallResult(ResponseType response);
}
