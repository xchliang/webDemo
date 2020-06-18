package com.liang.design.prototype;

/**
 * 员工
 * @author xcl
 */
public class Employee implements Cloneable{

    String name;
    int age;
    String mobile;
    String address;

    public Employee(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public Employee clone(){
        try {
            return (Employee)super.clone();
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

