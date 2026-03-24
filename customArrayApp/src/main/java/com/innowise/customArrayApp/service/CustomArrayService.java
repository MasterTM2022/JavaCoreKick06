package com.innowise.customArrayApp.service;

import com.innowise.customArrayApp.entity.CustomArray;

public interface CustomArrayService {

    int findMax(CustomArray array);

    int findMin(CustomArray array);

    int findTotal(CustomArray array);

    double findAverage(CustomArray array);

    CustomArray sort(CustomArray array);

    CustomArray sortBubble(CustomArray array);

    CustomArray sortMerge(CustomArray array);

    boolean contains(CustomArray array, int value);
}