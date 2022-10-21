package com.example.district13;

import com.example.district13.DataModel.DataModel;
import com.example.district13.DataModel.JSONResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APICall {
    // Base URL: https://api.punkapi.com/
    @GET("v2/beers/192")
    Call<JSONResponse[]> getPunks();

}
