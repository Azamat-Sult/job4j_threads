package ru.job4j.concurrent.nbcache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class NBCache {
    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();

    public boolean add(Base model) {
        return memory.putIfAbsent(model.getId(), model) == null;
    }

    public boolean update(Base model) {
        return memory.computeIfPresent(model.getId(), (key, value) -> {
            Base stored = memory.get(model.getId());
            if (stored.getVersion() != model.getVersion()) {
                throw new OptimisticException("Versions are not equal");
            }
            stored.setVersion(model.getVersion() + 1);
            stored.setName(model.getName());
            return stored;
        }) != null;
    }

    public void delete(Base model) {
        memory.remove(model.getId(), model);
    }

    public Base get(int key) {
        return memory.get(key);
    }
}
