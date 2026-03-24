package com.innowise.customArrayApp.warehouse;

import com.innowise.customArrayApp.entity.CustomArray;
import com.innowise.customArrayApp.observer.CustomArrayObserver;

import java.util.HashMap;
import java.util.Map;

public class Warehouse implements CustomArrayObserver {
    // Singleton
    private static Warehouse instance;
    private final Map<Long, CustomArrayStats> statsMap = new HashMap<>();

    private Warehouse() {}

    public static Warehouse getInstance() {
        if (instance == null) {
            instance = new Warehouse();
        }
        return instance;
    }

    @Override
    public void onArrayChanged(CustomArray array) {
        CustomArrayStats stats = new CustomArrayStats(array.getId(), array.toArray());
        statsMap.put(array.getId(), stats);
    }

    public CustomArrayStats getStats(long id) {
        return statsMap.get(id);
    }

    public void clear() {
        statsMap.clear();
    }
}
