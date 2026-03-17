package arrayApp.entity;

import arrayApp.factory.ArrayFactory;
import arrayApp.repository.ArrayRepository;
import arrayApp.repository.InMemoryArrayRepository;
import arrayApp.warehouse.Warehouse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DefaultArrayFactory implements ArrayFactory {

    private static final Logger logger = LogManager.getLogger(DefaultArrayFactory.class);
    private final ArrayRepository repository = InMemoryArrayRepository.getInstance();;
    private final Warehouse warehouse = Warehouse.getInstance();

    @Override
    public Array createEmpty(int size) {
        logger.info("Creating empty array of size {}", size);
        long id = ((InMemoryArrayRepository) repository).generateNextId();
        Array array = new Array(id, size);
        array.addObserver(warehouse);
        repository.save(array);
        logger.info("Array created and saved: {}", array);
        return array;
    }

    @Override
    public Array createFromValues(int... values) {
        logger.debug("Creating array from {} values", values != null ? values.length : 0);
        long id = ((InMemoryArrayRepository) repository).generateNextId();
        Array array = new Array(id, values);
        array.addObserver(warehouse);
        repository.save(array);
        logger.info("Array created and saved: {}", array);
        return array;
    }
}
