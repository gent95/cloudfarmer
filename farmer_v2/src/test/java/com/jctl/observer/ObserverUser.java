package com.jctl.observer;

/**
 * 具体观察者
 * Created by Administrator on 2017/3/13.
 * 具体观察者实现抽象观察者接口，可以在收到更新通知后更改自己状态
 */
public class ObserverUser implements Observer {

    private String userName;

    public ObserverUser(String userName) {
        this.userName = userName;
    }

    @Override
    public void update(String message) {
        System.out.println(userName + "-" + message);
    }
}
