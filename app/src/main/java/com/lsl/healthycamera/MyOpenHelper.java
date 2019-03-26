package com.lsl.healthycamera;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.lsl.healthycamera.entity.DaoMaster;
import com.lsl.healthycamera.entity.UserDao;

import org.greenrobot.greendao.database.Database;

public class MyOpenHelper extends DaoMaster.OpenHelper {

    public MyOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        // TODO 以后还有一些类需要添加
        MigrationHelper.getInstance().migrate(db,UserDao.class);
    }
}

