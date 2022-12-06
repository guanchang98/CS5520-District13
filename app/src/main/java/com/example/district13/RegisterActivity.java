package com.example.district13;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private Button register;

    private FirebaseAuth auth;

    //progressbar to display while registering
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        email = findViewById(R.id.editText_register_email);
        password = findViewById(R.id.editText_register_password);
        register = findViewById(R.id.button_register);

        auth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Registering...");


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textEmail = email.getText().toString();
                String textPassword = password.getText().toString();

                if (TextUtils.isEmpty(textEmail) || TextUtils.isEmpty(textPassword)) {
                    Toast.makeText(RegisterActivity.this, "Empty credentials!", Toast.LENGTH_SHORT).show();
                } else if (password.length() < 6) {
                    Toast.makeText(RegisterActivity.this, "The minimum length of password is 6!", Toast.LENGTH_SHORT).show();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(textEmail).matches()) {
                    //validate if input email address is in correct form
                    Toast.makeText(RegisterActivity.this, "Invalid email!", Toast.LENGTH_SHORT).show();
                } else {
                    registerUser(textEmail, textPassword);
                }
            }
        });
    }

    private void registerUser(String email, String password) {
        //input is valid, show progress and start registering
        progressDialog.show();

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    //success, dismiss dialog and start activity
                    progressDialog.dismiss();

                    //get current user
                    FirebaseUser user = auth.getCurrentUser();
                    //get user email and uid
                    String email = user.getEmail();
                    String uid = user.getUid();
                    //user is registered, store user info in firebase realtime database
                    HashMap<Object, String> hashMap = new HashMap<>();
                    //put info into hashmap
                    hashMap.put("uid", uid);
                    hashMap.put("email", email);
                    hashMap.put("name", "");
                    hashMap.put("avatar", "");
                    hashMap.put("posts", "");
                    hashMap.put("following", "");
                    hashMap.put("followers", "");
                    //get database instance
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    //path to store user data named "appUsers"
                    DatabaseReference reference = database.getReference("appUsers");
                    //put data within hashmap in database
                    reference.child(uid).setValue(hashMap);

                    Toast.makeText(RegisterActivity.this, "User registered successfully!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterActivity.this, FeedActivity.class));
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(RegisterActivity.this, "Failed to register!", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //error, dismiss progress and get error message
                progressDialog.dismiss();
                Toast.makeText(RegisterActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}