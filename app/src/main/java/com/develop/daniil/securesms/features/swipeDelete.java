package com.develop.daniil.securesms.features;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.develop.daniil.securesms.utils.MainAdapter;
import com.develop.daniil.securesms.utils.message;

import java.util.ArrayList;

public class swipeDelete  {
    private RecyclerView recyclerView;
    private Context context;
    private ArrayList<message> arrayList;
    private MainAdapter mainAdapter;

    public swipeDelete(Context context, ArrayList<message> arrayList, MainAdapter mainAdapter, RecyclerView recyclerView){
        this.recyclerView = recyclerView;
        this.context = context;
        this.arrayList = arrayList;
        this.mainAdapter = mainAdapter;
    }

}
