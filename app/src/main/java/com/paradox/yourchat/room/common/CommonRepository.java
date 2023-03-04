package com.paradox.yourchat.room.common;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.paradox.yourchat.room.MyDataBase;

import java.util.List;


public class CommonRepository {
    private CommonDao commonDao;

    public CommonRepository(Context context) {
        this.commonDao = MyDataBase.init(context).getCommonDao();
    }

    /**
     * 插入数据库
     *
     * @param commonEntity
     */
    public void insert(CommonEntity commonEntity) {
        new AsyncTask<Void, Void, Void>() {

            @SuppressLint("StaticFieldLeak")
            @Override
            protected Void doInBackground(Void... voids) {
                commonDao.insert(commonEntity);
                Log.e("插入成功", "");
                return null;
            }
        }.execute();
    }

    /**
     * 根据id从数据库查询一条
     *
     * @param hisId
     * @return
     */
    public LiveData<CommonEntity> getCommonEntity(String hisId) {
        return commonDao.get(hisId);
    }

    /**
     * 查询所有
     *
     * @return
     */
    public LiveData<List<CommonEntity>> getAll() {
        return commonDao.getAll();
    }


    /**
     * 删除全部
     */
    public void delete(String hisId) {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                commonDao.delete(hisId);
                return null;
            }
        }.execute();
    }
}
