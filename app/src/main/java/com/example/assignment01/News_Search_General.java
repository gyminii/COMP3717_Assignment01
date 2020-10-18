package com.example.assignment01;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class News_Search_General {
    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("totalResults")
    @Expose
    private int totalResult;

    @SerializedName("articles")
    @Expose
    private ArrayList<Article> articles;

    public String getStatus() {
        return status;
    }
    public int getTotalResult() {
        return totalResult;
    }
    public ArrayList<Article> getArticles() {
        return articles;
    }
}
