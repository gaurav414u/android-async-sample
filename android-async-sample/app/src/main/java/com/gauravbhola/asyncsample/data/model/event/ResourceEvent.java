package com.gauravbhola.asyncsample.data.model.event;


public class ResourceEvent<T> {
    public enum Status {SUCCESS, ERROR, LOADING}
    private Status mStatus;
    private T mData;
    private String mMessage;

    public ResourceEvent() {
        this(Status.LOADING, null, null);
    }

    public ResourceEvent(Status status, T data, String message) {
        mStatus = status;
        mData = data;
        mMessage = message;
    }

    public Status getStatus() {
        return mStatus;
    }

    public ResourceEvent<T> setStatus(Status status) {
        mStatus = status;
        return this;
    }

    public T getData() {
        return mData;
    }

    public ResourceEvent<T> setData(T data) {
        mData = data;

        return this;
    }

    public String getMessage() {
        return mMessage;
    }

    public ResourceEvent<T> setMessage(String message) {
        mMessage = message;
        return this;
    }
}
