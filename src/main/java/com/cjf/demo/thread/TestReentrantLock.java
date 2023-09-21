package com.cjf.demo.thread;

import org.springframework.beans.BeanUtils;

import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author : chenjianfeng
 * @date : 2023/8/12
 */
public class TestReentrantLock {
    public static void main(String[] args) {
        /**
         * ReenTrantLock是JDK实现的
         * ReentrantLock 公平锁和非公平锁
         */
        ExecutorService threadPool = Executors.newCachedThreadPool();

        ReentrantLock fairLock = new ReentrantLock(true);
        ReentrantLock unFairLock = new ReentrantLock();
        for (int i = 0; i < 10; i++) {
            //threadPool.submit(new TestThread(fairLock,i," fairLock"));
            threadPool.submit(new TestThread(unFairLock, i, "unFairLock"));
        }

        /**
         * synchronized 非公平锁测试
         * synchronized是作为Java关键字是依赖于JVM实现
         */
        for(int i=0;i<20;i++){
            int finalI = i;
            new Thread(() ->
                    test(finalI)
            ).start();
        }

        /**
         * 测试ReentrantLock
         */
        ReentrantLock lock = new ReentrantLock();

        new Thread(()->{
            try {
                lock.lock();
                System.out.println("线程A拿到锁，并执行模拟业务执行60分钟");
                Thread.sleep(60*60*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        },"线程A").start();

        new Thread(()->{
            try {
                lock.lock();
                System.out.println("线程B拿到锁");
            }finally {
                lock.unlock();
            }
        },"线程B").start();

        System.out.println("程序结束.....");
    }

    static synchronized  private void test(int index) {
        System.out.println("--------------- > Task :" + index);
    }



static class TestThread implements Runnable {
        Lock lock;
        int indext;
        String tag;

        public TestThread(Lock lock, int index, String tag) {
            this.lock = lock;
            this.indext = index;
            this.tag = tag;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getId() + " 线程 START  " + tag);
            meath();
        }

        private void meath() {
            lock.lock();
            try {
                if((indext&0x1)==1){
                    Thread.sleep(200);
                }else{
                    Thread.sleep(500);
                }
                System.out.println(Thread.currentThread().getId() + " 线程 获得： Lock  ---》" + tag + "  Index:" + indext);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }

    }

}
