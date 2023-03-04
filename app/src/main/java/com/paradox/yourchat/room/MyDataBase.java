package com.paradox.yourchat.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.paradox.yourchat.room.common.CommonDao;
import com.paradox.yourchat.room.common.CommonEntity;


@Database(entities = {CommonEntity.class}, version = 1, exportSchema = false)
public abstract class MyDataBase extends RoomDatabase {
    private static final String DBNAME = MyDataBase.class.getSimpleName();
    private static MyDataBase instance;

    public static MyDataBase getInstance() {
        if (instance == null) throw new NullPointerException("database not init!!");
        return instance;
    }

    public static synchronized MyDataBase init(Context context) {
        if (instance == null)
            instance = Room.databaseBuilder(context.getApplicationContext()
                            , MyDataBase.class, DBNAME + ".db")
                    .fallbackToDestructiveMigration()//数据库更新时删除数据重新创建
                    .build();

        return instance;
    }

    //获取Dao对象
    public abstract CommonDao getCommonDao();

}
