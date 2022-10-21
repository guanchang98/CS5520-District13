package com.example.district13;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APICall {
    // Base URL: https://api.punkapi.com/
    @GET("v2/beers?brewed_before=11-2012&abv_gt=6")
    Call<JSONResponse[]> getPunks();

}
