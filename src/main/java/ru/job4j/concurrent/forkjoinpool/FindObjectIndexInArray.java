package ru.job4j.concurrent.forkjoinpool;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class FindObjectIndexInArray extends RecursiveTask<Integer> {
    private final Obj[] array;
    private final int from;
    private final int to;
    private final Obj findObject;

    public FindObjectIndexInArray(Obj[] array, int from, int to, Obj findObject) {
        this.array = array;
        this.from = from;
        this.to = to;
        this.findObject = findObject;
    }

    @Override
    protected Integer compute() {
        if (to - from <= 10) {
            for (int index = from; index <= to; index++) {
                if (array[index].equals(findObject)) {
                    return index;
                }
            }
            return -1;
        }
        int mid = (from + to) / 2;
        FindObjectIndexInArray leftFind = new FindObjectIndexInArray(array, from, mid, findObject);
        FindObjectIndexInArray rightFind = new FindObjectIndexInArray(array, mid + 1, to, findObject);
        leftFind.fork();
        rightFind.fork();
        int rslLeft = leftFind.join();
        int rslRight = rightFind.join();
        return Math.max(rslLeft, rslRight);
    }

    public static void main(String[] args) {
        int arraySize = 1_000_000;
        Obj[] array = new Obj[arraySize];

        for (int i = 0; i < arraySize; i++) {
            array[i] = new Obj("Object: " + i);
        }

        int size = Runtime.getRuntime().availableProcessors();
        ForkJoinPool forkJoinPool = new ForkJoinPool(size);

        int index = forkJoinPool.invoke(
                new FindObjectIndexInArray(array, 0, array.length - 1, new Obj("Object: 987654")));

        System.out.println("Found index of current object: " + index);
    }

}
