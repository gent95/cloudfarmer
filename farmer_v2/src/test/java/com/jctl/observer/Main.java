package com.jctl.observer;

/**
 * Created by Administrator on 2017/3/13.
 */
public class Main {


    public static void main(String [] args){
        Subject subject = new SubjectUser();

        Observer ob1 = new ObserverUser("老刘");
        Observer ob2 = new ObserverUser("老李");
        Observer ob3 = new ObserverUser("老张");


        subject.addObserver(ob1);
        subject.addObserver(ob2);
        subject.addObserver(ob3);

        subject.notifyObserver("看到我更新了没");


    }
}
