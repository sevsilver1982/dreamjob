package store;

import model.ItemImpl;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class StoreMemoryImpl<T extends ItemImpl> implements Store<T> {
    private final Map<Integer, T> store = new ConcurrentHashMap<>();
    private static final AtomicInteger ID = new AtomicInteger(0);

    @Override
    public boolean add(T item) {
        if (item.getId() == 0) {
            item.setId(ID.incrementAndGet());
        }
        store.put(item.getId(), item);
        return true;
    }

    @Override
    public Collection<T> find() {
        return store.values();
    }

    @Override
    public boolean delete(int id) {
        return !store.remove(id).isEmpty();
    }

}