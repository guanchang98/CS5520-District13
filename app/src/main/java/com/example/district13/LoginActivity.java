package com.example.district13;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private Button login;

    private FirebaseAuth auth;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.editText_register_email);
        password = findViewById(R.id.editText_register_password);
        login = findViewById(R.id.button_login);

        auth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Logging in...");

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textEmail = email.getText().toString();
                String textPassword = password.getText().toString();
                if (!Patterns.EMAIL_ADDRESS.matcher(textEmail).matches()) {
                    Toast.makeText(LoginActivity.this, "Invalid email!", Toast.LENGTH_SHORT).show();
                } else {
                    loginUser(textEmail, textPassword);
                }

            }
        });
    }

    private void loginUser(String email, String password) {
        progressDialog.show();
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    progressDialog.dismiss();

                    // get current user
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

                    Toast.makeText(LoginActivity.this, "User login successfully!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this, FeedActivity.class));
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(LoginActivity.this, "Wrong credentials!", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(LoginActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}