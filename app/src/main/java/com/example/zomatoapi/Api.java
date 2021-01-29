package com.example.zomatoapi;


import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public class Api {

    private static final String url = "https://developers.zomato.com/api/v2.1/";
    public static PostService postService = null;

    public static PostService getService()
    {
        if (postService == null){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            postService = retrofit.create(PostService.class);
        }
        return postService;
    }

    public interface PostService{

        @Headers({
                "Accept:application/json",
                "user-key: 1b3c8b37ea96785391fa55c288ac385c"
        })
        @GET("search")
        Call<SearchZomato> searchRestaurantbyName(@Query("q") String name);
    }
}
