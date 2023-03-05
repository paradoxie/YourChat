package com.paradox.yourchat.room.common;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CommonDao {

    //插入一条数据
    @Insert
    long insert(CommonEntity commonEntity);

    //删除一条数据
    @Query("DELETE FROM commonentity WHERE id= :hisId")
    void delete(int hisId);

    //删除所有数据
    @Query("DELETE  FROM commonentity")
    void deleteAll();


    //更新一条数据
    @Update()
    void update(CommonEntity commonEntity);

    //更新一条数据
    @Query("UPDATE commonentity SET content= :con WHERE comId= :id")
    void update(String id, String con);


    //查询一条数据
    @Query("SELECT * FROM commonentity WHERE comId= :hisId")
    LiveData<CommonEntity> get(String hisId);

    //查询所有内容
    @Query("SELECT * FROM commonentity ORDER BY id DESC")
    LiveData<List<CommonEntity>> getAll();
}
