package com.example.nfc_app;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FirstFragment extends AppCompatActivity {
    ImageButton imgHistory;
    ImageButton imgDeleteUser;
    ImageButton imgAddTag;
    ImageButton imgOpen;
    ImageButton imgClose;
    ImageButton imgHold;
    Button btnHistory;
    Button btnVoice;
    Button btnDeleteUser;
    Button btnAddTag;
    Button btnOpen;
    Button btnClose;
    Button btnHold;
    DatabaseReference reference;
    FrameLayout fragment_container_admin;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_first);
        btnVoice = findViewById(R.id.btnVoice);
        imgHistory = findViewById(R.id.ImgBtnHistory);
        imgDeleteUser = findViewById(R.id.ImgBtnDelete);
        imgAddTag = findViewById(R.id.ImgBtnAddTag);
        imgOpen = findViewById(R.id.ImgBtnOpen);
        imgClose = findViewById(R.id.ImgBtnClose);
        imgHold = findViewById(R.id.ImgBtnHold);
        btnHistory = findViewById(R.id.btnHistory);
        btnDeleteUser = findViewById(R.id.btnDelete);
        btnAddTag = findViewById(R.id.btnAddTag);
        btnOpen = findViewById(R.id.btnOpen);
        btnClose = findViewById(R.id.btnClose);
        btnHold = findViewById(R.id.btnHold);
        fragment_container_admin = findViewById(R.id.fragment_container_Admin);

        btnVoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent speech = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                speech.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                speech.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak");
                startActivityForResult(speech, 1);
            }
        });

        imgOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reference = FirebaseDatabase.getInstance().getReference("Open");
                reference.setValue("1");
                reference = FirebaseDatabase.getInstance().getReference("Close");
                reference.setValue("0");
                reference = FirebaseDatabase.getInstance().getReference("Hold");
                reference.setValue("0");
            }
        });

        btnOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reference = FirebaseDatabase.getInstance().getReference("Open");
                reference.setValue("1");
                reference = FirebaseDatabase.getInstance().getReference("Close");
                reference.setValue("0");
                reference = FirebaseDatabase.getInstance().getReference("Hold");
                reference.setValue("0");
            }
        });

        @Override
        protected void onActivityResult

        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reference = FirebaseDatabase.getInstance().getReference("Open");
                reference.setValue("2");
                reference = FirebaseDatabase.getInstance().getReference("Close");
                reference.setValue("0");
                reference = FirebaseDatabase.getInstance().getReference("Hold");
                reference.setValue("0");
            }
        });

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reference = FirebaseDatabase.getInstance().getReference("Open");
                reference.setValue("2");
                reference = FirebaseDatabase.getInstance().getReference("Close");
                reference.setValue("0");
                reference = FirebaseDatabase.getInstance().getReference("Hold");
                reference.setValue("0");
            }
        });

        btnHold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reference = FirebaseDatabase.getInstance().getReference("signMode");
                reference.setValue("1");
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        imgHold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reference = FirebaseDatabase.getInstance().getReference("Close");
                reference.setValue("0");
                reference = FirebaseDatabase.getInstance().getReference("Open");
                reference.setValue("0");
                reference = FirebaseDatabase.getInstance().getReference("Hold");
                reference.setValue("1");
            }
        });

        btnDeleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment selectedFragment = new SecondFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_Admin, selectedFragment).commit();
                imgHistory.setVisibility(View.GONE);
                imgDeleteUser.setVisibility(View.GONE);
                imgAddTag.setVisibility(View.GONE);
                imgOpen.setVisibility(View.GONE);
                imgClose.setVisibility(View.GONE);
                imgHold.setVisibility(View.GONE);
                btnHistory.setVisibility(View.GONE);
                btnAddTag.setVisibility(View.GONE);
                btnClose.setVisibility(View.GONE);
                btnDeleteUser.setVisibility(View.GONE);
                btnHold.setVisibility(View.GONE);
                btnOpen.setVisibility(View.GONE);
            }
        });

        imgDeleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment selectedFragment = new SecondFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_Admin, selectedFragment).commit();
                imgHistory.setVisibility(View.GONE);
                imgDeleteUser.setVisibility(View.GONE);
                imgAddTag.setVisibility(View.GONE);
                imgOpen.setVisibility(View.GONE);
                imgClose.setVisibility(View.GONE);
                imgHold.setVisibility(View.GONE);
                btnHistory.setVisibility(View.GONE);
                btnAddTag.setVisibility(View.GONE);
                btnClose.setVisibility(View.GONE);
                btnDeleteUser.setVisibility(View.GONE);
                btnHold.setVisibility(View.GONE);
                btnOpen.setVisibility(View.GONE);
            }
        });

        imgHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment selectedFragment = new History_Fragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_Admin, selectedFragment).commit();
                imgHistory.setVisibility(View.GONE);
                imgDeleteUser.setVisibility(View.GONE);
                imgAddTag.setVisibility(View.GONE);
                imgOpen.setVisibility(View.GONE);
                imgClose.setVisibility(View.GONE);
                imgHold.setVisibility(View.GONE);
                btnHistory.setVisibility(View.GONE);
                btnAddTag.setVisibility(View.GONE);
                btnClose.setVisibility(View.GONE);
                btnDeleteUser.setVisibility(View.GONE);
                btnHold.setVisibility(View.GONE);
                btnOpen.setVisibility(View.GONE);
            }
        });

        imgAddTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment selectedFragment = new AddTagFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_Admin, selectedFragment).commit();
                imgHistory.setVisibility(View.GONE);
                imgDeleteUser.setVisibility(View.GONE);
                imgAddTag.setVisibility(View.GONE);
                imgOpen.setVisibility(View.GONE);
                imgClose.setVisibility(View.GONE);
                imgHold.setVisibility(View.GONE);
                btnHistory.setVisibility(View.GONE);
                btnAddTag.setVisibility(View.GONE);
                btnClose.setVisibility(View.GONE);
                btnDeleteUser.setVisibility(View.GONE);
                btnHold.setVisibility(View.GONE);
                btnOpen.setVisibility(View.GONE);
            }
        });

        btnHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment selectedFragment = new History_Fragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_Admin, selectedFragment).commit();
                imgHistory.setVisibility(View.GONE);
                imgDeleteUser.setVisibility(View.GONE);
                imgAddTag.setVisibility(View.GONE);
                imgOpen.setVisibility(View.GONE);
                imgClose.setVisibility(View.GONE);
                imgHold.setVisibility(View.GONE);
                btnHistory.setVisibility(View.GONE);
                btnAddTag.setVisibility(View.GONE);
                btnClose.setVisibility(View.GONE);
                btnDeleteUser.setVisibility(View.GONE);
                btnHold.setVisibility(View.GONE);
                btnOpen.setVisibility(View.GONE);
            }
        });

        btnAddTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment selectedFragment = new AddTagFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_Admin, selectedFragment).commit();
                imgHistory.setVisibility(View.GONE);
                imgDeleteUser.setVisibility(View.GONE);
                imgAddTag.setVisibility(View.GONE);
                imgOpen.setVisibility(View.GONE);
                imgClose.setVisibility(View.GONE);
                imgHold.setVisibility(View.GONE);
                btnHistory.setVisibility(View.GONE);
                btnAddTag.setVisibility(View.GONE);
                btnClose.setVisibility(View.GONE);
                btnDeleteUser.setVisibility(View.GONE);
                btnHold.setVisibility(View.GONE);
                btnOpen.setVisibility(View.GONE);
            }
        });
    }

    @Override
    protected void onDestroy() {
        Set0();
        super.onDestroy();
    }

    private void Set0() {
        reference = FirebaseDatabase.getInstance().getReference("Close");
        reference.setValue(0);
        reference = FirebaseDatabase.getInstance().getReference("Open");
        reference.setValue(0);
        reference = FirebaseDatabase.getInstance().getReference("Hold");
        reference.setValue(0);
    }
}