package com.example.district13;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StickerLogInActivity extends AppCompatActivity {

    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sticker_log_in);
    }

    public void showStickerPage(View view) {
        Intent intent = new Intent(this, StickerActivity.class);
        editText = findViewById(R.id.editText_login_username);
        Log.d("StickerLogInActivity", editText.getText().toString());
        String username = editText.getText().toString();
        if (username.trim().length() == 0) {
            Toast.makeText(StickerLogInActivity.this, "Please Input Your Username!", Toast.LENGTH_SHORT).show();
            return;
        }

        intent.putExtra("Username", username);
        startActivity(intent);
    }
}