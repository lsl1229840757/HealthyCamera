package com.lsl.healthycamera;

import android.app.Application;

import com.lsl.healthycamera.entity.DaoMaster;
import com.lsl.healthycamera.entity.DaoSession;


public class MyApplication extends Application {
    private static DaoSession daoSession;
    private static DaoMaster daoMaster;
    private static MyApplication instance;
    private static String username;

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        MyApplication.username = username;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        MyOpenHelper helper = new MyOpenHelper(this, "message-db",
                null);
         daoMaster = new DaoMaster(helper.getWritableDatabase());
         daoSession = daoMaster.newSession();
    }

    public static MyApplication getInstance(){
        if(instance == null){
            instance = new MyApplication();
        }
            return instance;
    }


    public DaoSession getDaoSession(){
        return daoSession;
    }

}
