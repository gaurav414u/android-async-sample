package com.gauravbhola.asyncsample.data;


import com.google.gson.Gson;

import com.gauravbhola.asyncsample.data.model.AddContactsRequest;
import com.gauravbhola.asyncsample.data.model.AddContactsResponse;
import com.gauravbhola.asyncsample.data.model.Contact;
import com.gauravbhola.asyncsample.data.model.event.AddContactEvent;
import com.gauravbhola.asyncsample.data.model.event.FetchContactsEvent;
import com.gauravbhola.asyncsample.data.model.event.ResourceEvent;
import com.gauravbhola.asyncsample.data.remote.ApiService;
import com.gauravbhola.asyncsample.util.AppExecutors;
import com.gauravbhola.asyncsample.util.NetworkBoundTask;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;

@Singleton
public class ContactsManager {
    // Considering it to be same as of now
    final int mAccountId = 1;
    private Gson mGson;
    private AppExecutors mAppExecutors;
    private EventBus mEventBus;
    private ApiService mApiService;

    @Inject
    ContactsManager(Gson gson, AppExecutors appExecutors, EventBus eventBus, ApiService apiService) {
        mApiService = apiService;
        mAppExecutors = appExecutors;
        mEventBus = eventBus;
        mApiService = apiService;
    }

    public void fetchContacts() {
        mEventBus.post(new FetchContactsEvent(ResourceEvent.Status.LOADING, null, null));

        mAppExecutors.networkIO().execute(new NetworkBoundTask<List<Contact>, FetchContactsEvent>(mEventBus) {
            @Override
            public Call<List<Contact>> createCall() {
                return mApiService.getContacts(mAccountId+"");
            }

            @Override
            public FetchContactsEvent getNewEvent() {
                return new FetchContactsEvent();
            }

            @Override
            public void saveCallResult(List<Contact> response) {
                // Save in Db if required
            }
        });

    }

    public void addContact(final Contact contact) {
        mEventBus.post(new AddContactEvent(ResourceEvent.Status.LOADING, null, null));

        mAppExecutors.networkIO().execute(new NetworkBoundTask<AddContactsResponse, AddContactEvent>(mEventBus) {
            @Override
            public Call<AddContactsResponse> createCall() {
                AddContactsRequest request = new AddContactsRequest();
                request.setAccountId(mAccountId);
                request.setEmail(contact.getEmail());
                request.setName(contact.getName());
                request.setPhone(contact.getPhone());
                return mApiService.addContact(request);
            }

            @Override
            public AddContactEvent getNewEvent() {
                return new AddContactEvent();
            }

            @Override
            public void saveCallResult(AddContactsResponse response) {
                // Save in Db if required
            }
        });
    }
}
