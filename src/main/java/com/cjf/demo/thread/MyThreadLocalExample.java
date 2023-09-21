package com.cjf.demo.thread;

public class MyThreadLocalExample {
    private static final ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public static void main(String[] args) {
        Thread thread1 = new Thread(() -> {
            threadLocal.set("Thread 1 Local Variable");
            System.out.println(threadLocal.get());
        });
        Thread thread2 = new Thread(() -> {
            threadLocal.set("Thread 2 Local Variable");
            System.out.println(threadLocal.get());
        });

        thread1.start();
        thread2.start();
    }
}
