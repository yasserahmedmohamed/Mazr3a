package com.yasser.mazr3a_task.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.yasser.mazr3a_task.data.db.Dao.ItemCartDao
import com.yasser.mazr3a_task.data.db.Dao.UserDao
import com.yasser.mazr3a_task.model.ItemCartData
import com.yasser.mazr3a_task.model.User

@Database(entities = [ItemCartData::class,User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getCartDao(): ItemCartDao
    abstract fun getUserDao(): UserDao


    companion object {
        @Volatile
        private var instance: AppDatabase? = null
        private val LOCK = Any()
        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDataBase(context).also {
                instance = it
            }
        }

        private fun buildDataBase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext, AppDatabase::class.java
                , "MyDatabase.db"
            )
                .build()

    }


}