package com.example.nfc_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {
    private Context context;
    private ArrayList<String> user;
    private ArrayList<String> time;

    public HistoryAdapter(Context context, ArrayList<String> user, ArrayList<String> time) {
        this.user = user;
        this.context = context;
        this.time = time;
    }

    @NonNull
    @Override
    public HistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.history_item, parent, false);
        return new HistoryAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter.ViewHolder holder, int position) {
        holder.usernameHistory.setText(user.get(position));
        holder.Time.setText(time.get(position));
    }

    @Override
    public int getItemCount() {
        return user.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView usernameHistory;
        public TextView Time;

        public ViewHolder(View itemView) {
            super(itemView);
            usernameHistory = itemView.findViewById(R.id.userNameHistory);
            Time = itemView.findViewById(R.id.Time);
        }
    }
}
