package com.example.software_architect;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
    MaterialEditText username,email,password;
    Button tbn_register;

    FirebaseAuth mAuth;
    DatabaseReference mReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Toolbar mToolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Register");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        username=(MaterialEditText)findViewById(R.id.username);
        email=(MaterialEditText)findViewById(R.id.email);
        password=(MaterialEditText)findViewById(R.id.password);
        tbn_register=(Button)findViewById(R.id.btn_register);

        mAuth = FirebaseAuth.getInstance();

        tbn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_username= username.getText().toString();
                String txt_email = email.getText().toString();
                String txt_password = password.getText().toString();
                if(TextUtils.isEmpty(txt_username)|| TextUtils.isEmpty(txt_email)||TextUtils.isEmpty(txt_password)){
                    Toast.makeText(RegisterActivity.this, "All fileds are required", Toast.LENGTH_SHORT).show();
                }else if(txt_password.length()<6){
                    Toast.makeText(RegisterActivity.this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show();
                }else{
                    register(txt_username,txt_email,txt_password);
                }
            }
        });
    }
    private void register(final String username, String email, String password){
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> mTask) {
                if(mTask.isSuccessful()){
                    FirebaseUser mFirebaseUser = mAuth.getCurrentUser();
                    assert mFirebaseUser != null;
                    String userid = mFirebaseUser.getUid();

                    mReference = FirebaseDatabase.getInstance().getReference("Users").child(userid);

                    HashMap<String,String> mHashMap = new HashMap<>();
                    mHashMap.put("id", userid);
                    mHashMap.put("username", username);
                    mHashMap.put("imageURL","default");
                    mHashMap.put("status","offline");
                    mReference.setValue(mHashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> mTask) {
                            if(mTask.isSuccessful()){
                                Intent mIntent = new Intent(RegisterActivity.this, MainActivity.class);
                                mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(mIntent);
                                finish();
                            }
                        }
                    });
                } else{
                    Toast.makeText(RegisterActivity.this, "You can't register woth this email or password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
