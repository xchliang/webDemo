package com.liang.thread;

import com.liang.queue.Task;
import com.liang.vo.User;
import org.omg.CORBA.FloatSeqHelper;

import java.util.Collections;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class ThreadTest {

    public static void main(String[] args) {
        /*ThreadOne t1 = new ThreadOne();
        t1.start();
        Thread.State state = t1.getState();
        //System.out.println(state.);
        new Thread(t1).start();
        new Thread(t1).start();*/

       // Task.put(new User("aa",12));
       // Task.put(new User("bb",13));
        ConcurrentHashMap<String,String> chm = new ConcurrentHashMap<String,String>();
        chm.put("a","aa");
        chm.put("b","aa");

        Collections.synchronizedMap(new HashMap<String,String>());
        float f = Float.MAX_VALUE;
        float f2 = Float.MIN_VALUE;
        System.out.println(f+"  "+ f2);

        System.out.println(Short.MAX_VALUE);
        System.out.println(Short.MIN_VALUE);
    }
}
