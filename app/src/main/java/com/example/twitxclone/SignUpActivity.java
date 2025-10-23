package com.example.twitxclone;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


public class SignUpActivity extends AppCompatActivity {

    EditText usernameText;
    EditText passwordText;
    EditText dobText;

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
    }


    public void onSignUp(View view) {
        String usern = usernameText.getText().toString();
        String password = passwordText.getText().toString();
        String dob = dobText.getText().toString();


    }

}