package com.example.district13;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class About extends AppCompatActivity {
    TextView about;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        about = findViewById(R.id.about_us);
        String str =
                "Group Name: District 13 \n" +
                "Group Member: \n Aparna Krishnan\n Chang Guan\n Erdi Jiang";
        about.setText(str);
    }
}