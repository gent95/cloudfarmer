package com.jctl.observer;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * 具体被的观察者，实现被观察中者的接口
 * Created by Administrator on 2017/3/13.
 */
public class SubjectUser implements Subject{

    //存放观察者的集合
    private List<Observer> userList = new ArrayList<Observer>();

    @Override
    public void addObserver(Observer observer) {
        userList.add(observer);
    }

    @Override
    public void delObserver(Observer observer) {
        userList.remove(observer);
    }

    @Override
    public void notifyObserver(String message) {
        for(Observer observer :userList){
            observer.update(message);
        }
    }
}
