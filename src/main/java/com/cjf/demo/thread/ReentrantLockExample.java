package com.cjf.demo.thread;

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockExample {
    private static final int THREAD_COUNT = 5;
    private static final int LOOP_COUNT = 1000;
    private static int counter = 0;
    private static final ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        Thread[] threads = new Thread[THREAD_COUNT];

        // 创建并启动多个线程
        for (int i = 0; i < THREAD_COUNT; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < LOOP_COUNT; j++) {
                    System.out.println("j:"+j);
                    incrementCounter();
                    System.out.println("Counter: " + counter);
                }
            });
            System.out.println("线程"+i+"开始");
            threads[i].start();
        }

        // 等待所有线程执行完毕
        for (int i = 0; i < THREAD_COUNT; i++) {
            threads[i].join();
        }

        // 输出计数器的最终值
        System.out.println("Counter: " + counter);
    }

    private static void incrementCounter() {
        lock.lock(); // 获取锁
        try {
            counter++; // 对共享资源进行操作
        } finally {
            lock.unlock(); // 释放锁
        }
    }
}

