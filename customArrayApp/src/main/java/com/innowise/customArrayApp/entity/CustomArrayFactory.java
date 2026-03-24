package com.innowise.customArrayApp.entity;

import com.innowise.customArrayApp.repository.CustomArrayRepository;
import com.innowise.customArrayApp.repository.Impl.InMemoryCustomArrayRepository;
import com.innowise.customArrayApp.warehouse.Warehouse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CustomArrayFactory implements com.innowise.customArrayApp.factory.CustomArrayFactory {

    private static final Logger logger = LogManager.getLogger(CustomArrayFactory.class);
    private final CustomArrayRepository repository = InMemoryCustomArrayRepository.getInstance();;
    private final Warehouse warehouse = Warehouse.getInstance();

    @Override
    public CustomArray createEmpty(int size) {
        logger.info("Creating empty array of size {}", size);
        long id = repository.generateNextId();
        CustomArray array = new CustomArray(id, size);
        array.addObserver(warehouse);
        array.notifyObservers();
        repository.save(array);
        logger.info("Array created and saved: {}", array);
        return array;
    }

    @Override
    public CustomArray createFromValues(int... values) {
        logger.debug("Creating array from {} values", values != null ? values.length : 0);
        long id = repository.generateNextId();
        CustomArray array = new CustomArray(id, values);
        array.addObserver(warehouse);
        array.notifyObservers();
        repository.save(array);
        logger.info("Array created and saved: {}", array);
        return array;
    }
}
