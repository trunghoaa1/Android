package com.example.nfc_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nfc_app.databinding.FragmentSecondBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SecondFragment extends Fragment {
    ImageView profileImage;
    ImageButton imgBack;
    TextView userName;
    RecyclerView userRecycler;
    DatabaseReference reference;
    UserAdapter userAdapter;
    ArrayList<String> users;
    ArrayList<String> uid;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_second, container, false);
        users = new ArrayList<>();
        uid = new ArrayList<>();
        profileImage = view.findViewById(R.id.userAvatar);
        userName = view.findViewById(R.id.userName);
        userRecycler = view.findViewById(R.id.recyclerUser);
        imgBack = view.findViewById(R.id.imgBack);
        ReadUser();
        userAdapter = new UserAdapter(this.getContext(), users, uid);
        userRecycler.setAdapter(userAdapter);
        userRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), FirstFragment.class);
                startActivity(intent);
            }
        });
        return view;
    }

    public void ReadUser() {
        reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                users.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    String NameFromDB = snapshot1.child("name").getValue(String.class);
                    String UidFromDB = snapshot1.child("uid").getValue(String.class);
                    users.add(NameFromDB);
                    uid.add(UidFromDB);
                    userAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}