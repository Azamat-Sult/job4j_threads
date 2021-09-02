package ru.job4j.concurrent.forkjoinpool;

import java.util.Objects;

public class Obj {
    private String name;

    public Obj(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Obj obj = (Obj) o;
        return Objects.equals(name, obj.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Obj{ " + "name='" + name + '\'' + " }";
    }
}
