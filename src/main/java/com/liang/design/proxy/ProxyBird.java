package com.liang.design.proxy;

/**
 * 静态代理
 * 代理类实现被代理对象的接口，持有被代理对象实例
 */
public class ProxyBird implements IBird {
    IBird bird;

    ProxyBird(IBird bird) {
        this.bird = bird;
    }

    @Override
    public void fly() {
        System.out.println("Before fly !");
        bird.fly();
        System.out.println("After fly !");
    }

    @Override
    public String eat(String food) {
        System.out.println("Before eat !");
        String eat = bird.eat(food);
        System.out.println("After eat !");
        return eat;
    }

    public static void main(String[] args) {
        IBird bird = new Bird("Swan");
        ProxyBird ProxyBird = new ProxyBird(bird);
        bird.fly();
        String eat = bird.eat("grass");
        System.out.println(eat);
    }

}
