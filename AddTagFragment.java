package com.example.nfc_app;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AddTagFragment extends Fragment {
    RecyclerView rcvBlankUser;
    Button btnBack;
    ArrayList<String> UID;
    BlankItemAdapter blankItemAdapter;
    DatabaseReference reference;
    Button btnMore;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_tag, container, false);
        rcvBlankUser = view.findViewById(R.id.rcvBlankUser);
        btnBack = view.findViewById(R.id.btnBack);
        btnMore = view.findViewById(R.id.btnMore);
        UID = new ArrayList<>();
        blankItemAdapter = new BlankItemAdapter(this.getContext(), UID);
        rcvBlankUser.setAdapter(blankItemAdapter);
        rcvBlankUser.setLayoutManager(new LinearLayoutManager(getContext()));
        readBlankUser();
        btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_add_thu_cong);
                EditText edtUID = dialog.findViewById(R.id.UIDSign);
                EditText edtName = dialog.findViewById(R.id.NameSign);
                Button btnOK = dialog.findViewById(R.id.btnOKSign);
                Button btnCancel = dialog.findViewById(R.id.btnCancelSign);
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                btnOK.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (edtName.getText().toString().isEmpty() || edtUID.getText().toString().isEmpty()) {
                            Toast.makeText(getContext(), "Nhập thiếu thông tin", Toast.LENGTH_SHORT).show();
                        } else {
                            String Name = edtName.getText().toString();
                            String UID = edtUID.getText().toString();
                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
                            Query check = reference.orderByChild("uid").equalTo(UID);
                            check.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if (snapshot.exists()) {
                                        Toast.makeText(getContext(), "UID này đã tồn tại", Toast.LENGTH_SHORT).show();
                                    } else {
                                        UserHelperClass userHelperClass = new UserHelperClass(UID, Name);
                                        reference.child(UID).setValue(userHelperClass);
                                        Toast.makeText(getContext(), "Đăng kí thành công", Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                    }
                });
                dialog.show();
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), FirstFragment.class);
                startActivity(intent);
            }
        });
        Toast.makeText(

                getContext(), "Chọn một UID trống để đăng kí", Toast.LENGTH_SHORT).

                show();
        return view;
    }

    public void readBlankUser() {
        reference = FirebaseDatabase.getInstance().getReference("blankuid");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UID.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String blankUidFromDB = dataSnapshot.child("uid").getValue(String.class);
                    UID.add(blankUidFromDB);
                    blankItemAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}