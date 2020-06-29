package com.liang.design.factory.abstractFactory;

/**
 * 产品实例：B电池
 */
public class BatteryB implements IBattery {

    public void charge() {
        // TODO implement here
        System.out.println("BatteryB charging ");
    }

}