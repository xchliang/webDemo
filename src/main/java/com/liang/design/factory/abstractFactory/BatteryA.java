package com.liang.design.factory.abstractFactory;

/**
 * 产品实例：A电池
 */
public class BatteryA implements IBattery {

    public void charge() {
        System.out.println("BatteryA charging ");
    }

}