package com.jd.reflect.entity;

import com.jd.reflect.iface.Animal;

/**
 * Title: Person
 * Description: Person
 * Company: <a href=www.jd.com>京东</a>
 * Date:  2016/8/15
 *
 * @author <a href=mailto:zhouzhichao@jd.com>chaochao</a>
 */
public class Person implements Animal{

    private String name;
    private int age;


    public Person() {
    }

    public Person(String name) {
        this.name = name;
    }

    public Person(int age) {
        this.age = age;
    }

    public Person(String name, int age) {
        this.age = age;
        this.name = name;
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



    @Override
    public String toString() {
        return "[" + this.name + "  " + this.age + "]";
    }

    public void hello(){
        System.out.println("hello");
    }

    public void say(String world,int times){
        System.out.println();
        for(int i=0;i<times;i++){
            System.out.print(world+",");
        }
        System.out.println();
    }

    @Override
    public void eat(String food) {
        System.out.print(this.getClass().getName() + " eat " + food);
    }
}