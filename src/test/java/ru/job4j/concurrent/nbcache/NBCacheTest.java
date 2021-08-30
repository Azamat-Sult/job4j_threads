package ru.job4j.concurrent.nbcache;

import org.junit.Test;

import static org.junit.Assert.*;

public class NBCacheTest {

    @Test
    public void whenAddThenGet() {
        NBCache cache = new NBCache();
        Base base = new Base(1, 0);
        boolean rsl = cache.add(base);
        assertTrue(rsl);
        assertEquals(cache.get(1), base);
    }

    @Test
    public void whenAddThenExist() {
        NBCache cache = new NBCache();
        Base base1 = new Base(1, 0);
        Base base2 = new Base(1, 10);
        assertTrue(cache.add(base1));
        assertEquals(cache.get(1), base1);
        assertFalse(cache.add(base2));
        assertEquals(cache.get(1), base1);
    }

    @Test(expected = OptimisticException.class)
    public void whenVersionsNotEqualAndUpdateThenException() {
        NBCache cache = new NBCache();
        Base base1 = new Base(1, 0);
        Base base2 = new Base(1, 10);
        assertTrue(cache.add(base1));
        cache.update(base2);
    }

    @Test
    public void whenUpdateThenVersionIncAndNameUpdated() {
        NBCache cache = new NBCache();
        Base base1 = new Base(1, 0);
        base1.setName("old base");
        Base base2 = new Base(1, 0);
        base2.setName("new base");
        assertTrue(cache.add(base1));
        assertTrue(cache.update(base2));
        assertEquals(cache.get(1).getVersion(), 1);
        assertEquals(cache.get(1).getName(), "new base");
    }

    @Test
    public void whenDeleteThenDeleted() {
        NBCache cache = new NBCache();
        Base base1 = new Base(1, 0);
        assertTrue(cache.add(base1));
        cache.delete(base1);
        assertNull(cache.get(1));
    }
}