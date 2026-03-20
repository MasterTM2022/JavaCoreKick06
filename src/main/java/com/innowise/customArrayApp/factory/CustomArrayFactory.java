package com.innowise.customArrayApp.factory;

import com.innowise.customArrayApp.entity.CustomArray;

public interface CustomArrayFactory {

    CustomArray createEmpty(int size);

    CustomArray createFromValues(int... values);
}
