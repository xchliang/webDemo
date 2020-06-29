package com.liang.design.factory.factoryMethod;

/**
 * 产品实例：小米手机
 */
public class HuaweiPhone implements IPhone {

    public String call(String num) {
        System.out.println("HuaweiPhone dial number "+ num);
        return "ok";
    }


}