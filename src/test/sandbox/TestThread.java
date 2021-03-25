package test.sandbox;

import java.util.ArrayList;
import java.util.List;

class Test {
    static final List<Thread> threads = new ArrayList<>();

    public static void main(String[] args) {
        TestThread thread1 = new TestThread();
        TestThread thread2 = new TestThread();
        TestThread thread3 = new TestThread();
        TestThread thread4 = new TestThread();
        TestThread thread5 = new TestThread();

        Thread thread6 = new Thread(new Runner());

        threads.add(thread1);
        threads.add(thread2);
        threads.add(thread3);
        threads.add(thread4);
        threads.add(thread5);
        threads.add(thread6);

        for (Thread thread : threads) {
            synchronized (threads) {
                thread.start();
            }
        }

        System.out.println(Thread.currentThread().getName() + " HELLO FROM MAIN!");
    }
}

public class TestThread extends Thread {
    private int count = 0;

    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            for (int j = 0; j < 100; j++) {
                count++;
            }
        }
        System.out.println(Thread.currentThread().getName() + " counted: " + count);
    }
}

class Runner implements Runnable {
    private int count = 0;

    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            for (int j = 0; j < 100; j++) {
                count++;
            }
        }
        System.out.println(Thread.currentThread().getName() + " counted: " + count);
    }
}
