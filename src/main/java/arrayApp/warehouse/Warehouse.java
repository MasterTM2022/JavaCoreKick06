package arrayApp.warehouse;

import arrayApp.entity.Array;
import arrayApp.observer.ArrayObserver;

import java.util.HashMap;
import java.util.Map;

public class Warehouse implements ArrayObserver {
    // Singleton
    private static Warehouse instance;

    public static Warehouse getInstance() {
        if (instance == null) {
            instance = new Warehouse();
        }
        return instance;
    }

    private final Map<Long, ArrayStats> statsMap = new HashMap<>();

    private Warehouse() {}

    @Override
    public void onArrayChanged(Array array) {
        ArrayStats stats = new ArrayStats(array.getId(), array.toArray());
        statsMap.put(array.getId(), stats);
    }

    public ArrayStats getStats(long id) {
        return statsMap.get(id);
    }

    public Map<Long, ArrayStats> getAllStats() {
        return new HashMap<>(statsMap);
    }

    public void clear() {
        statsMap.clear();
    }
}
