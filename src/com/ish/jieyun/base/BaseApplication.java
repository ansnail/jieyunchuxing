package com.ish.jieyun.base;

import android.app.Application;
import android.content.Context;

/**
 * Created by biger on 2016/4/12.
 */
public class BaseApplication extends Application {

    private static Application MyApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        MyApplication = this;
    }

    public static Context getContext() {
        return MyApplication.getApplicationContext();
    }

}
