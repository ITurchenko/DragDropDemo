package com.iturchenko.dragdropdemo.data.controllers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.iturchenko.dragdropdemo.data.model.DataElement;

class DbHelper extends SQLiteOpenHelper implements BaseColumns {
    private static final String DATABASE_NAME = "mydb";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "mytable";

    private static final String COLUMN_NAME = "name_column";
    private static final String COLUMN_NEXT = "next_id";
    private static final String COLUMN_PREV = "prev_id";

    public static final String[] STRUCTURE = new String[]{_ID, COLUMN_NAME, COLUMN_NEXT, COLUMN_PREV};

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    _ID + " INTEGER PRIMARY KEY, " +
                    COLUMN_NAME + " TEXT, " +
                    COLUMN_NEXT + " INTEGER, " +
                    COLUMN_PREV + " INTEGER " + " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + DATABASE_NAME;

    public DbHelper(Context context, SQLiteDatabase.CursorFactory factory) {
        this(context, factory, null);
    }

    public DbHelper(Context context, SQLiteDatabase.CursorFactory factory, DatabaseErrorHandler errorHandler) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public long getItemCount() {
        return DatabaseUtils.queryNumEntries(getReadableDatabase(), TABLE_NAME);
    }

    public void insert(DataElement element) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(_ID, element.id);
        contentValues.put(COLUMN_NAME, element.value);
        contentValues.put(COLUMN_NEXT, element.nextID);
        contentValues.put(COLUMN_PREV, element.prevID);

        getWritableDatabase().insert(TABLE_NAME, null, contentValues);
    }

    public void update(DataElement element) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NEXT, element.nextID);
        contentValues.put(COLUMN_PREV, element.prevID);

        SQLiteDatabase database = getWritableDatabase();
        database.update(TABLE_NAME, contentValues, _ID + " = ?", new String[] {Integer.toString(element.id)});
        database.close();
    }

    @Nullable
    public DataElement getElement(int id) {
        DataElement result = null;

        Cursor cursor = getReadableDatabase().query(TABLE_NAME, STRUCTURE, _ID + " = ?", new String[]{Integer.toString(id)}, null, null, null);
        if (cursor.moveToFirst()) {
            result = fillFromCursor(cursor);
        }
        cursor.close();
        return result;
    }

    @NonNull
    private DataElement fillFromCursor(Cursor cursor) {
        DataElement result;
        result = new DataElement(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
        result.id = cursor.getInt(cursor.getColumnIndex(_ID));
        result.nextID = cursor.getInt(cursor.getColumnIndex(COLUMN_NEXT));
        result.prevID = cursor.getInt(cursor.getColumnIndex(COLUMN_PREV));
        return result;
    }

    @Nullable
    public DataElement getFirst() {
        DataElement result = null;

        Cursor cursor = getReadableDatabase().query(TABLE_NAME, STRUCTURE, COLUMN_PREV + " = ?", new String[]{"-1"}, null, null, null);
        if (cursor.moveToFirst()) {
            result = fillFromCursor(cursor);
        }
        cursor.close();
        return result;
    }

    @Nullable
    public DataElement getLast() {
        DataElement result = null;

        Cursor cursor = getReadableDatabase().query(TABLE_NAME, STRUCTURE, COLUMN_NEXT + " = ?", new String[]{"-1"}, null, null, null);
        if (cursor.moveToFirst()) {
            result = fillFromCursor(cursor);
        }
        cursor.close();
        return result;
    }

    public DataElement removeFromList(int id) {
        DataElement element = getElement(id);

        DataElement prev = getElement(element.prevID);
        DataElement next = getElement(element.nextID);

        if (prev != null) prev.nextID = next == null ? -1 : next.id;
        if (next != null) next.prevID = prev == null ? -1 : prev.id;

        if (prev != null) update(prev);
        if (next != null) update(next);

        return element;
    }

    public void insertBefore(DataElement element, DataElement elementBefore) {
        if (elementBefore == null) {
            insertAfter(element, getLast());
            return;
        }

        element.nextID = elementBefore.id;
        element.prevID = elementBefore.prevID;

        update(element);

        DataElement prev = getElement(elementBefore.prevID);
        if (prev != null) {
            prev.nextID = element.id;
            update(prev);
        }

        elementBefore.prevID = element.id;
        update(elementBefore);
    }

    public void insertAfter(DataElement element, DataElement elementAfter) {
        element.nextID = elementAfter.nextID;
        element.prevID = elementAfter.id;

        update(element);

        DataElement next = getElement(elementAfter.nextID);
        if (next != null) {
            next.prevID = element.id;
            update(next);
        }

        elementAfter.nextID = element.id;
        update(elementAfter);
    }
}
