package com.iturchenko.dragdropdemo.data;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.iturchenko.dragdropdemo.gui.MainActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataProvider {
    private static final int MAX_ELEMENT_COUNT = 5;

    private List<DataElement> values = new ArrayList<>();
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
            values.add(element);

            if (prev != null) {
                prev.nextID = element.id;
                element.prevID = prev.id;
            }
            prev = element;

            dbHelper.insert(element);
        }
    }

    private void loadFromDb() {
        Map<Integer, DataElement> map = new HashMap<>();
        for (DataElement element : dbHelper.getAll()) {
            Log.e("AA","Load "+element);
            map.put(element.prevID, element);
        }

        Log.e("AA","-> "+map);
    }

    public int getItemCount() {
//        return (int) dbHelper.getItemCount();
        return 0;
    }

    public DataElement get(int position) {
        return values.get(position);
    }

    public long getItemId(int position) {
        return values.get(position).id;
    }

    public void moveItem(int fromPosition, int toPosition) {
        Log.e("AA", "Move from "+fromPosition+" to "+toPosition);

        DataElement element = values.remove(fromPosition);
        values.add(toPosition, element);
    }
}
