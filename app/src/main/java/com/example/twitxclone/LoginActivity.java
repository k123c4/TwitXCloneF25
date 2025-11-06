package com.example.twitxclone;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    Button signupButton;
    Button loginButton;
    FirebaseAuth auth;
    FirebaseDatabase database;

    View.OnClickListener loginListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            EditText editText = findViewById(R.id.user_field);
            final String username = editText.getText().toString();

            editText = findViewById(R.id.pass_field);
            final String password = editText.getText().toString();

            auth.signInWithEmailAndPassword(username, password)
                    .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){

                                FirebaseUser userN = auth.getCurrentUser();
                                DatabaseReference refUsers = database.getReference("user");
                                refUsers.orderByChild("email").equalTo(username).limitToLast(1).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        user U = snapshot.getChildren().iterator().next().getValue(user.class);
                                        String dob = U.getDob();
                                        getIntent().putExtra(user.N_KEY, username);
                                        getIntent().putExtra(user.DOB_Key,dob);
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });

                                Intent goToMessages = new Intent(getApplicationContext(), MessagesActivity.class);
                                startActivity(goToMessages);
                            }else{
                                Exception e = task.getException();
                                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
    };
    View.OnClickListener SignupListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(intent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        signupButton = findViewById(R.id.signup_button);
        signupButton.setOnClickListener(SignupListener);

        loginButton = findViewById(R.id.login_button);
        loginButton.setOnClickListener(loginListener);
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void signUp(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}