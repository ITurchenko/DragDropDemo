package com.iturchenko.dragdropdemo.data.model;

public class DataElement {
    public String value;
    public int id;

    public int nextID = -1;
    public int prevID = -1;

    public DataElement(String value) {
        this.value = value;
    }

    public DataElement(String value, int id) {
        this.value = value;
        this.id = id;
    }

    @Override
    public String toString() {
        return "DataElement{" +
                "value='" + value + '\'' +
                ", id=" + id +
                ", nextID=" + nextID +
                ", prevID=" + prevID +
                '}';
    }
}
