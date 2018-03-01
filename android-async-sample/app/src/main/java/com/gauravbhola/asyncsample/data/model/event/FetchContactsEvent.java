package com.gauravbhola.asyncsample.data.model.event;


import com.gauravbhola.asyncsample.data.model.Contact;

import java.util.List;

public class FetchContactsEvent extends ResourceEvent<List<Contact>> {
    public FetchContactsEvent() {
        super();
    }

    public FetchContactsEvent(Status status, List<Contact> data, String message) {
        super(status, data, message);
    }
}


