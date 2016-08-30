package com.iturchenko.dragdropdemo.data;

import android.support.v7.widget.RecyclerView;

import com.iturchenko.dragdropdemo.gui.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class DataController implements AsyncInitTask.CompleteListener {
    private List<Integer> orderList = new ArrayList<>();
    private final DbHelper dbHelper;

    private int elementCount = 0;
    private RecyclerView.Adapter adapter;

    public DataController(MainActivity mainActivity) {
        dbHelper = new DbHelper(mainActivity, null);
        new AsyncInitTask(dbHelper, this).execute();
    }

    public int getItemCount() {
        return elementCount;
    }

    public DataElement get(int position) {
        return dbHelper.getElement(getItemId(position));
    }

    public int getItemId(int position) {
        if (position < 0 || position >= orderList.size()) return -1;
        return orderList.get(position);
    }

    public void moveItem(int fromPosition, int toPosition) {
        Integer id = orderList.remove(fromPosition);

        DataElement dataElement = dbHelper.removeFromList(id);
        dbHelper.insertBefore(dataElement, get(toPosition));

        orderList.add(toPosition, id);
    }

    @Override
    public void onDone(List<Integer> orderList) {
        this.orderList = orderList;
        elementCount = orderList.size();

        if (adapter != null) adapter.notifyDataSetChanged();
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        this.adapter = adapter;
    }
}
