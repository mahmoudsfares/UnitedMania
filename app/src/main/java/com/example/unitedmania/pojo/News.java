package com.example.unitedmania.pojo;


import com.google.gson.annotations.SerializedName;

public class News {
    // news source
    @SerializedName("source")
    private final Source mSource;
    // news title
    @SerializedName("title")
    private final String mTitle;
    // news details
    @SerializedName("content")
    private final String mDetails;
    // news source url
    @SerializedName("url")
    private final String mUrl;
    // news corresponding image
    @SerializedName("urlToImage")
    private final String mImageUrl;

    public News (Source source, String title, String details, String url, String imageUrl){
        mSource = source;
        mTitle = title;
        mDetails = details;
        mUrl = url;
        mImageUrl = imageUrl;
    }

    public Source getSource() {
        return mSource;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getDetails() {
        return mDetails;
    }

    public String getUrl() {
        return mUrl;
    }

    public String getImageUrl() {
        return mImageUrl;
    }
}
