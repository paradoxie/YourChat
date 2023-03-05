package com.paradox.yourchat.room.common;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.paradox.yourchat.base.MyApplication;

import java.util.List;


public class CommonViewModel extends ViewModel {
    private CommonRepository commonRepository;

    public CommonViewModel() {
        commonRepository = new CommonRepository(MyApplication.getContext());
    }

    /**
     * 增加一条转换记录
     *
     * @param jd
     */
    public void insert(CommonEntity jd) {
        commonRepository.insert(jd);
        Log.e("插入数据库", jd.toString());
    }

    /**
     * 查询一条记录
     *
     * @param hisId
     */
    public LiveData<CommonEntity> getCommonEntity(String hisId) {
        return commonRepository.getCommonEntity(hisId);
    }

    /**
     * 查询所有记录
     *
     */
    public LiveData<List<CommonEntity>> getAll() {
        return commonRepository.getAll();
    }

    /**
     * 更新
     *
     * @param commonEntity
     */
    public void update(CommonEntity commonEntity) {
        commonRepository.update(commonEntity);
    }

    /**
     * 更新
     * @param comId
     * @param content
     */
    public void update(String comId,String content) {
        commonRepository.update(comId,content);
    }

    /**
     * 删除一条记录
     */
    public void delete(int hisId) {
        commonRepository.delete(hisId);
    }

}
