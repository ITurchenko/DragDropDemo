package com.iturchenko.dragdropdemo.data.controllers;

import android.os.AsyncTask;

import com.iturchenko.dragdropdemo.data.model.DataElement;
import com.iturchenko.dragdropdemo.utils.Gears;

class AsyncGetItemTask extends AsyncTask<Void, Void, DataElement> {
    private final DbHelper dbHelper;
    private final Integer id;
    private final ItemRequestCompleteListener completeListener;

    public AsyncGetItemTask(DbHelper dbHelper, Integer id, ItemRequestCompleteListener completeListener) {
        this.dbHelper = dbHelper;
        this.id = id;
        this.completeListener = completeListener;
    }

    @Override
    protected DataElement doInBackground(Void... voids) {
        Gears.imitateLongCalculations(50);
        return dbHelper.getElement(id);
    }

    @Override
    protected void onPostExecute(DataElement dataElement) {
        super.onPostExecute(dataElement);
        completeListener.onDone(dataElement);
    }
}
