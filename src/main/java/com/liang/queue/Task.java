package com.liang.queue;

import com.liang.vo.User;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class Task extends Thread {
    private static Task task = new Task();
    private LinkedBlockingQueue<User> queue = new LinkedBlockingQueue<User>();
    private static boolean b = false;
    private Task(){
    }

    @Override
    public void run() {
        System.out.println("运行run()");
        while (true){
            System.out.println("获取队列元素："+task.queue.poll().getName());
        }
    }

    public static void put(User user, long timeout) {
        if (user != null) {
            try {
                task.queue.offer(user, timeout, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            startTask();
        }
    }
    public static void put(User user) {
        if (user != null) {
            task.queue.offer(user);
            startTask();
        }
    }

    private static void startTask(){
        if (!b) {
            synchronized (task) {
                if (!b) {
                    System.out.println("启动线程Task");
                    new Thread(task).start();
                    b=true;
                }
            }
        }
    }


    public static void main(String[] args) {
        AtomicBoolean b = null;
        User user = new User("a",12);
        user.name="aaaa";
        System.out.println(user.name);
        task.queue.offer(user);
        if (user.address != null) {

        }
    }

}
