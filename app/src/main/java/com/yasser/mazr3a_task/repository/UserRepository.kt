package com.yasser.mazr3a_task.repository

import com.yasser.mazr3a_task.data.db.AppDatabase
import com.yasser.mazr3a_task.data.network.SafeApiRequest
import com.yasser.mazr3a_task.data.network.ServiceApi
import com.yasser.mazr3a_task.model.User
import com.yasser.quran.data.preference.PreferenceProvider

// i added serviceApi class in case there is any api to save user data on cloud
class UserRepository(private val serviceApi: ServiceApi,
                     private val mPrefrence: PreferenceProvider, private val db: AppDatabase) : SafeApiRequest() {

    fun GetUserLogged() = db.getUserDao().GetUser()
    suspend fun GetUserData() = db.getUserDao().GetUserData()

    suspend fun UpdateUserData(user: User){
        db.getUserDao().DeleteAllData()
        db.getUserDao().insert(user)
    }

    fun GetUserStatus():Boolean{
        return mPrefrence.GetUserLoggedStatu()
    }
    fun ChangesUserStatus(status:Boolean){
         mPrefrence.ChangeUserLoggedStatus(status)
    }
}