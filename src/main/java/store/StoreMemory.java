package store;

import model.Item;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class StoreMemory<T extends Item> implements Store<T> {
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
    public Collection<T> findAll() {
        return store.values();
    }

    @Override
    public T findById(int id) {
        return findAll().stream()
                .filter(item -> item.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public Collection<T> find(Predicate<T> predicate) {
        return findAll().stream().filter(predicate).collect(Collectors.toList());
    }

    @Override
    public boolean delete(int id) {
        return !store.remove(id).isEmpty();
    }

}