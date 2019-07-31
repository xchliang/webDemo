package com.liang.queue;

import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * LinkedList测试
 * 可用作非阻塞队列
 */
public class BlokingQueueTest {
    static LinkedBlockingQueue<String> q = new LinkedBlockingQueue<String>(10);
    static ArrayBlockingQueue<String> ab= new ArrayBlockingQueue<String>(5);
    public static void main(String[] args) {
        ab.poll();
        test1();

    }
    //阻塞
    public static void test1(){

        Thread t1 =  new Thread(){
            @Override
            public void run() {
                for (int i = 0; i < 100000 ; i++) {
                    try {
                        /*
                        //添加元素，队列满时阻塞等待，超时返回false
                        boolean bo = q.offer(i + "", 200L, TimeUnit.MILLISECONDS);
                        System.out.println("++++添加元素："+i+" "+bo);
                        */
                        //添加元素，队列满时，无限期等待
                        q.put("" + i );
                        System.out.println(System.currentTimeMillis() +"++++添加元素："+i);
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
                    try {
                        /*
                        //获取元素并移除，队列空时阻塞等待，超时返回null
                        System.out.println("----获取元素："+q.poll(500L, TimeUnit.MILLISECONDS));
                        Thread.sleep(500L);
                        */
                        //获取队列头部元素，队列为空，无限期等待
                        String v = q.take();
                        System.out.println(System.currentTimeMillis() + "----获取元素："+v);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        t2.start();
    }

}
