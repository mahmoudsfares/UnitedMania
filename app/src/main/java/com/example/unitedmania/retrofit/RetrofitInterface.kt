package com.example.unitedmania.retrofit;

import com.example.unitedmania.pojo.NewsResponse;

import retrofit2.Call;
import retrofit2.http.GET;

interface RetrofitInterface {
    @GET ("everything?q=manchester%20united%7Cman%20utd%7Cman%20united%7Cmanchester%20utd&apiKey=47ba773d0f1147438a3d6244bc7f1e5e&sortBy=publishedAt&pageSize=100&language=en&fbclid=IwAR215STnwzrUekxittTkbK3Vn8INjsOE0Zl28uctn2lDwpOelkKVurJvWwc")
    fun getAllNews(): Call<NewsResponse>
}
