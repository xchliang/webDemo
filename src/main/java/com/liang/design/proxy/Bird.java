package com.liang.design.proxy;

public class Bird implements IBird {
    private String name;
    public Bird(String name) {
        this.name=name;
    }

    @Override
    public void fly() {
        System.out.println(name+" can fly!");
    }

    @Override
    public String eat(String food) {
        System.out.println(name+" eat " +food);
        return food+" ok!";
    }


}
