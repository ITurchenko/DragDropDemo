package com.iturchenko.dragdropdemo.data;

import com.iturchenko.dragdropdemo.gui.MainActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Igor on 29.08.2016.
 */
public class DataProvider {
    private List<DataElement> values = new ArrayList<>();

    public DataProvider(MainActivity mainActivity) {
        for (int i = 0; i < 30; i++) {
            values.add(new DataElement(i+"", i));
        }
    }

    public int getItemCount() {
        return values.size();
    }

    public DataElement get(int position) {
        return values.get(position);
    }

    public long getItemId(int position) {
        return values.get(position).id;
    }

    public void moveItem(int fromPosition, int toPosition) {
        DataElement element = values.remove(fromPosition);
        values.add(toPosition, element);
    }
}
