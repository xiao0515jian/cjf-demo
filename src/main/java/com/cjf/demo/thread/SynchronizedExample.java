package com.cjf.demo.thread;

public class SynchronizedExample {

    private int count = 0;

    public synchronized void increment() {
        count++;
    }

    public void process() {
        for(int i=0; i<10000; i++) {
            increment();
        }
    }

    public static void main(String[] args) {
        SynchronizedExample example = new SynchronizedExample();

        Thread thread1 = new Thread(() -> {
            example.process();
        });

        Thread thread2 = new Thread(() -> {
            example.process();
        });

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            System.out.println("Interrupted Exception: " + e.getMessage());
        }

        System.out.println("Count: " + example.count);
    }
}

