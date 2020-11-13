package com.mah_awad.chatapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mah_awad.chatapp.MessageActivity;
import com.mah_awad.chatapp.R;
import com.mah_awad.chatapp.model.Chat;
import com.mah_awad.chatapp.model.User;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    public static final int MSG_TYPE_LEFT = 0;
    public static final int MSG_TYPE_RIGHT = 1;
    private Context context ;
    private List<Chat> chats;
    private String imageurl;
    FirebaseUser firebaseUser;


    public MessageAdapter(Context context, List<Chat> chats,String imageurl) {
        this.context = context;
        this.chats = chats;
        this.imageurl = imageurl;
    }

    @NonNull
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // check if view type is right select layout chat_item_right or left select layout chat_item_left
        if (viewType == MSG_TYPE_RIGHT) {
            View view = LayoutInflater.from(context).inflate(R.layout.chat_item_right, parent, false);
            return new MessageAdapter.ViewHolder(view);
        }else {
            View view = LayoutInflater.from(context).inflate(R.layout.chat_item_left, parent, false);
            return new MessageAdapter.ViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.ViewHolder holder, int position) {

          // init class Chat
          Chat chat = chats.get(position);
          // show msg that user send or receive
          holder.show_message.setText(chat.getMessage());

          if (imageurl.equals("default")){
              holder.profile_image.setImageResource(R.drawable.ic_baseline_account_circle_24);
          }else {
              Glide.with(context).load(imageurl).into(holder.profile_image);
          }
          // check last message
          if (position == chats.size()-1){
              if (chat.isIsseen()){
                  holder.txt_seen.setText("Seen");
              }else {
                  holder.txt_seen.setText("Delivered");
              }
          }else {
              holder.txt_seen.setVisibility(View.GONE);
          }

    }

    @Override
    public int getItemCount() {
        return chats.size();
    }


    // create class to attach  user items with recyclerView
    public static  class  ViewHolder extends  RecyclerView.ViewHolder{

        CircleImageView profile_image;
        TextView show_message;
        TextView txt_seen;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            profile_image = itemView.findViewById(R.id.profile_image);
            show_message = itemView.findViewById(R.id.show_message);
            txt_seen = itemView.findViewById(R.id.txt_seen);
        }
    }

    @Override
    public int getItemViewType(int position) {
        // init firebaseUser
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        //check if user send msg return type right of msg shape or receiver return left shape of msg
        if (chats.get(position).getSender().equals(firebaseUser.getUid())){
            return MSG_TYPE_RIGHT;
        }else {
            return MSG_TYPE_LEFT;
        }
    }
}
