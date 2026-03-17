package arrayApp.service;

import arrayApp.entity.Array;

public interface ArrayService {

    int findMax(Array array);

    int findMin(Array array);

    int findTotal(Array array);

    double findAverage(Array array);

    Array sort(Array array);

    Array sortBubble(Array array);

    Array sortMerge(Array array);

    boolean contains(Array array, int value);
}