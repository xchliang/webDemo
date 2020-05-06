package com.liang.queue.delayQueue;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * 延时队列
 * 实现延时处理数据。如：下单后30分钟未支付，则自动取消
 * @author xcl
 */
public class DelayedOrder implements Delayed {

    private String orderCode = null;
    private long endTime = 0;

    DelayedOrder(String orderCode, long endTime) {
        this.orderCode = orderCode;
        this.endTime = endTime;
    }

    //比较元素，确定顺序
    @Override
    public int compareTo(Delayed o) {
        //long n = this.getDelay(TimeUnit.MILLISECONDS) - o.getDelay(TimeUnit.MILLISECONDS);
        DelayedOrder deo = (DelayedOrder) o;
        long n = this.endTime - deo.endTime;
        return n > 0 ? 1 : (n < 0 ? -1 : 0);
    }

    //剩余延时时间
    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(this.endTime - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }

    public String getOrderCode() {
        return orderCode;
    }

    public long getEndTime() {
        return endTime;
    }

    public static void main(String[] args) {
        //延时队列，元素必须实现接口Delayed
        DelayQueue<DelayedOrder> dq = new DelayQueue<DelayedOrder>();
        //添加元素，add/put方法都是调用offer
        dq.offer(new DelayedOrder("001", System.currentTimeMillis()+3*1000));
        dq.offer(new DelayedOrder("002", System.currentTimeMillis()+6*1000));
        dq.offer(new DelayedOrder("003", System.currentTimeMillis()+9*1000));
        System.out.println("元素总数："+dq.size());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        while(dq.size()>0){
            try {
                //等到延时时间才可取出元素
                System.out.println("取出元素："+dq.take().orderCode+" "+ LocalDateTime.now().format(formatter));
                System.out.println("剩余元素："+dq.size());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
