package com.liang.design.factory.abstractFactory;

/**
 * 手机产品实例：小米手机
 */
public class XiaoMiPhone implements IPhone {

    public String call(String num) {
        System.out.println("XiaoMiPhone dail number "+num);
        return "ok";
    }

}