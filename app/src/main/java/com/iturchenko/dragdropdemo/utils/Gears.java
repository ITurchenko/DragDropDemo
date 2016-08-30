package com.iturchenko.dragdropdemo.utils;

public class Gears {
    public static void imitateLongCalculations() {
        imitateLongCalculations(1000);
    }

    public static void imitateLongCalculations(int timeout) {
        try {
            Thread.sleep(timeout);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
