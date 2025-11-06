package com.example.twitxclone;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.twitxclone.model.Message;
import com.example.twitxclone.model.user;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

public class MessagesActivity extends AppCompatActivity {

    private final List<Message> messageList = new ArrayList<>();
    private MessageAdapter adapter;
    FirebaseDatabase database;
    EditText messageField;
    TextView userTextView;
    ListView listView;

    public void submitMessage(View view) {
        String message = messageField.getText().toString();
        String username = userTextView.getText().toString();

        Message newMessage = new Message();
        newMessage.setAuthor(username);
        newMessage.setMessage(message);
        newMessage.setPublishedAt(new GregorianCalendar().getTimeInMillis());

        DatabaseReference mDatabase = database.getReference("messages");

        mDatabase.child(mDatabase.push().getKey()).setValue(newMessage);

        messageField.getText().clear();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_messages);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        database = FirebaseDatabase.getInstance();
        Intent t = getIntent();
        String email = t.getStringExtra(user.N_KEY);
        String dob = t.getStringExtra(user.DOB_Key);

        messageField = findViewById(R.id.message_input);
        userTextView = findViewById(R.id.username);
        TextView  dobTV = findViewById(R.id.dob);
        listView = (ListView) findViewById(R.id.listView);
        adapter = new MessageAdapter(this, messageList);
        listView.setAdapter(adapter);

        userTextView.setText(email);
        dobTV.setText(dob);


    }
}