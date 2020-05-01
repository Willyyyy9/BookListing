package com.example.booklisting;

import android.content.Context;
import java.util.List;
import androidx.annotation.NonNull;
import android.content.AsyncTaskLoader;

public class BookListingLoader extends AsyncTaskLoader<List<BookListing>> {
    private String url;


    public BookListingLoader(@NonNull Context context, String url) {
        super(context);
        this.url = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }


    @Override
    public List<BookListing> loadInBackground() {
        if(url == null){
            return null;
        }
        List<BookListing> result = QueryUtils.fetchBookListingData(url);
        return result;
    }
}
