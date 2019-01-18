package com.develop.daniil.securesms.utils;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.develop.daniil.securesms.ChatActivity;
import com.develop.daniil.securesms.R;

import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MyViewHolder> {
    ArrayList<message> messages; int layout;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        public TextView fromAddress, text, time;

        public MyViewHolder(View message) {
            super(message);
            message.setOnClickListener(this); //Need for CLICK
            fromAddress = message.findViewById(R.id.fromAddress_TextView);
            text = message.findViewById(R.id.mainText_TextView);
            time = message.findViewById(R.id.mainTime_TextView);
        }

        @Override
        public void onClick(View view) { //Easy way to do CLICK
            Intent intent = new Intent(view.getContext(), ChatActivity.class);
            intent.putExtra("number", fromAddress.getText().toString()); // send Username to Chat

            view.getContext().startActivity(intent);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MainAdapter(ArrayList<message> messages, int layout) {
        this.messages = messages;
        this.layout = layout;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MainAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View message = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);

        MyViewHolder viewHolder = new MyViewHolder(message);
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.fromAddress.setText(messages.get(position).getFromAddress());
        holder.text.setText(messages.get(position).getText());
        holder.time.setText(messages.get(position).getTime());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return messages.size();
    }

}