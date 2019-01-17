package com.develop.daniil.securesms.utils;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.develop.daniil.securesms.R;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {
    ArrayList<messageRight> messages;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        public TextView text, time;

        public MyViewHolder(View messageRight) {
            super(messageRight);
            messageRight.setOnClickListener(this); //Need for CLICK
            text = messageRight.findViewById(R.id.text);
            time = messageRight.findViewById(R.id.time);
        }

        @Override
        public void onClick(View view) { //Easy way to do CLICK
            /*
                onClick
             */
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ChatAdapter(ArrayList<messageRight> messages) {
        this.messages = messages;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ChatAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View messageRight = LayoutInflater.from(parent.getContext()).inflate(R.layout.rigth_message_model, parent, false);

        MyViewHolder viewHolder = new MyViewHolder(messageRight);
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.text.setText(messages.get(position).getText());
        holder.time.setText(messages.get(position).getTime());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return messages.size();
    }

}
