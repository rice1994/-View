package com.example.lenovo.mpplication.test;

/**
 * @Description:
 * @Author: weilu
 * @Time: 2017/11/4 10:45.
 */

public class Person {
    
    private String name = "默认值";
    private int sex = -1;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }
    
    public int getAge(){
        return 11;
    }
    
    public String eat(String food){
        return food;
    }
}
