package com.example.software_architect.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.software_architect.Adapter.UserAdapter;
import com.example.software_architect.Model.User;
import com.example.software_architect.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UsersFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private UserAdapter mUserAdapter;
    private List<User> mUsers;
    EditText search_users;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_users, container, false);

        mRecyclerView = mView.findViewById(R.id.recycle_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mUsers = new ArrayList<>();

        readUsers();

        search_users = mView.findViewById(R.id.search_users);
        search_users.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchUsers(s.toString().toLowerCase());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return mView;
    }

    private void searchUsers(String mToString) {
        final FirebaseUser mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        Query mQuery = FirebaseDatabase.getInstance().getReference("Users").orderByChild("search")
                .startAt(mToString)
                .endAt(mToString + "\uf8ff");
        mQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot mDataSnapshot) {
                mUsers.clear();
                for (DataSnapshot mSnapshot : mDataSnapshot.getChildren()) {
                    User user = mSnapshot.getValue(User.class);
                    assert user != null;
                    assert mFirebaseUser != null;
                    if (!user.getId().equals(mFirebaseUser.getUid())) {
                        mUsers.add(user);
                    }
                }
                mUserAdapter = new UserAdapter(getContext(), mUsers, false);
                mRecyclerView.setAdapter(mUserAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError mDatabaseError) {

            }
        });
    }

    private void readUsers() {
        final FirebaseUser mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference("Users");

        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot mDataSnapshot) {
                if(search_users.getText().toString().equals("")){
                mUsers.clear();
                for (DataSnapshot snapshot : mDataSnapshot.getChildren()) {
                    User user = snapshot.getValue(User.class);
                    assert user != null;
                    assert mFirebaseUser != null;
                    if (!user.getId().equals(mFirebaseUser.getUid())) {
                        mUsers.add(user);
                    }
                }
                mUserAdapter = new UserAdapter(getContext(), mUsers,false);
                mRecyclerView.setAdapter(mUserAdapter);

            }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError mDatabaseError) {

            }
        });
    }


}
