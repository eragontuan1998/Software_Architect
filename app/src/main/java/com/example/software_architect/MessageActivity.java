package com.example.software_architect;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.software_architect.Adapter.MessageAdapter;
import com.example.software_architect.Model.Chat;
import com.example.software_architect.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageActivity extends AppCompatActivity {

    CircleImageView profile_image;
    TextView username;

    FirebaseUser fuser;
    DatabaseReference mReference;

    ImageButton btn_send;
    EditText text_send;

    MessageAdapter mMessageAdapter;
    List<Chat> mChats;

    RecyclerView mRecyclerView;

    Intent mIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mRecyclerView = findViewById(R.id.recycle_view);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getApplicationContext());
        mLinearLayoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        profile_image = findViewById(R.id.profile_image);
        username = findViewById(R.id.username);
        btn_send = findViewById(R.id.btn_send);
        text_send = findViewById(R.id.text_send);


        mIntent = getIntent();
        final String userid = mIntent.getStringExtra("userid");
        fuser = FirebaseAuth.getInstance().getCurrentUser();
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = text_send.getText().toString();
                if (!msg.equals("")) {
                    sendMessage(fuser.getUid(), userid, msg);

                }else{
                    Toast.makeText(MessageActivity.this, "You can't send empty message", Toast.LENGTH_SHORT).show();
                }
                text_send.setText("");
            }
        });


        mReference = FirebaseDatabase.getInstance().getReference("Users").child(userid);

        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot mDataSnapshot) {
                User user = mDataSnapshot.getValue(User.class);
                username.setText(user.getUsername());
                if (user.getImageURL().equals("default")) {
                    profile_image.setImageResource(R.mipmap.ic_launcher);

                }else{
                    Glide.with(MessageActivity.this).load(user.getImageURL()).into(profile_image);
                }
                readMesages(fuser.getUid(),userid,user.getImageURL());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError mDatabaseError) {

            }
        });
    }

    private void sendMessage(String sender, String receiver, String message) {
        DatabaseReference mReference = FirebaseDatabase.getInstance().getReference();
        HashMap<String, Object> mHashMap = new HashMap<>();
        mHashMap.put("sender",sender);
        mHashMap.put("receiver",receiver);
        mHashMap.put("message",message);

        mReference.child("Chats").push().setValue(mHashMap);
    }

    private void readMesages(final String myid, final String userid, final String imageURL) {
        mChats = new ArrayList<>();

        mReference = FirebaseDatabase.getInstance().getReference("Chats");
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot mDataSnapshot) {
                mChats.clear();
                for (DataSnapshot mSnapshot : mDataSnapshot.getChildren()) {
                    Chat mChat = mSnapshot.getValue(Chat.class);
                    if (mChat.getReceiver().equals(myid) && mChat.getSender().equals(userid) ||
                        mChat.getReceiver().equals(userid) && mChat.getSender().equals(myid)){
                        mChats.add(mChat);
                    }
                    mMessageAdapter = new MessageAdapter(MessageActivity.this, mChats, imageURL);
                    mRecyclerView.setAdapter(mMessageAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError mDatabaseError) {

            }
        });
    }
}
