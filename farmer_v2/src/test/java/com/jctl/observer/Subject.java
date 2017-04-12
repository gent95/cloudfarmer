package com.jctl.observer;

/**
 *
 * 抽象的被观察者
 *
 * 1、观察者对象保存在一个集合
 * 2、可以增加和删除观察者对象
 * 3、主动通知观察者
 * Created by Administrator on 2017/3/13.
 */
public interface Subject {

    void addObserver(Observer observer);

    void delObserver(Observer observer);

    void notifyObserver(String message);



}
