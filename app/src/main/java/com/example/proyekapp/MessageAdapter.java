package com.example.proyekapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.viewHolder>{
    private Context context;
    private ArrayList<MessageModel> list;
    private AESDecrypt aesDecrypt = new AESDecrypt();

    public MessageAdapter(Context context, ArrayList<MessageModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MessageAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.message, parent, false);
        return new viewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.viewHolder holder, int position) {
        final MessageModel model = list.get(position);
        final String subject= model.getMessageSubject();
        final String massage= model.getMessageText();
        final String user= model.getMessageUser();
        final String time= model.getMessageTime();

        try {
            holder.subject.setText(aesDecrypt.AESDecrypt(subject));
            holder.pesan.setText(aesDecrypt.AESDecrypt(massage));
            holder.nama.setText(user);
            holder.waktu.setText(time);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView subject, pesan, nama, waktu;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            subject = itemView.findViewById(R.id.subyek);
            pesan = itemView.findViewById(R.id.message_text);
            nama = itemView.findViewById(R.id.message_user);
            waktu = itemView.findViewById(R.id.message_time);
        }
    }
}
