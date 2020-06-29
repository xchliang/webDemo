package com.liang.design.factory.abstractFactory;

/**
 * 产品实例：华为手机
 */
public class HuaweiPhone implements IPhone {

    public String call(String num) {
        System.out.println("HuaweiPhone dail number "+num);
        return "ok";
    }

}