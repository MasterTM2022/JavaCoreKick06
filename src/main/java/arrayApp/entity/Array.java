package arrayApp.entity;

import arrayApp.exception.ArrayException;
import arrayApp.observer.ArrayObserver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Array {
    private final long id;
    private final int[] data;
    private final List<ArrayObserver> observers = new ArrayList<>();

    Array(long id, int size) {
        if (size < 0) {
            throw new ArrayException("Array size cannot be negative: " + size);
        }
        this.id = id;
        this.data = new int[size];
        notifyObservers();
    }

    Array(long id, int[] values) {
        if (values == null) {
            throw new ArrayException("Initial values cannot be null");
        }
        this.id = id;
        this.data = Arrays.copyOf(values, values.length);
        notifyObservers();
    }

    // === Методы для работы с наблюдателями ===
    public void addObserver(ArrayObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(ArrayObserver observer) {
        observers.remove(observer);
    }

    private void notifyObservers() {
        for (ArrayObserver observer : observers) {
            observer.onArrayChanged(this);
        }
    }


    public long getId() {
        return id;
    }

    public int get(int index) {
        if (index < 0 || index >= data.length) {
            throw new ArrayException("Index out of bounds: " + index);
        }
        return data[index];
    }

    public void set(int index, int value) {
        if (index < 0 || index >= data.length) {
            throw new ArrayException("Index out of bounds: " + index);
        }
        data[index] = value;
        notifyObservers();
    }

    public int size() {
        return data.length;
    }

    public int[] toArray() {
        return Arrays.copyOf(data, data.length);
    }

    @Override
    public String toString() {
        return "Array" + Arrays.toString(data);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Array array = (Array) o;
        return id == array.id && Objects.deepEquals(data, array.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, Arrays.hashCode(data));
    }

    public boolean isEmpty() {
        return data.length == 0;
    }
}
