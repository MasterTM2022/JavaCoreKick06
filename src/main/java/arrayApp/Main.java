package arrayApp;

import arrayApp.entity.Array;
import arrayApp.entity.DefaultArrayFactory;
import arrayApp.factory.ArrayFactory;
import arrayApp.repository.ArrayRepository;
import arrayApp.repository.InMemoryArrayRepository;
import arrayApp.repository.comparator.ArrayComparators;
import arrayApp.repository.specification.ArraySpecifications;
import arrayApp.warehouse.Warehouse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import arrayApp.service.ArrayService;
import arrayApp.service.DefaultArrayService;
import arrayApp.reader.FileReader;

import java.util.List;
import java.util.Optional;

public class Main {

    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        System.setProperty("log4j.skipJansi", "false");
        logger.info("Application started");
        String dataFile = "input/input1.txt";

        Warehouse warehouse = Warehouse.getInstance();

        ArrayFactory factory = new DefaultArrayFactory();

        ArrayService service = new DefaultArrayService(factory);

        ArrayRepository repository = InMemoryArrayRepository.getInstance();

        try {
            FileReader fileReader = new FileReader();
            logger.info("Reading arrays from file: {}", dataFile);
            List<List<Integer>> validArrays = fileReader.readValidArrays(dataFile);

            // Используем

            int index = 1;
            for (List<Integer> numbers : validArrays) {
                logger.info("Start Array #{} =============================================================", index);
                // Преобразуем List<Integer> → int[]
                int[] values = numbers.stream().mapToInt(i -> i).toArray();
                Array arr = factory.createFromValues(values);
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
        List<Array> bigSum = repository.findBySpecification(ArraySpecifications.sumGreaterThan(biggerValue));
        logger.info("All arrays with sum bigger than {} in repository:{}", biggerValue, bigSum);


        // Найти массивы с количеством элементов = 3 И средним > 5
        int elementsQuantity = 3;
        double average = 5.0;
        List<Array> complex = repository.findBySpecification(
                ArraySpecifications.and(
                        ArraySpecifications.sizeEquals(elementsQuantity),
                        ArraySpecifications.averageGreaterThan(average)
                )
        );
        logger.info("All arrays with more than {} elements and with average more than {} in repository:{}", elementsQuantity, average, complex);

        // Найти массив по ID
        long arrayId = 2L;
        Optional<Array> specific = repository.findBySpecification(
                ArraySpecifications.hasId(2L)
        ).stream().findFirst();
        logger.info("Array with ID={} in repository:{}", arrayId, specific);

// Получаем все массивы, отсортированные по ID
        List<Array> byId = repository.findAllSorted(ArrayComparators.byId());
        logger.info("Sorted by ID: {}", byId);

// По количеству элементов
        List<Array> bySize = repository.findAllSorted(ArrayComparators.bySize());
        logger.info("Sorted by size: {}", bySize);

// По первому элементу
        List<Array> byFirst = repository.findAllSorted(ArrayComparators.byFirstElement());
        logger.info("Sorted by first element: {}", byFirst);

// По сумме
        List<Array> bySum = repository.findAllSorted(ArrayComparators.bySum());
        logger.info("Sorted by sum: {}", bySum);

// Комбинированная: сначала по размеру, потом по ID
        List<Array> bySizeThenId = repository.findAllSorted(
                ArrayComparators.bySizeThenById()
        );
        logger.info("Sorted by size, then by ID: {}", bySizeThenId);

        logger.info("Application ended");
    }
}