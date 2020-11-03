package com.yasser.mazr3a_task.data.db.Dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.yasser.mazr3a_task.model.ItemCartData


@Dao
interface ItemCartDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: ItemCartData)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(item: ItemCartData)

    @Query("select * from itemcartdata")
     fun GetAllRecipe(): LiveData<List<ItemCartData>>


    @Query("select * from itemcartdata")
    suspend fun GetFinalProduct(): List<ItemCartData>

    @Query("delete  from itemcartdata where productID = :productID")
    suspend fun DeleteProduct(productID:Int)

    @Query("delete  from itemcartdata")
    suspend fun DeleteAllData()
}