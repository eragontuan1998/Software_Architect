package com.example.software_architect.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.software_architect.Adapter.UserAdapter;
import com.example.software_architect.Model.Chat;
import com.example.software_architect.Model.User;
import com.example.software_architect.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.MissingResourceException;

public class ChatsFragment extends Fragment {
    private RecyclerView mRecyclerView;

    private UserAdapter mUserAdapter;
    private List<User> mUserList;

    FirebaseUser fuser;
    DatabaseReference mReference;

    private List<String> userList;
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView = inflater.inflate(R.layout.fragment_chats, container, false);

        mRecyclerView = mView.findViewById(R.id.recycle_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        fuser = FirebaseAuth.getInstance().getCurrentUser();

        userList = new ArrayList<>();

        mReference = FirebaseDatabase.getInstance().getReference("Chats");
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot mDataSnapshot) {
                userList.clear();

                for(DataSnapshot mSnapshot : mDataSnapshot.getChildren()){
                    Chat mChat = mSnapshot.getValue(Chat.class);

                    if (mChat.getSender().equals(fuser.getUid())) {
                        userList.add(mChat.getReceiver());
                    }
                    if (mChat.getReceiver().equals(fuser.getUid())) {
                        userList.add(mChat.getSender());
                    }
                }
                readChats();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError mDatabaseError) {

            }
        });

        return mView;

    }

    private void readChats() {
        mUserList = new ArrayList<>();

        mReference = FirebaseDatabase.getInstance().getReference("Users");

        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot mDataSnapshot) {
                mUserList.clear();
                for (DataSnapshot mSnapshot : mDataSnapshot.getChildren()) {
                    User mUser = mSnapshot.getValue(User.class);

                    //display 1 user from chats
                    for (String id : userList) {
                        if (mUser.getId().equals(id)) {
                            if (mUserList.size() != 0) {
                                for (User mUser1 : mUserList) {
                                    if (!mUser.getId().equals(mUser1.getId())) {
                                        mUserList.add(mUser);
                                    }
                                }
                            }else{
                                mUserList.add(mUser);
                            }
                        }
                    }
                }
                mUserAdapter = new UserAdapter(getContext(), mUserList,true);
                mRecyclerView.setAdapter(mUserAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError mDatabaseError) {

            }
        });
    }


}
