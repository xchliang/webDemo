package com.liang.design.prototype;

/**
 * @author xcl
 */
public class Shop implements Cloneable{

    String name;
    String address;
    String openTime;
    Employee manager;//管理者对象

    public Shop(String name, String openTime) {
        this.name = name;
        this.openTime = openTime;
    }

    protected Shop clone(){
        try {
            //浅拷贝时，manager只拷贝了引用，不拷贝指向的对象
            Shop clone = (Shop) super.clone();
            //深拷贝要把引用对象一起拷贝
            clone.setManager(manager.clone());
            return clone;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public Employee getManager() {
        return manager;
    }

    public void setManager(Employee manager) {
        this.manager = manager;
    }
}

