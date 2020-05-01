package com.example.booklisting;

import android.text.TextUtils;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import static com.example.booklisting.MainActivity.LOG_TAG;

public final class QueryUtils {

    private QueryUtils(){
        //Empty Constructor
    }

    public static ArrayList<BookListing> extractDataFromJSON(String bookListingJSON){

        if(TextUtils.isEmpty(bookListingJSON)){
            return null;
        }

        //Gets the data from the API
        ArrayList<BookListing> books = new ArrayList<>();
        try {
            JSONObject root  =  new JSONObject(bookListingJSON);
            int itemsNumber = root.getInt("totalItems");
            if(itemsNumber>0){
                JSONArray items = root.getJSONArray("items");
                for(int i = 0;i<items.length();i++){
                    JSONObject book = items.getJSONObject(i);
                    JSONObject volumeInfo = book.getJSONObject("volumeInfo");
                    String subtitle = "";
                    try{
                        subtitle = volumeInfo.getString("subtitle");
                    }catch(JSONException e){
                        Log.e("Query","Exception" + e);
                    }
                    String title = volumeInfo.getString("title");
                    JSONArray authors = new JSONArray();
                    try{
                        authors = volumeInfo.getJSONArray("authors");
                    }catch (Exception e){
                        Log.e("Query","Exception" + e);
                    }

                    String author = "";
                    for(int j = 0;j<authors.length();j++){
                        if(authors.length() != j + 1){
                            author += authors.getString(j) + " ";
                        }else {
                            author += authors.getString(j);
                        }
                    }
                    String infoLink = volumeInfo.getString("infoLink");
                    books.add(new BookListing(title, subtitle, author, infoLink));
            }
            }
        }catch(Exception e){
            Log.e("QueryUtils", "Problem parsing the bookListing JSON results", e);
        }
        return books;
    }


    private static URL createURL(String stringURL){
        URL url = null;
        try{
            url = new URL(stringURL);
        }catch (Exception e){
            Log.e("QueryUtils", "Problem building the URL ", e);
        }
        return url;
    }



    private static String makeHttpRequest(URL url) throws IOException {

        String jsonResponse = "";
        if(url == null){
            return jsonResponse;
        }

        //Makes the connection with the API
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try{
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if(urlConnection.getResponseCode() == 200){
                //Gets the response in the of String
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        }catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {

            //Disconnects the connection and closes the inputStream regardless the connection was successful or not
            if(urlConnection != null){
                urlConnection.disconnect();
            }
            if(inputStream != null){
                inputStream.close();
            }
        }
        return jsonResponse;
    }



    private static String readFromStream(InputStream inputStream) throws IOException {

        StringBuilder stringBuilder = new StringBuilder();
        if(inputStream != null){
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = bufferedReader.readLine();
            while (line!=null){
                stringBuilder.append(line);
                line = bufferedReader.readLine();
            }
        }
        return stringBuilder.toString();
    }




    public static List<BookListing> fetchBookListingData(String requestURL){

        //A function that has all the necessary steps to get data
        URL url = createURL(requestURL);
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        }catch (IOException e){
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }
        List<BookListing> books =extractDataFromJSON(jsonResponse);
        return books;
    }
}
