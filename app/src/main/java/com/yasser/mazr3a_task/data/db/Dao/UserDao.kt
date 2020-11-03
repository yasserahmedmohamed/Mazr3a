package com.yasser.mazr3a_task.data.db.Dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.yasser.mazr3a_task.model.ItemCartData
import com.yasser.mazr3a_task.model.User


@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: User)
//    @Update(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun update(item: User)

    @Query("select * from user")
    fun GetUser(): LiveData<User>

    @Query("delete  from user")
    suspend fun DeleteAllData()
}