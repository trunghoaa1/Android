package com.example.nfc_app;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class GuestActivity extends AppCompatActivity {
    ImageButton OpenGuest;
    ImageButton CloseGuest;
    ImageButton HoldGuest;
    Button btnOpenGuest;
    Button btnCloseGuest;
    Button btnHoldGuest;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guest_layout);
        OpenGuest = findViewById(R.id.ImgBtnOpenGuest);
        CloseGuest = findViewById(R.id.ImgBtnCloseGuest);
        HoldGuest = findViewById(R.id.ImgBtnHoldGuest);
        btnOpenGuest = findViewById(R.id.btnOpenGuest);
        btnCloseGuest = findViewById(R.id.btnCloseGuest);
        btnHoldGuest = findViewById(R.id.btnHoldGuest);

        OpenGuest.setOnClickListener(new View.OnClickListener() {
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

        btnOpenGuest.setOnClickListener(new View.OnClickListener() {
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

        btnCloseGuest.setOnClickListener(new View.OnClickListener() {
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

        CloseGuest.setOnClickListener(new View.OnClickListener() {
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

        HoldGuest.setOnClickListener(new View.OnClickListener() {
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

        btnHoldGuest.setOnClickListener(new View.OnClickListener() {
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
    }

    @Override
    protected void onDestroy() {
        Set0();
        super.onDestroy();
    }

    private void Set0() {
        reference = FirebaseDatabase.getInstance().getReference("Close");
        reference.setValue("0");
        reference = FirebaseDatabase.getInstance().getReference("Open");
        reference.setValue("0");
        reference = FirebaseDatabase.getInstance().getReference("Hold");
        reference.setValue("0");
    }
}