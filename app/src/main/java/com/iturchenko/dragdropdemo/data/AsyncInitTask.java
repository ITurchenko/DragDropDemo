package com.iturchenko.dragdropdemo.data;

import android.os.AsyncTask;
import android.util.Log;

import com.iturchenko.dragdropdemo.utils.Gears;

import java.util.ArrayList;
import java.util.List;

class AsyncInitTask extends AsyncTask<Void, Void, List<Integer>> {
    private static final int MAX_ELEMENT_COUNT = 20;

    private DbHelper dbHelper;
    private CompleteListener listener;

    public AsyncInitTask(DbHelper dbHelper, CompleteListener listener) {
        this.dbHelper = dbHelper;
        this.listener = listener;
    }

    @Override
    protected List<Integer> doInBackground(Void... voids) {
        Gears.imitateLongCalculations();

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

                Log.e("AA","Update" + prev);
                dbHelper.update(prev);
            }
            prev = element;

            Log.e("AA","Save "+element);

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
            Log.e("AA","Got -> "+element);
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
