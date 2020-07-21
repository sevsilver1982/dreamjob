package store;

import model.ItemImpl;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class StoreMemory<T extends ItemImpl> implements StoreImpl<T> {
    private final Map<Integer, T> store = new ConcurrentHashMap<>();
    private static final AtomicInteger ID = new AtomicInteger(0);

    @Override
    public T add(T item) {
        if (item.getId() == 0) {
            item.setId(ID.incrementAndGet());
        }
        return store.put(item.getId(), item);
    }

    @Override
    public Collection<T> findAll() {
        return store.values();
    }

    @Override
    public T findById(int id) {
        return store.get(id);
    }

}