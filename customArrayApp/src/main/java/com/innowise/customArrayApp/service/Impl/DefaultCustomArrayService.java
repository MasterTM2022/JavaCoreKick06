package com.innowise.customArrayApp.service.Impl;

import com.innowise.customArrayApp.entity.CustomArray;
import com.innowise.customArrayApp.exception.CustomArrayException;
import com.innowise.customArrayApp.factory.CustomArrayFactory;
import com.innowise.customArrayApp.service.CustomArrayService;
import com.innowise.customArrayApp.util.ArrayStatisticsUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;

public class DefaultCustomArrayService implements CustomArrayService {

    private static final Logger logger = LogManager.getLogger(DefaultCustomArrayService.class);
    private final CustomArrayFactory arrayFactory;

    public DefaultCustomArrayService(CustomArrayFactory arrayFactory) {
        this.arrayFactory = arrayFactory;
        logger.debug("DefaultArrayService initialized with factory: {}", arrayFactory != null ? arrayFactory.getClass().getSimpleName() : "null");
    }

    public int findMax(CustomArray array) {
        logger.trace("Entering findMax with array of size {}", array.size());
        if (array.isEmpty()) {
            logger.error("Cannot find max in empty array");
            throw new CustomArrayException("Cannot find max in empty array");
        }
        int max = ArrayStatisticsUtils.max(array.toArray());
        logger.debug("Max value found: {}", max);
        return max;
    }

    public int findMin(CustomArray array) {
        logger.trace("Entering findMin with array of size {}", array.size());
        if (array.isEmpty()) {
            logger.error("Cannot find min in empty array");
            throw new CustomArrayException("Cannot find min in empty array");
        }
        int min = ArrayStatisticsUtils.min(array.toArray());
        logger.debug("Min value found: {}", min);
        return min;
    }

    public double findAverage(CustomArray array) {
        logger.trace("Entering findAverage with array of size {}", array.size());
        if (array.isEmpty()) {
            logger.error("Cannot find average in empty array");
            throw new CustomArrayException("Cannot find average in empty array");
        }
        double avg = ArrayStatisticsUtils.average(array.toArray());
        logger.debug("Average value found: {}", avg);
        return avg;
    }

    public int findTotal(CustomArray array) {
        logger.trace("Entering findTotal with array of size {}", array.size());
        if (array.isEmpty()) {
            logger.error("Cannot find total in empty array");
            throw new CustomArrayException("Cannot find total in empty array");
        }
        int sum = ArrayStatisticsUtils.sum(array.toArray());
        logger.debug("Total value found: {}", sum);
        return sum;
    }


    public CustomArray sort(CustomArray array) {
        logger.info("Sorting array: {}", array);

        int[] copy = array.toArray();
        Arrays.sort(copy);
        CustomArray sorted = arrayFactory.createFromValues(copy);

        logger.info("Sorted array: {}", sorted);
        return sorted;
    }

    public CustomArray sortBubble(CustomArray array) {
        logger.info("Sorting array with Bubble Sort: {}", array);

        if (array.size() <= 1) {
            return arrayFactory.createFromValues(array.toArray());
        }

        int[] copy = array.toArray();
        int n = copy.length;

        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false;
            for (int j = 0; j < n - i - 1; j++) {
                if (copy[j] > copy[j + 1]) {
                    int temp = copy[j];
                    copy[j] = copy[j + 1];
                    copy[j + 1] = temp;
                    swapped = true;
                }
            }
            if (!swapped) break;
        }

        CustomArray sorted = arrayFactory.createFromValues(copy);
        logger.info("Bubble-sorted array: {}", sorted);
        return sorted;
    }

    public CustomArray sortMerge(CustomArray array) {
        logger.info("Sorting array with Merge Sort: {}", array);

        if (array.size() <= 1) {
            return arrayFactory.createFromValues(array.toArray());
        }

        int[] copy = array.toArray();
        mergeSortRecursive(copy, 0, copy.length - 1);

        CustomArray sorted = arrayFactory.createFromValues(copy);
        logger.info("Merge-sorted array: {}", sorted);
        return sorted;
    }

    private void mergeSortRecursive(int[] arr, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            mergeSortRecursive(arr, left, mid);
            mergeSortRecursive(arr, mid + 1, right);
            merge(arr, left, mid, right);
        }
    }

    private void merge(int[] arr, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        int[] L = new int[n1];
        int[] R = new int[n2];

        System.arraycopy(arr, left, L, 0, n1);
        System.arraycopy(arr, mid + 1, R, 0, n2);

        int i = 0, j = 0, k = left;
        while (i < n1 && j < n2) {
            if (L[i] <= R[j]) {
                arr[k++] = L[i++];
            } else {
                arr[k++] = R[j++];
            }
        }

        while (i < n1) arr[k++] = L[i++];
        while (j < n2) arr[k++] = R[j++];
    }

    public boolean contains(CustomArray array, int value) {
        logger.debug("Checking if array contains value: {}", value);
        for (int i = 0; i < array.size(); i++) {
            if (array.get(i) == value) {
                logger.trace("Value {} found at index {}", value, i);
                return true;
            }
        }
        logger.debug("Value {} not found", value);
        return false;
    }
}
