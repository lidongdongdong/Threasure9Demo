package com.zhuoxin.com.threasure9demo;

/**
 * Created by Administrator on 2017/8/8.
 */

public class A {
 public  static  A a=new A();
private A(){

}
public static  A getA(){
    return a;
}
}
class B{
    public static  volatile  B b=null;
    private B(){

    }
    public static  B getB(){
        if (b==null){
            synchronized (B.class){
                if (b==null){
                    b=new B();
                }
            }
        }
        return b;
    }
}