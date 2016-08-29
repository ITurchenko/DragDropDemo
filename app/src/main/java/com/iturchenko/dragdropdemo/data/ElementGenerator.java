package com.iturchenko.dragdropdemo.data;

import java.util.Random;

public class ElementGenerator {
    private static String[] colors = new String[] {"White", "Black", "Blue", "Green","Red","Brown","Purple","Orange", "Yellow", "Grey"};
    private static String[] animals = new String[] {"Albatross", "Bear", "Camel", "Deer", "Eagle", "Falcon", "Gecko", "Hamster", "Iguana", "Jackal"};

    private final Random random;

    public ElementGenerator() {
        random = new Random();
    }

    public DataElement createNew(int position) {
        return new DataElement(String.format("%d %s %s", position, getRnd(colors), getRnd(animals)));
    }

    private String getRnd(String[] array) {
        return array[random.nextInt(array.length)];
    }
}
