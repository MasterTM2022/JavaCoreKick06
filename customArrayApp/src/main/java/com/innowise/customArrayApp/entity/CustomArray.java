package com.innowise.customArrayApp.entity;

import com.innowise.customArrayApp.exception.CustomArrayException;
import com.innowise.customArrayApp.observer.CustomArrayObserver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class CustomArray {
    private final long id;
    private final int[] data;
    private final List<CustomArrayObserver> observers = new ArrayList<>();

    CustomArray(long id, int size) {
        if (size < 0) {
            throw new CustomArrayException("Array size cannot be negative: " + size);
        }
        this.id = id;
        this.data = new int[size];
    }

    CustomArray(long id, int[] values) {
        if (values == null) {
            throw new CustomArrayException("Initial values cannot be null");
        }
        this.id = id;
        this.data = Arrays.copyOf(values, values.length);
    }

    // === Методы для работы с наблюдателями ===
    public void addObserver(CustomArrayObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(CustomArrayObserver observer) {
        observers.remove(observer);
    }

    void notifyObservers() {
        for (CustomArrayObserver observer : observers) {
            observer.onArrayChanged(this);
        }
    }


    public long getId() {
        return id;
    }

    public int get(int index) {
        if (index < 0 || index >= data.length) {
            throw new CustomArrayException("Index out of bounds: " + index);
        }
        return data[index];
    }

    public void set(int index, int value) {
        if (index < 0 || index >= data.length) {
            throw new CustomArrayException("Index out of bounds: " + index);
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
        CustomArray array = (CustomArray) o;
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
