package com.liang.map;

import com.liang.vo.User;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class MapTest {

    public static void main(String[] args) {
        testOne();
    }

    /**
     * 自定义对象作hashmap的key,修改hashcode相关属性后，对hashmap的影响
     */
    public static void testOne(){
        HashMap<User, String> h = new HashMap<>();
        //name age作为重写hashcode和equals方法的属性
        User u = new User("小明",10);
        h.put(u,"北京");
        System.out.println(u.getName()+"\t"+h.get(u));
        User user1 = new User("小明",10);
        System.out.println(u.getName()+"\t"+h.get(user1));

        u = new User("小红",11);
        h.put(u,"上海");
        //原值查找正常
        System.out.println("原值查找："+u.getName()+"\t"+h.get(u));
        u.setName("小刚");
        /* 修改key对象的属性值，用修改后的对象查找，查不到；属性改变，但hashmap中hash值不可改变。
        新旧hash不同 */
        System.out.println("key修改后："+u.getName()+"\t"+h.get(u));
        h.remove(u);
        /* 创建新对象，用修改前的属性值，查不到。虽然属性值与插入时相同，
        hash值相同，计算数组的下标位置也正确，但equals方法判断不通过 */
        user1 = new User("小红",11);
        System.out.println("新对象旧值查找："+user1.getName()+"\t"+h.get(user1));
        //创建新对象，用修改后的属性值，查不到。属性值与插入时不同，hash值不同
        user1 = new User("小刚",11);
        System.out.println("新对象新值查找："+user1.getName()+"\t"+h.get(user1));

        //扩容
        for (int i=0;i<15;i++){
            u = new User("小伟"+i,11);
            h.put(u,"上海");
        }
        /* 用修改后的属性值，不一定能查到。虽然重新计算位置，
        但是是用原下标或原下标上加旧数组长度做新下标，并不是用取模重新计算的，所以不准确 */
        user1 = new User("小刚",11);
        System.out.println("扩容后，修改后的属性值查找："+user1.getName()+"\t"+h.get(user1));

        System.out.println("遍历--------可以找到所有的key------------");
        Set<Map.Entry<User, String>> entries = h.entrySet();
        Iterator<Map.Entry<User, String>> iterator = entries.iterator();
        Map.Entry<User, String> item=null;
        while (iterator.hasNext()){
            item = iterator.next();
            System.out.println("key:"+item.getKey().getName()+" value:"+item.getValue());
        }

    }
}
