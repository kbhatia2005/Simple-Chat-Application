package com.example.chattingavapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class messagesAdpter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    ArrayList<msgModelclass> messagesArrayList;
    String senderImg;
    String reciverIImg;
    int ITEM_SEND = 1;
    int ITEM_RECIVE = 2;

    // Constructor to accept context, messages list, sender image, and receiver image
    public messagesAdpter(Context context, ArrayList<msgModelclass> messagesArrayList, String senderImg, String reciverIImg) {
        this.context = context;
        // Initialize the list to avoid NullPointerException
        this.messagesArrayList = messagesArrayList != null ? messagesArrayList : new ArrayList<>();
        this.senderImg = senderImg;
        this.reciverIImg = reciverIImg;
    }

    // Second constructor (which was incomplete)
    public messagesAdpter(Context context, ArrayList<msgModelclass> messagesArrayList) {
        this.context = context;
        // Initialize the list to avoid NullPointerException
        this.messagesArrayList = messagesArrayList != null ? messagesArrayList : new ArrayList<>();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == ITEM_SEND) {
            View view = LayoutInflater.from(context).inflate(R.layout.sender_layout, parent, false);
            return new senderViewHolder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.reciver_layout, parent, false);
            return new reciverViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        msgModelclass message = messagesArrayList.get(position);

        if (holder.getClass() == senderViewHolder.class) {
            senderViewHolder senderViewHolder = (senderViewHolder) holder;
            senderViewHolder.msgtxt.setText(message.getMessage());
            // Load sender image
            Picasso.get().load(senderImg).into(senderViewHolder.circleImageView);
        } else {
            reciverViewHolder reciverViewHolder = (reciverViewHolder) holder;
            reciverViewHolder.msgtxt.setText(message.getMessage());
            // Load receiver image
            Picasso.get().load(reciverIImg).into(reciverViewHolder.circleImageView);
        }
    }

    @Override
    public int getItemCount() {
        return messagesArrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        msgModelclass message = messagesArrayList.get(position);
        // Check if the message was sent by the current user
        if (FirebaseAuth.getInstance().getUid().equals(message.getSenderid())) {
            return ITEM_SEND;
        } else {
            return ITEM_RECIVE;
        }
    }

    // ViewHolder for sender messages
    class senderViewHolder extends RecyclerView.ViewHolder {
        CircleImageView circleImageView;
        TextView msgtxt;

        public senderViewHolder(@NonNull View itemView) {
            super(itemView);
            circleImageView = itemView.findViewById(R.id.profilerggg); // Replace with your image view ID
            msgtxt = itemView.findViewById(R.id.msgsendertyp); // Replace with your text view ID
        }
    }

    // ViewHolder for receiver messages
    class reciverViewHolder extends RecyclerView.ViewHolder {
        CircleImageView circleImageView;
        TextView msgtxt;

        public reciverViewHolder(@NonNull View itemView) {
            super(itemView);
            circleImageView = itemView.findViewById(R.id.pro); // Replace with your image view ID
            msgtxt = itemView.findViewById(R.id.recivertextset); // Replace with your text view ID
        }
    }
}
