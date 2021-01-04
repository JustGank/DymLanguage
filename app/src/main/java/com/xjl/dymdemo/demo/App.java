package com.xjl.dymdemo.demo;

import android.app.Application;

import com.xjl.dymdemo.demo.dym.MyDymCustomerView;
import com.xjl.dymdemo.demo.dym.TextMap;
import com.xjl.dymlanguage.DymLanguageManager;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        DymLanguageManager.INSTANCE
                .setDymCustomerView(new MyDymCustomerView())
                .setLanguageMap(TextMap.class);

    }
}
