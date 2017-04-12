package com.jctl.observer;

/**
 * 抽象观察者
 * Created by Administrator on 2017/3/13.
 *
 * 定义一个接口，可以让被观察者更新时主动通知自己
 */


public interface Observer {

        void update(String message);

}
