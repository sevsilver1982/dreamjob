package store;

import model.ItemImpl;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class StoreMemoryImpl<T extends ItemImpl> implements Store<T> {
    private final Map<Integer, T> store = new ConcurrentHashMap<>();
    private static final AtomicInteger ID = new AtomicInteger(0);

    public Store<T> getInstance() {
        return null;
    }

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
    public Collection<T> find(Predicate<T> predicate) {
        return findAll().stream().filter(predicate).collect(Collectors.toList());
    }

    @Override
    public T findById(int id) {
        return find(obj -> obj.getId() == id)
                .stream()
                .findFirst()
                .orElse(null);
    }

    @Override
    public boolean delete(int id) {
        return !store.remove(id).isEmpty();
    }

}