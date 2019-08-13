package com.liang.design.proxy;

public class Dog {
    private String name;
    public Dog(){
    }
    public Dog(String name){
        this.name=name;
    }
    public void run(){
        System.out.println( name + " is runing");
    }
}
