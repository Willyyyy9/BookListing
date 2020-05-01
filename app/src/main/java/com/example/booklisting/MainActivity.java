package com.example.booklisting;

import androidx.appcompat.app.AppCompatActivity;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<BookListing>> {
    public static final String LOG_TAG = MainActivity.class.getName();
    private String initialURL = "https://www.googleapis.com/books/v1/volumes?maxResults=15&q=";
    private String BOOKS_REQUEST_URL = "";
    private static final int BOOKLISTING_LOADER_ID = 1;
    private BookListingListAdapter adapter;
    private TextView mEmptyStateTextView;
    EditText searchEditText;
    View loadingIndicator;
    ListView booksListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initializing View
        searchEditText = findViewById(R.id.searchET);
        booksListView = (ListView) findViewById(R.id.listView);
        loadingIndicator = findViewById(R.id.loading_indicator);
        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        Button searchButton = findViewById(R.id.searchButton);

        //onClick of the search button
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Clears the emptyStateTextView and any previous information
                mEmptyStateTextView.setVisibility(GONE);
                adapter.clear();

                //creating the URL of the API that we'll get our data from
                String query = searchEditText.getText().toString();
                String[] queries = query.split(" ");
                String addOn = "";
                for(int i = 0;i<queries.length;i++){
                    addOn += queries[i];
                }
                BOOKS_REQUEST_URL = initialURL + addOn;

                //Accessing the API to get the data
                ConnectivityManager connectivityManager = (ConnectivityManager)
                        getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()){
                    //if the connection is successful
                    //start the loader and set the loading indicator to be visible
                    LoaderManager loaderManager = getLoaderManager();
                    Log.e(LOG_TAG, "Error response code: " + BOOKS_REQUEST_URL);
                    loaderManager.restartLoader(BOOKLISTING_LOADER_ID,null,MainActivity.this);
                    loadingIndicator.setVisibility(View.VISIBLE);
                }  else {
                    // Otherwise, display error
                    // First, hide loading indicator so error message will be visible and display no connection message in the empty view
                    loadingIndicator = findViewById(R.id.loading_indicator);
                    loadingIndicator.setVisibility(GONE);
                    booksListView.setEmptyView(mEmptyStateTextView);
                    mEmptyStateTextView.setText("No Internet Connection");
                }
            }
        });

        //Setting the listView and its onItemClickListener
        adapter = new BookListingListAdapter(this,R.layout.listview_adapter, new ArrayList<BookListing>());
        booksListView.setAdapter(adapter);
        booksListView.setClickable(true);
        booksListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //When you click on the item, it directs you to a link to the book
                BookListing bookListing = adapter.getItem(position);
                Uri bookListingUri = Uri.parse(bookListing.getUrl());
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW,bookListingUri);
                startActivity(websiteIntent);
            }
        });
        booksListView.setAdapter(adapter);
    }

    @Override
    public Loader<List<BookListing>> onCreateLoader(int id, Bundle args) {
        //starting the loader
        return new BookListingLoader(this, BOOKS_REQUEST_URL);

    }

    @Override
    public void onLoadFinished(Loader<List<BookListing>> loader, List<BookListing> books) {
        //Clearing any previous data
        adapter.clear();

        //Displaying data in the views
        if (books != null && !books.isEmpty()) {
            booksListView.setVisibility(View.VISIBLE);
            adapter.addAll(books);
        }else if(books == null || books.isEmpty()){
            mEmptyStateTextView.setText("No Books");
            mEmptyStateTextView.setVisibility(View.VISIBLE);
            booksListView.setVisibility(GONE);
            loadingIndicator.setVisibility(GONE);
        }
        loadingIndicator.setVisibility(GONE);
    }

    @Override
    public void onLoaderReset(Loader<List<BookListing>> loader) {
        adapter.clear();
    }
}
