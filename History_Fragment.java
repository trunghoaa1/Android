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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class History_Fragment extends Fragment {
    ImageView profileImage;
    ImageButton imgBack;
    TextView userName;
    RecyclerView HistoryRecycler;
    DatabaseReference reference;
    HistoryAdapter historyAdapter;
    ArrayList<String> users;
    ArrayList<String> time;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.history_fragment, container, false);
        users = new ArrayList<>();
        time = new ArrayList<>();
        profileImage = view.findViewById(R.id.userAvatar);
        userName = view.findViewById(R.id.userName);
        HistoryRecycler = view.findViewById(R.id.recyclerHistory);
        imgBack = view.findViewById(R.id.imgBack);
        ReadUser();
        historyAdapter = new HistoryAdapter(this.getContext(), users, time);
        HistoryRecycler.setAdapter(historyAdapter);
        HistoryRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
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
        reference = FirebaseDatabase.getInstance().getReference("History");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                users.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    String NameFromDB = snapshot1.child("name").getValue(String.class);
                    String TimeFromDB = snapshot1.child("time").getValue(String.class);
                    users.add(NameFromDB);
                    time.add(TimeFromDB);
                    historyAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
