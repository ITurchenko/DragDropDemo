package com.iturchenko.dragdropdemo.data.controllers;

import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;

import com.iturchenko.dragdropdemo.gui.main.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class DataController {
    private List<Integer> orderList = new ArrayList<>();
    private final DbHelper dbHelper;

    private int elementCount = 0;
    private RecyclerView.Adapter adapter;

    public DataController(MainActivity mainActivity) {
        dbHelper = new DbHelper(mainActivity, null);
        new AsyncInitTask(dbHelper, new AsyncInitTask.CompleteListener() {
            @Override
            public void onDone(List<Integer> orderList) {
                onInitializationDone(orderList);
            }
        }).execute();
    }

    public int getItemCount() {
        return elementCount;
    }

    public AsyncTask get(int position, ItemRequestCompleteListener completeListener) {
        AsyncGetItemTask itemTask = new AsyncGetItemTask(dbHelper, getItemId(position), completeListener);
        itemTask.execute();

        return itemTask;
    }

    public int getItemId(int position) {
        if (position < 0 || position >= orderList.size()) return -1;
        return orderList.get(position);
    }

    public void moveItem(int fromPosition, int toPosition) {
        Integer id = orderList.remove(fromPosition);

        new AsyncMoveItemTask(dbHelper, id, getItemId(toPosition)).execute();

        orderList.add(toPosition, id);
    }


    private void onInitializationDone(List<Integer> orderList) {
        this.orderList = orderList;
        elementCount = orderList.size();

        if (adapter != null) adapter.notifyDataSetChanged();
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        this.adapter = adapter;
    }
}
