package com.liang.queue;

import com.liang.vo.User;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 初次使用task时，启动线程
 */
public class Task {
    private static TaskInstance task = null;
    static {
        task = new TaskInstance();
        new Thread(task).start();
    }
    public static boolean offer(User user, long timeout) {
        boolean offer = false;
        if (user != null) {
            try {
                offer = task.queue.offer(user, timeout, TimeUnit.MILLISECONDS);
                if(offer){
                    System.out.println("添加元素："+user.getName());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //startTask();
        }
        return offer;
    }
    public static void put(User user) {
        if (user != null) {
            try {
                task.queue.put(user);
                System.out.println("添加元素："+user.getName());
                //startTask();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args){
        int i=0;
        while(true){
            User user = new User("a"+i++,12);
            Task.put(user);
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class TaskInstance implements Runnable{
        public LinkedBlockingQueue<User> queue = new LinkedBlockingQueue<User>(3);
        @Override
        public void run() {
            System.out.println("运行run()");
            while(true){
                try {
                    User user = queue.take();
                    System.out.println("获取队列元素："+user.getName());
                    Thread.sleep(2000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
