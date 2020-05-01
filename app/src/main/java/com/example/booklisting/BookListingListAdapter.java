package com.example.booklisting;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class BookListingListAdapter extends ArrayAdapter<BookListing> {
    private Context mContext;
    private int mResource;

    public BookListingListAdapter(Context context, int resource, ArrayList<BookListing> books) {
        super(context, resource, books);
        this.mResource = resource;
        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //Getting data that needs to be displayed
        String title =getItem(position).getTitle();
        String subtitle = getItem(position).getSubtitle();
        String author = getItem(position).getAuthor();
        String url = getItem(position).getUrl();
        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent,false);

        //Initializing TextViews
        TextView titleView = convertView.findViewById(R.id.title);
        TextView subtitleView = convertView.findViewById(R.id.subtitle);
        TextView authorView = convertView.findViewById(R.id.author);

        //Putting data in the TextViews
        titleView.setText(title);
        subtitleView.setText(subtitle);
        authorView.setText(author);

        return convertView;
    }
}
