package com.gauravbhola.asyncsample.data.remote;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

import com.gauravbhola.asyncsample.data.model.AddContactsRequest;
import com.gauravbhola.asyncsample.data.model.AddContactsResponse;
import com.gauravbhola.asyncsample.data.model.Contact;

import java.util.List;

public interface ApiService {
    @POST("/addContact")
    Call<AddContactsResponse> addContact(@Body AddContactsRequest request);

    @GET("/contacts")
    Call<List<Contact>> getContacts(@Query("accountId") String accountId);
}
