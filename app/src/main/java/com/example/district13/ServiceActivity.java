package com.example.district13;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceActivity extends AppCompatActivity {
    RecyclerView punksRecyclerView;
    List<Punk> punkList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        punksRecyclerView = findViewById(R.id.recycler_view_punk);
        punkList = new ArrayList<>();

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
                    Punk curPunk = new Punk(jsonResponse.getName(), jsonResponse.getTagline(),
                            jsonResponse.getFirst_brewed(), jsonResponse.getDescription(),
                            jsonResponse.getImage_url());
                    punkList.add(curPunk);
                }

                putDataIntoRecyclerView(punkList);

            }

            @Override
            public void onFailure(Call<JSONResponse[]> call, Throwable t) {
                Log.v("ServiceActivity response test:", t.toString());
            }
        });
        Log.v("ServiceActivity response test:", "After enqueue");
        for (Punk punk : punkList) {
            Log.v("Punk List:", punk.getName());
        }

        Log.v("ServiceActivity list size:", String.valueOf(punkList.size()));

        punksRecyclerView = findViewById(R.id.recycler_view_punk);
        //This defines the way in which the RecyclerView is oriented
        punksRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Associates the adapter with the RecyclerView
        punksRecyclerView.setAdapter(new PunkAdapter(punkList, this));
    }

    private void putDataIntoRecyclerView(List<Punk> punkList) {
        punksRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //Associates the adapter with the RecyclerView
        punksRecyclerView.setAdapter(new PunkAdapter(punkList, this));
    }
}
