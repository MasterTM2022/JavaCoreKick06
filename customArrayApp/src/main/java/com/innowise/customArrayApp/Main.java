package com.innowise.customArrayApp;

import com.innowise.customArrayApp.entity.CustomArray;
import com.innowise.customArrayApp.factory.CustomArrayFactory;
import com.innowise.customArrayApp.repository.CustomArrayRepository;
import com.innowise.customArrayApp.repository.Impl.InMemoryCustomArrayRepository;
import com.innowise.customArrayApp.repository.comparator.ArrayComparators;
import com.innowise.customArrayApp.repository.specification.Impl.CustomArraySpecifications;
import com.innowise.customArrayApp.warehouse.Warehouse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.innowise.customArrayApp.service.CustomArrayService;
import com.innowise.customArrayApp.service.Impl.DefaultCustomArrayService;
import com.innowise.customArrayApp.reader.Impl.CustomFileReaderImpl;

import java.util.List;
import java.util.Optional;

public class Main {

    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        System.setProperty("log4j.skipJansi", "false");
        logger.info("Application started");
        String dataFile = "input/input1.txt";

        Warehouse warehouse = Warehouse.getInstance();

        CustomArrayFactory factory = new com.innowise.customArrayApp.entity.CustomArrayFactory();

        CustomArrayService service = new DefaultCustomArrayService(factory);

        CustomArrayRepository repository = InMemoryCustomArrayRepository.getInstance();

        try {
            CustomFileReaderImpl fileReader = new CustomFileReaderImpl();
            logger.info("Reading arrays from file: {}", dataFile);
            List<List<Integer>> validArrays = fileReader.readValidArrays(dataFile);

            // Используем

            int index = 1;
            for (List<Integer> numbers : validArrays) {
                logger.info("Start Array #{} =============================================================", index);
                // Преобразуем List<Integer> → int[]
                int[] values = numbers.stream().mapToInt(i -> i).toArray();
                CustomArray arr = factory.createFromValues(values);
                logger.info("Stats after create: {}", warehouse.getStats(arr.getId()));

                arr.set(0, 1000 + (int) (Math.random() * 10));
                logger.info("Stats after set: {}", warehouse.getStats(arr.getId()));

                if (arr.size() > 0) {
                    logger.info("Array #{} created and store: {}", index, arr);
                    logger.info("Sorted Array #{} : {}", index, service.sort(arr));
                    logger.info("Sorted (bubble) Array #{}: {}", index, service.sortBubble(arr));
                    logger.info("Sorted (merge) Array #{}:  {}", index, service.sortMerge(arr));
                    logger.info("Min for Array #{}: {}", index, service.findMin(arr));
                    logger.info("Max for Array #{}: {}", index, service.findMax(arr));
                    logger.info("Total for Array #{}: {}", index, service.findTotal(arr));
                    logger.info("Average for Array #{}: {}", index, service.findAverage(arr));
                    int check = arr.get((int) (Math.random() * arr.size()));
                    logger.info("Is value = {} present in Array #{}? - {}", check, index, service.contains(arr, check));
                } else {
                    logger.warn("  Skipping analytics for empty Array #{}", index);
                }
                index++;
            }
        } catch (Exception e) {
            logger.fatal("Application failed", e);
        }

        logger.info("All arrays in repository:");
        repository.findAll().forEach(a -> logger.info("#{}  {}", a.getId(), a));

        // Найти массивы с суммой больше 15
        int biggerValue = 15;
        List<CustomArray> bigSum = repository.findBySpecification(CustomArraySpecifications.sumGreaterThan(biggerValue));
        logger.info("All arrays with sum bigger than {} in repository:{}", biggerValue, bigSum);


        // Найти массивы с количеством элементов = 3 И средним > 5
        int elementsQuantity = 3;
        double average = 5.0;
        List<CustomArray> complex = repository.findBySpecification(
                CustomArraySpecifications.and(
                        CustomArraySpecifications.sizeEquals(elementsQuantity),
                        CustomArraySpecifications.averageGreaterThan(average)
                )
        );
        logger.info("All arrays with more than {} elements and with average more than {} in repository:{}", elementsQuantity, average, complex);

        // Найти массив по ID
        long arrayId = 2L;
        Optional<CustomArray> specific = repository.findBySpecification(
                CustomArraySpecifications.hasId(2L)
        ).stream().findFirst();
        logger.info("Array with ID={} in repository:{}", arrayId, specific);

// Получаем все массивы, отсортированные по ID
        List<CustomArray> byId = repository.findAllSorted(ArrayComparators.byId());
        logger.info("Sorted by ID: {}", byId);

// По количеству элементов
        List<CustomArray> bySize = repository.findAllSorted(ArrayComparators.bySize());
        logger.info("Sorted by size: {}", bySize);

// По первому элементу
        List<CustomArray> byFirst = repository.findAllSorted(ArrayComparators.byFirstElement());
        logger.info("Sorted by first element: {}", byFirst);

// По сумме
        List<CustomArray> bySum = repository.findAllSorted(ArrayComparators.bySum());
        logger.info("Sorted by sum: {}", bySum);

// Комбинированная: сначала по размеру, потом по ID
        List<CustomArray> bySizeThenId = repository.findAllSorted(
                ArrayComparators.bySizeThenById()
        );
        logger.info("Sorted by size, then by ID: {}", bySizeThenId);

        logger.info("Application ended");
    }
}