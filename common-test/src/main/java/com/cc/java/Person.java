package com.cc.java;

/**
 * Title: Person
 * Description: Person
 * Date:  2019/6/14
 *
 * @author <a href=mailto:zhouzhichao1024@gmail.com>chaochao</a>
 */

public class Person {
    private long id;
    private int sex;
    private String name;

    public Person(long id, int sex, String name) {
        this.id = id;
        this.sex = sex;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
