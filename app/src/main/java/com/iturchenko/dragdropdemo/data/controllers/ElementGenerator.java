package com.iturchenko.dragdropdemo.data.controllers;

import com.iturchenko.dragdropdemo.data.model.DataElement;

import java.util.Random;

class ElementGenerator {
    private static String[] colors = new String[] {"White", "Black", "Blue", "Green","Red","Brown","Purple","Orange", "Yellow", "Grey"};
    private static String[] animals = new String[] {"Albatross", "Bear", "Camel", "Deer", "Eagle", "Falcon", "Gecko", "Hamster", "Iguana", "Jackal"};
    private static String[] cities = new String[] {"Chicago", "Houston", "Philadelphia", "Phoenix","Los Angeles","Dallas","Austin","Columbus", "Seattle", "Denver"};

    private final Random random;

    public ElementGenerator() {
        random = new Random();
    }

    public DataElement createNew(int position) {
        return new DataElement(String.format("<%d> %s %s at %s", position, getRnd(colors), getRnd(animals), getRnd(cities)));
    }

    private String getRnd(String[] array) {
        return array[random.nextInt(array.length)];
    }
}
