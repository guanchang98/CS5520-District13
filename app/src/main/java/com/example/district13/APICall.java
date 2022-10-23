package com.example.district13;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APICall {
    // Base URL: https://api.punkapi.com/
    @GET("v2/beers")
    Call<JSONResponse[]> getPunks(@Query("brewed_before") String date, @Query("abv_gt") String abv);



}
