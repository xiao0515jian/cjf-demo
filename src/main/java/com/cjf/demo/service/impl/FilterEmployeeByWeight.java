package com.cjf.demo.service.impl;

import com.cjf.demo.po.Employee;
import com.cjf.demo.service.MyPredicate;

/**
 * @author : chenjianfeng
 * @date : 2022/8/9
 */
public class FilterEmployeeByWeight implements MyPredicate<Employee> {
    public boolean filter(Employee employee) {
        return employee.getWeight()>50;
    }
}
