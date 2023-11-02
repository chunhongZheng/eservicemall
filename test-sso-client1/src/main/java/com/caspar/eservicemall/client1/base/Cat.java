package com.caspar.eservicemall.client1.base;

public class Cat implements Cloneable{
    String name;
    String desc;

    Cat cloneTest;
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
    //对于实现了Cloneable接口的对象，可以调用Object#clone()来进行属性的拷贝。这里的拷贝就是浅拷贝
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    // 浅拷贝
    public void assign(Cat cat){
        Cat newCat=cat;
        newCat.setName("new cat");
    }

    public static void main(String[] args) throws CloneNotSupportedException {
        Cat cat=new Cat();
        cat.setName("foo");
        System.out.println("拷贝前:::"+cat.getName());
        //浅拷贝
        Cat newcat=cat;
        newcat.setName("new cat");
        System.out.println("浅拷贝后:::"+cat.getName());
//        //对于实现了Cloneable接口的对象，可以调用Object#clone()来进行属性的拷贝。这里的拷贝就是浅拷贝，两个对象其实不是同一个对象。
        Cat clone = (Cat) cat.clone();
        clone.setName("foocopy");
        System.out.println("深拷贝后:::"+cat.getName());


        "hello".equals("111");

    }
}


