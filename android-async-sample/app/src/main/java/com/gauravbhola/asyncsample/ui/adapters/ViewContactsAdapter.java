package com.gauravbhola.asyncsample.ui.adapters;

import com.gauravbhola.asyncsample.R;
import com.gauravbhola.asyncsample.data.model.Contact;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ViewContactsAdapter extends RecyclerView.Adapter<ViewContactsAdapter.ContactViewHolder> {

    private final Context mContext;
    private List<Contact> mItems;

    public ViewContactsAdapter(Context context, List<Contact> items) {
        mContext = context;
        mItems = items;
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_contact, parent, false);
        return new ContactViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ContactViewHolder holder, int position) {
        holder.bind(mItems.get(position));
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }


    public static final class ContactViewHolder extends RecyclerView.ViewHolder {
        private TextView mNameView;
        private TextView mEmailView;
        private TextView mPhoneView;

        ContactViewHolder(View v) {
            super(v);
            mNameView = v.findViewById(R.id.tv_name);
            mEmailView = v.findViewById(R.id.tv_email);
            mPhoneView = v.findViewById(R.id.tv_phone);
        }

        public void bind(Contact contact) {
            mNameView.setText(contact.getName());
            mEmailView.setText(contact.getEmail());
            mPhoneView.setText(contact.getPhone());
        }
    }
}
