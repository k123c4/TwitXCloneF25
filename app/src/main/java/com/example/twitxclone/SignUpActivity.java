package com.example.twitxclone;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.twitxclone.model.user;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.auth.User;


public class SignUpActivity extends AppCompatActivity {

    EditText usernameText;
    EditText passwordText;
    EditText dobText;

    FirebaseDatabase database;
    FirebaseAuth auth;
    OnCompleteListener<AuthResult> listener = new OnCompleteListener<>() {
        @Override
        public void onComplete(@NonNull Task task) {
            if (task.isSuccessful()) {
                DatabaseReference userReference = database.getReference("users");
                String uid = userReference.push().getKey();
                user user = new user();
                user.setDob(dobText.getText().toString());
                user.setName(usernameText.getText().toString());
                userReference.child(uid).setValue(user);
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class); //getApplicationContext() bc in listener

                startActivity(intent);
            } else {
                Toast.makeText(getApplicationContext(), "ERROR user not created", Toast.LENGTH_LONG).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_up);
        usernameText = findViewById(R.id.user_field);
        passwordText = findViewById(R.id.pass_field);
        dobText = findViewById(R.id.dob_field);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

    }


    public void onSignUp(View view) {
        String usern = usernameText.getText().toString();
        String password = passwordText.getText().toString();
        String dob = dobText.getText().toString();

        auth.createUserWithEmailAndPassword(usern, password).addOnCompleteListener(this,listener);


    }

}