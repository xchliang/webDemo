package com.liang.design.adapter;

/**
 * 适配者，业务系统现有的接口，它是被访问和适配的现存组件库中的组件接口。
 * @author xcl
 */
public class Adaptee {

    public String doSomething(String str){
        System.out.println("===Adaptee.doSomething===");
        str = str+str;
        return str;
    }
}
