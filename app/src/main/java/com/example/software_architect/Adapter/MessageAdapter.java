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
import com.example.software_architect.Model.Chat;
import com.example.software_architect.Model.User;
import com.example.software_architect.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    public static final int MSG_TYPE_LEFT = 0;
    public static final int MSG_TYPE_RIGHT = 1;

    private Context mContext;
    private List<Chat> mChats;
    private String imageURL;

    FirebaseUser fuser;

    public MessageAdapter(Context mContext, List<Chat> mChats, String mImageURL) {
        this.mContext = mContext;
        this.mChats = mChats;
        imageURL = mImageURL;
    }

    @NonNull
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup mViewGroup, int mI) {
        if (mI == MSG_TYPE_RIGHT) {
            View mView = LayoutInflater.from(mContext).inflate(R.layout.chat_item_right, mViewGroup, false);
            return new MessageAdapter.ViewHolder(mView);
        }else{
            View mView = LayoutInflater.from(mContext).inflate(R.layout.chat_item_left, mViewGroup, false);
            return new MessageAdapter.ViewHolder(mView);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.ViewHolder mViewHolder, int mI) {
        Chat chat = mChats.get(mI);

        mViewHolder.show_message.setText(chat.getMessage());

        if (imageURL.equals("default")) {
            mViewHolder.profile_image.setImageResource(R.mipmap.ic_launcher);
        }else{
            Glide.with(mContext).load(imageURL).into(mViewHolder.profile_image);
        }
        if (mI == mChats.size() - 1) {
            if (chat.isIsseen()) {
                mViewHolder.txt_seen.setText("Seen");
            }else{
                mViewHolder.txt_seen.setText("Deliverd");
            }
        }else{
            mViewHolder.txt_seen.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mChats.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView show_message;
        public ImageView profile_image;

        public TextView txt_seen;

        public ViewHolder(View itemView) {
            super(itemView);

            show_message = itemView.findViewById(R.id.show_message);
            profile_image = itemView.findViewById(R.id.profile_image);
            txt_seen = itemView.findViewById(R.id.txt_seen);
        }
    }

    @Override
    public int getItemViewType(int position) {
        fuser = FirebaseAuth.getInstance().getCurrentUser();
        if (mChats.get(position).getSender().equals(fuser.getUid())) {
            return MSG_TYPE_RIGHT;
        }else{
            return MSG_TYPE_LEFT;
        }
    }
}