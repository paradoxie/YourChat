package com.paradox.yourchat.room.common;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.paradox.yourchat.base.MyApplication;


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
     * 删除一条记录
     */
    public void delete(String hisId) {
        commonRepository.delete(hisId);
    }

}
