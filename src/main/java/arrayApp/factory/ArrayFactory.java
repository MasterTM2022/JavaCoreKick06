package arrayApp.factory;

import arrayApp.entity.Array;

public interface ArrayFactory {

    Array createEmpty(int size);

    Array createFromValues(int... values);
}
