package com.example.district13;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class FeedActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    FeedFragment feedFragment = new FeedFragment();
    FriendsFragment friendsFragment = new FriendsFragment();
    PostFragment postFragment = new PostFragment();
    AccountFragment accountFragment = new AccountFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);


        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.page_feed:
                        getSupportFragmentManager().beginTransaction().replace(R.id.teatalks_container, feedFragment).commit();
                        return true;

                    case R.id.page_friend_list:
                        getSupportFragmentManager().beginTransaction().replace(R.id.teatalks_container, friendsFragment).commit();
                        return true;

                    case R.id.page_create_post:
                        getSupportFragmentManager().beginTransaction().replace(R.id.teatalks_container, postFragment).commit();
                        return true;

                    case R.id.page_account:
                        getSupportFragmentManager().beginTransaction().replace(R.id.teatalks_container, accountFragment).commit();
                        return true;
                }
                return false;
            }
        });

        bottomNavigationView.setSelectedItemId(R.id.page_feed);
    }
}