package com.liang.thread;

import com.liang.queue.Task;
import com.liang.vo.User;

public class ThreadTest {

    public static void main(String[] args) {
        /*ThreadOne t1 = new ThreadOne();
        t1.start();
        Thread.State state = t1.getState();
        //System.out.println(state.);
        new Thread(t1).start();
        new Thread(t1).start();*/

        Task.put(new User("aa",12));
        Task.put(new User("bb",13));
    }
}
