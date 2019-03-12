package com.example.software_architect.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.software_architect.MessageActivity;
import com.example.software_architect.Model.User;
import com.example.software_architect.R;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    private Context mContext;
    private List<User> mUsers;
    private boolean ischat;
    public UserAdapter(Context mContext, List<User> mUsers,boolean ischat) {
        this.mContext = mContext;
        this.mUsers = mUsers;
        this.ischat=ischat;
    }

    @NonNull
    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup mViewGroup, int mI) {
        View mView = LayoutInflater.from(mContext).inflate(R.layout.user_item, mViewGroup, false);
        return new UserAdapter.ViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.ViewHolder mViewHolder, int mI) {
        final User user = mUsers.get(mI);
        mViewHolder.username.setText(user.getUsername());
        if (user.getImageURL().equals("default")) {
            mViewHolder.profile_image.setImageResource(R.mipmap.ic_launcher);
        }else{
            Glide.with(mContext).load(user.getImageURL()).into(mViewHolder.profile_image);
        }

        if(ischat){
            if (user.getStatus().equals("online")) {
                mViewHolder.img_on.setVisibility(View.VISIBLE);
                mViewHolder.img_off.setVisibility(View.GONE);
            }else{
                mViewHolder.img_on.setVisibility(View.GONE);
                mViewHolder.img_off.setVisibility(View.VISIBLE);
            }
        }else {
            mViewHolder.img_on.setVisibility(View.GONE);
            mViewHolder.img_off.setVisibility(View.GONE);
        }

        mViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MessageActivity.class);
                intent.putExtra("userid", user.getId());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView username;
        public ImageView profile_image;
        private ImageView img_on,img_off;
        public ViewHolder(View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.username);
            profile_image = itemView.findViewById(R.id.profile_image);
            img_on=itemView.findViewById(R.id.img_on);
            img_off=itemView.findViewById(R.id.img_off);;
        }
    }
}
