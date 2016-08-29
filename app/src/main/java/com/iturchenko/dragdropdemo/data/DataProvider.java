package com.iturchenko.dragdropdemo.data;

import android.util.Log;

import com.iturchenko.dragdropdemo.gui.MainActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataProvider {
    private static final int MAX_ELEMENT_COUNT = 5;

    private List<Integer> orderList = new ArrayList<>();
    private final DbHelper dbHelper;

    public DataProvider(MainActivity mainActivity) {
        dbHelper = new DbHelper(mainActivity, null);

        Log.e("AA","total -> "+ dbHelper.getItemCount());

        if (dbHelper.getItemCount() == 0) {
            generateStubData();
        } else {
            loadFromDb();
        }
    }

    private void generateStubData() {
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
    }

    private void loadFromDb() {
        DataElement element = dbHelper.getFirst();
        while (element != null) {
            orderList.add(element.id);
            Log.e("AA","Got -> "+element);
            element = dbHelper.getElement(element.nextID);
        }
    }

    public int getItemCount() {
        return (int) dbHelper.getItemCount();
//        return 0;
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
}
