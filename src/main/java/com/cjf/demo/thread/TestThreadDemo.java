package com.cjf.demo.thread;

import com.cjf.demo.thread.vo.Bank;
import com.cjf.demo.thread.vo.PersonA;
import com.cjf.demo.thread.vo.PersonB;
import com.cjf.demo.thread.vo.Station;
import org.junit.jupiter.api.Test;

/**
 * @author : chenjianfeng
 * @date : 2023/7/23
 */
public class TestThreadDemo {


    public static void main(String[] args) {
        // demo1
        //实例化站台对象，并为每一个站台取名字
        Station station1=new Station("窗口1");
        Station station2=new Station("窗口2");
        Station station3=new Station("窗口3");
        // 让每一个站台对象各自开始工作
        station1.start();
        station2.start();
        station3.start();

        // demo1
        Bank bank = new Bank();
        // 实例化两个人，传入同一个银行的对象
        PersonA a = new PersonA(bank, "Counter");
        PersonB b = new PersonB(bank, "ATM");
        a.start();
        b.start();
    }
}
