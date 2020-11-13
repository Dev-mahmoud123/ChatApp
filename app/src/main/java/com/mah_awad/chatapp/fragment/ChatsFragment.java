package com.mah_awad.chatapp.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.mah_awad.chatapp.R;
import com.mah_awad.chatapp.adapter.UserAdapter;
import com.mah_awad.chatapp.model.Chat;
import com.mah_awad.chatapp.model.ChatList;
import com.mah_awad.chatapp.model.User;
import com.mah_awad.chatapp.notification.Token;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;


public class ChatsFragment extends Fragment {

    private RecyclerView recyclerView ;
    private UserAdapter userAdapter ;
    private List<User> users;
    FirebaseUser firebaseUser;
    DatabaseReference reference;
    private List<ChatList> userList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chats,container,false);

        recyclerView  = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        userList = new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference("Chatlist").child(firebaseUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userList.clear();
                for (DataSnapshot dataSnapshot  : snapshot.getChildren()){
                    ChatList chatList = dataSnapshot.getValue(ChatList.class);
                    userList.add(chatList);
                }

                chatList();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        updateToken(FirebaseInstanceId.getInstance().getToken());

        return view;
    }

    private void updateToken(String token){
        DatabaseReference reference  = FirebaseDatabase.getInstance().getReference("Token");
        Token token1 =  new Token(token);
        reference.child(firebaseUser.getUid()).setValue(token1);

    }

    // create fun chat list to add user 
    private void chatList() {

        users = new ArrayList<>();
        reference =FirebaseDatabase.getInstance().getReference("users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                users.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    User user = dataSnapshot.getValue(User.class);
                    for (ChatList chatList : userList){
                        assert user != null;
                        if (user.getId().equals(chatList.getId())){
                            users.add(user);
                        }
                    }
                }
                userAdapter =  new UserAdapter(getContext(),users,true);
                recyclerView.setAdapter(userAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}