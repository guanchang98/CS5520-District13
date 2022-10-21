package com.example.district13;

import com.example.district13.DataModel.DataModel;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APICall {
    // https://api.punkapi.com/v2/
    @GET("")
    Call<DataModel> getData();
}
