package com.innowise.customArrayApp.observer;

import com.innowise.customArrayApp.entity.CustomArray;

public interface CustomArrayObserver {
    void onArrayChanged(CustomArray array);

}
