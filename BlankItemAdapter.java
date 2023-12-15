package com.example.nfc_app;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.provider.Contacts;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class BlankItemAdapter extends RecyclerView.Adapter<BlankItemAdapter.ViewHolder> {
    private Context context;
    private ArrayList<String> UID;

    public BlankItemAdapter(Context context, ArrayList<String> UID) {
        this.UID = UID;
        this.context = context;
    }

    @NonNull
    @Override
    public BlankItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.blank_item, parent, false);
        return new BlankItemAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BlankItemAdapter.ViewHolder holder, int position) {
        holder.blankUID.setText(UID.get(position));
        String chosenUID = UID.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogAdd(chosenUID);
            }
        });
    }

    @Override
    public int getItemCount() {
        return UID.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView blankUID;

        public ViewHolder(View itemView) {
            super(itemView);
            blankUID = itemView.findViewById(R.id.tvUIDBlank);
        }
    }

    public void DialogAdd(String uid) {
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog);
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
                if (edtName.getText().toString().isEmpty()) {
                    Toast.makeText(context, "Chưa nhập thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    String Name = edtName.getText().toString();
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
                    UserHelperClass userHelperClass = new UserHelperClass(uid, Name);
                    reference.child(uid).setValue(userHelperClass);
                    DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("blankuid");
                    reference1.child(uid).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(context, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
        dialog.show();
    }
}
