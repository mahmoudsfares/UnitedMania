package com.example.unitedmania.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NewsResponse {

    @SerializedName("articles")
    private List<News> articles;

    public NewsResponse(List<News> articles) {
        this.articles = articles;
    }

    public void setArticles(List<News> articles) {
        this.articles = articles;
    }

    public List<News> getArticles() {
        return articles;
    }
}
