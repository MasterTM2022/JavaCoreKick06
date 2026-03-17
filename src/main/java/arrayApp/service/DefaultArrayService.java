package arrayApp.service;

import arrayApp.entity.Array;
import arrayApp.exception.ArrayException;
import arrayApp.factory.ArrayFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;

public class DefaultArrayService implements ArrayService {

    private static final Logger logger = LogManager.getLogger(DefaultArrayService.class);
    private final ArrayFactory arrayFactory;

    public DefaultArrayService(ArrayFactory arrayFactory) {
        this.arrayFactory = arrayFactory;
        logger.debug("DefaultArrayService initialized with factory: {}", arrayFactory != null ? arrayFactory.getClass().getSimpleName() : "null");
    }

    public int findMax(Array array) {
        logger.trace("Entering findMax with array of size {}", array.size());
        if (array.isEmpty()) {
            logger.error("Cannot find max in empty array");
            throw new ArrayException("Cannot find max in empty array");
        }
        int max = array.get(0);
        for (int i = 1; i < array.size(); i++) {
            int value = array.get(i);
            if (value > max) {
                max = value;
            }
        }
        logger.debug("Max value found: {}", max);
        return max;
    }

    public int findMin(Array array) {
        logger.trace("Entering findMin with array of size {}", array.size());
        if (array.isEmpty()) {
            logger.error("Cannot find min in empty array");
            throw new ArrayException("Cannot find min in empty array");
        }
        int min = array.get(0);
        for (int i = 1; i < array.size(); i++) {
            int value = array.get(i);
            if (value < min) {
                min = value;
            }
        }
        logger.debug("Min value found: {}", min);
        return min;
    }

    public double findAverage(Array array) {
        logger.trace("Entering findAverage with array of size {}", array.size());
        if (array.isEmpty()) {
            logger.error("Cannot find average in empty array");
            throw new ArrayException("Cannot find average in empty array");
        }
        double avg = 1.0 * findTotal(array) / array.size();
        logger.debug("Average value found: {}", avg);
        return avg;
    }

    public int findTotal(Array array) {
        logger.trace("Entering findTotal with array of size {}", array.size());
        if (array.isEmpty()) {
            logger.error("Cannot find total in empty array");
            throw new ArrayException("Cannot find total in empty array");
        }
        int sum = 0;
        for (int i = 0; i < array.size(); i++) {
            sum += array.get(i);
        }
        logger.debug("Total value found: {}", sum);
        return sum;
    }


    public Array sort(Array array) {
        logger.info("Sorting array: {}", array);

        int[] copy = array.toArray();
        Arrays.sort(copy);
        Array sorted = arrayFactory.createFromValues(copy);

        logger.info("Sorted array: {}", sorted);
        return sorted;
    }

    public Array sortBubble(Array array) {
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

        Array sorted = arrayFactory.createFromValues(copy);
        logger.info("Bubble-sorted array: {}", sorted);
        return sorted;
    }

    public Array sortMerge(Array array) {
        logger.info("Sorting array with Merge Sort: {}", array);

        if (array.size() <= 1) {
            return arrayFactory.createFromValues(array.toArray());
        }

        int[] copy = array.toArray();
        mergeSortRecursive(copy, 0, copy.length - 1);

        Array sorted = arrayFactory.createFromValues(copy);
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

    public boolean contains(Array array, int value) {
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
