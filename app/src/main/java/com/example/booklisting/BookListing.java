package com.example.booklisting;

public class BookListing {

    private String title = "";
    private String subtitle = "";
    private String author;
    private String url = "";


    public BookListing(String title, String subtitle, String author, String url){
        this.title = title;
        this.subtitle = subtitle;
        this.author = author;
        this.url = url;
    }


    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public String getUrl() {
        return url;
    }

    public String getAuthor() {
        return author;
    }
}
