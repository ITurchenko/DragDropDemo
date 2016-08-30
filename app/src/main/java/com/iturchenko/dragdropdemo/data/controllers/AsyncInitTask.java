package com.iturchenko.dragdropdemo.data.controllers;

import android.os.AsyncTask;

import com.iturchenko.dragdropdemo.data.model.DataElement;
import com.iturchenko.dragdropdemo.utils.Gears;

import java.util.ArrayList;
import java.util.List;

class AsyncInitTask extends AsyncTask<Void, Void, List<Integer>> {
    private static final int MAX_ELEMENT_COUNT = 100;

    private DbHelper dbHelper;
    private CompleteListener listener;

    public AsyncInitTask(DbHelper dbHelper, CompleteListener listener) {
        this.dbHelper = dbHelper;
        this.listener = listener;
    }

    @Override
    protected List<Integer> doInBackground(Void... voids) {
        Gears.imitateLongCalculations(1000);

        if (dbHelper.getItemCount() == 0) {
            return generateStubData();
        } else {
            return loadFromDb();
        }
    }

    private List<Integer> generateStubData() {
        List<Integer> orderList = new ArrayList<>();
        ElementGenerator generator = new ElementGenerator();

        DataElement prev = null;
        for (int i = 0; i < MAX_ELEMENT_COUNT; i++) {
            DataElement element = generator.createNew(i);
            element.id = i;

            if (prev != null) {
                prev.nextID = element.id;
                element.prevID = prev.id;

                dbHelper.update(prev);
            }
            prev = element;

            dbHelper.insert(element);
            orderList.add(element.id);
        }

        return orderList;
    }

    private List<Integer> loadFromDb() {
        List<Integer> orderList = new ArrayList<>();
        DataElement element = dbHelper.getFirst();
        while (element != null) {
            orderList.add(element.id);
            element = dbHelper.getElement(element.nextID);
        }

        return orderList;
    }

    @Override
    protected void onPostExecute(List<Integer> orderList) {
        super.onPostExecute(orderList);

        if (listener != null) listener.onDone(orderList);
    }

    interface CompleteListener {
        void onDone(List<Integer> orderList);
    }
}
