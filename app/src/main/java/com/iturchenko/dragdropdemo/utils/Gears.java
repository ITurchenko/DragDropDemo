package com.iturchenko.dragdropdemo.utils;

public class Gears {
    public static void imitateLongCalculations() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
