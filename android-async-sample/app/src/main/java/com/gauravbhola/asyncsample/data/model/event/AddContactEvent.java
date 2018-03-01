package com.gauravbhola.asyncsample.data.model.event;


import com.gauravbhola.asyncsample.data.model.AddContactsResponse;


public class AddContactEvent extends ResourceEvent<AddContactsResponse> {
    public AddContactEvent() {
        super();
    }

    public AddContactEvent(Status status, AddContactsResponse data, String message) {
        super(status, data, message);
    }
}


