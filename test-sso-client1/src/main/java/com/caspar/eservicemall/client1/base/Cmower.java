package com.caspar.eservicemall.client1.base;

import java.util.HashMap;
import java.util.Map;

public class Cmower {
    private String name;

    private String age;

    public Cmower(String name,String age) {
        this.name = name;
        this.age=age;
    }
    @Override
    public int hashCode() {
      //  return this.hashCode();
        System.out.println("this.age==="+ this.age+"  hashcode==="+this.age.hashCode());
        System.out.println("this.name==="+ this.name+"  hashcode==="+this.name.hashCode());
        return this.age.hashCode();
    }
    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof Cmower))
            return false;
        if (obj == this)
            return true;
        return this.name.equals(((Cmower) obj).name);
    }

    public static void main(String[] args) {
        Cmower a1 = new Cmower("沉默王二","12");
        Cmower a2 = new Cmower("沉默王三","13");

        Map<Cmower, Integer> m = new HashMap<Cmower, Integer>();
        m.put(a1, 18);
        m.put(a2, 28);
        System.out.println(m.get(new Cmower("沉默王二","12")));


        StringBuffer s=new StringBuffer();  //  线程安全 ，有锁，性能低
        s.append("hello");   //synchronized


        StringBuilder builder=new StringBuilder();
        builder.append("hello");   //没有锁，线程不安全，性能高



        boolean result=true;
    }
}

