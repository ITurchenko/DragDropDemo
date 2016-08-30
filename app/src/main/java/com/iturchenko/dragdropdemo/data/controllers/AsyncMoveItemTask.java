package com.iturchenko.dragdropdemo.data.controllers;

import android.os.AsyncTask;

import com.iturchenko.dragdropdemo.data.model.DataElement;
import com.iturchenko.dragdropdemo.utils.Gears;

class AsyncMoveItemTask extends AsyncTask<Void, Void, Void> {
    private final DbHelper dbHelper;
    private final Integer id;
    private final Integer id2;

    public AsyncMoveItemTask(DbHelper dbHelper, Integer id, Integer id2) {
        this.dbHelper = dbHelper;
        this.id = id;
        this.id2 = id2;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        Gears.imitateLongCalculations(200);

        DataElement dataElement = dbHelper.removeFromList(id);
        dbHelper.insertBefore(dataElement, dbHelper.getElement(id2));
        return null;
    }
}
