package arrayApp.repository;

import arrayApp.entity.Array;
import arrayApp.entity.DefaultArrayFactory;
import arrayApp.repository.specification.ArraySpecification;
import arrayApp.warehouse.Warehouse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class InMemoryArrayRepository implements ArrayRepository {

    private static final Logger logger = LogManager.getLogger(DefaultArrayFactory.class);

    private static InMemoryArrayRepository instance;
    public static InMemoryArrayRepository getInstance() {
        if (instance == null) {
            instance = new InMemoryArrayRepository();
            logger.info("InMemoryArrayRepository was CREATED");
        }
        return instance;
    }

    private final Map<Long, Array> storage = new ConcurrentHashMap<>();
    private final AtomicLong nextId = new AtomicLong(1);

    private InMemoryArrayRepository() {
    }

    @Override
    public Array save(Array array) {
        if (array.getId() == 0) {
            throw new IllegalArgumentException("Array must have an ID");
        }
        storage.put(array.getId(), array);
        logger.info("Saving Array with ID={}...", array.getId());
        return array;
    }

    @Override
    public Optional<Array> findById(long id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<Array> findAll() {
        return Collections.unmodifiableList(new ArrayList<>(storage.values()));
    }

    @Override
    public List<Array> findBySpecification(ArraySpecification spec) {
        return storage.values().stream()
                .filter(spec::test)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Array array) {
        deleteById(array.getId());
        logger.info("Deleting Array with ID={}...", array.getId());
    }

    @Override
    public void deleteById(long id) {
        if (!storage.containsKey(id)) {
            throw new IllegalArgumentException("Array with id=" + id + " not found");
        }
        storage.remove(id);
        logger.info("Deleting Array with ID={}...", id);
    }

    @Override
    public void clear() {
        storage.clear();
        nextId.set(1);
        logger.info("InMemoryArrayRepository was cleared");
    }

    @Override
    public List<Array> findAllSorted(Comparator<Array> comparator) {
        return storage.values().stream()
                .sorted(comparator)
                .collect(Collectors.toList());
    }

    // Метод для генерации нового ID (будет использоваться фабрикой)
    public long generateNextId() {
        return nextId.getAndIncrement();
    }
}
