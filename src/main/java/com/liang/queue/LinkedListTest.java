package com.liang.queue;

import java.util.LinkedList;

/**
 * LinkedList测试
 * 继承了Deque接口
 * 可用作非阻塞队列
 */
public class LinkedListTest {
    static LinkedList<String> list = new LinkedList<String>();
    public static void main(String[] args) {
       //linkedListQueue();
        test1();
    }

    //非阻塞
    public static void test1(){
        Thread t1 =  new Thread(){
            @Override
            public void run() {
                for (int i = 0; i < 100000 ; i++) {
                    list.offer(i+"");
                    System.out.println("添加元素："+i);
                    try {
                        Thread.sleep(1000L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        t1.start();
        Thread t2 =  new Thread(){
            @Override
            public void run() {
                for (;;) {
                    System.out.println("获取元素："+list.poll());
                    try {
                        Thread.sleep(200L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        t2.start();

    }

    /**
     * LinkedList实现了接口Deque
     *
     */
    public static void linkedListQueue(){
        LinkedList<String> queue = new LinkedList<String>();
        //添加元素，尾部添加
        queue.offer("a");
        queue.offer("b");
        queue.offer("c");
        queue.offer("d");
        queue.offer("e");
        //添加元素到头部
        queue.push("f");
        for(String q : queue){
            System.out.println(q);
        }
        System.out.println("===");
        //返回第一个元素，并在队列中删除；如果队列为空，则返回null
        System.out.println("poll="+queue.poll());
        for(String q : queue){
            System.out.println(q);
        }
        System.out.println("===");
        //返回第一个元素，并在队列中删除；如果队列为空，则抛出异常
        System.out.println("pop="+queue.pop());
        for(String q : queue){
            System.out.println(q);
        }
        System.out.println("===");
        //返回队列头部的元素  如果队列为空，则抛出一个NoSuchElementException异常
        System.out.println("element=" + queue.element());
        for(String q : queue){
            System.out.println(q);
        }
        System.out.println("===");
        //返回第一个元素，不移除
        System.out.println("peek="+queue.peek());
        for(String q : queue){
            System.out.println(q);
        }
    }

}

