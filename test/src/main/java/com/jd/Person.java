package com.jd;

/**
 * Title: Person
 * Description: Person
 * Company: <a href=www.cc.com>CC</a>
 * Date:  2016/9/9
 *
 * @author <a href=mailto:zhouchaochao@cc.com>chaochao</a>
 */
public class Person {

    private String name;
    private int age;

    public Person(){
    }

    public Person(String name,int age){
        this.name = name;
        this.age = age;
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

}
