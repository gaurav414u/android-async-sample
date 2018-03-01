package com.gauravbhola.asyncsample.data;

import com.gauravbhola.asyncsample.data.model.AddContactsRequest;
import com.gauravbhola.asyncsample.data.model.AddContactsResponse;
import com.gauravbhola.asyncsample.data.model.Contact;
import com.gauravbhola.asyncsample.data.remote.ApiService;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Response;

@Singleton
public class ContactsManager {
    // Considering it to be same as of now
    final int mAccountId = 1;
    private ApiService mApiService;

    @Inject
    ContactsManager(ApiService apiService) {
        mApiService = apiService;
    }

    public List<Contact> fetchContacts() {
        try {
            Response<List<Contact>> r = mApiService.getContacts(mAccountId + "").execute();
            if (r.isSuccessful()) {
                return r.body();
            }
            return null;
        } catch (IOException e) {
            return null;
        }
    }

    public AddContactsResponse addContact(final Contact contact) {
        try {
            AddContactsRequest request = new AddContactsRequest();
            request.setAccountId(mAccountId);
            request.setEmail(contact.getEmail());
            request.setName(contact.getName());
            request.setPhone(contact.getPhone());
            Response<AddContactsResponse> r = mApiService.addContact(request).execute();
            if (r.isSuccessful()) {
                return r.body();
            }
            return null;
        } catch (IOException e) {
            return null;
        }
    }
}
