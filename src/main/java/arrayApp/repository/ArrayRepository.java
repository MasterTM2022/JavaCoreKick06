package arrayApp.repository;

import arrayApp.entity.Array;
import arrayApp.repository.specification.ArraySpecification;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public interface ArrayRepository {
    Array save(Array array);

    Optional<Array> findById(long id);

    List<Array> findAll();

    List<Array> findBySpecification(ArraySpecification spec);

    void delete(Array array);

    void deleteById(long id);

    void clear();

    List<Array> findAllSorted(Comparator<Array> comparator);
}
