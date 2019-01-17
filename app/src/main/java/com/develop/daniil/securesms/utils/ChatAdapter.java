package com.develop.daniil.securesms.utils;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.develop.daniil.securesms.R;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {
    ArrayList<message> messages;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        public TextView text, time;

        public MyViewHolder(View message) {
            super(message);
            message.setOnClickListener(this); //Need for CLICK
            text = message.findViewById(R.id.text_TextView);
            time = message.findViewById(R.id.time_TextView);
        }

        @Override
        public void onClick(View view) { //Easy way to do CLICK
            /*
                onClick
             */
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ChatAdapter(ArrayList<message> messages) {
        this.messages = messages;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ChatAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View message = LayoutInflater.from(parent.getContext()).inflate(R.layout.rigth_message_model, parent, false);

        MyViewHolder viewHolder = new MyViewHolder(message);
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
