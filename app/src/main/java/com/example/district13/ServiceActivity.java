package com.example.district13;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.district13.DataModel.JSONResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceActivity extends AppCompatActivity {
    RecyclerView PunkRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);

        // Retrofit builder
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.punkapi.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Log.v("ServiceActivity response test:", "After Retrofit");
        // instance for interface
        APICall apiCall = retrofit.create(APICall.class);
        Log.v("ServiceActivity response test:", "After apiCall");

        Call<JSONResponse[]> call = apiCall.getPunks();
        Log.v("ServiceActivity response test:", "After call");
        call.enqueue(new Callback<JSONResponse[]>() {
            @Override
            public void onResponse(Call<JSONResponse[]> call, Response<JSONResponse[]> response) {

                // Checking for the response
                if (response.code() != 200) {
                    // Handle the error
                    Log.v("ServiceActivity response test:", response.toString());
                    return;
                }

                // Get the data to view
                JSONResponse[] punks = response.body();

                for (JSONResponse jsonResponse : punks) {
                    String responseTest = "";
                    responseTest += jsonResponse.getName();
                    Log.v("ServiceActivity response test:", responseTest);
                }

            }

            @Override
            public void onFailure(Call<JSONResponse[]> call, Throwable t) {
                Log.v("ServiceActivity response test:", t.toString());
            }
        });
        Log.v("ServiceActivity response test:", "After enqueue");
    }
}
