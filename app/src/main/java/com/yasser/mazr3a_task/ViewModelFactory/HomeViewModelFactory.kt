package com.yasser.mazr3a_task.ViewModelFactory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yasser.mazr3a_task.repository.OrderRepository
import com.yasser.mazr3a_task.repository.ProductRepository
import com.yasser.mazr3a_task.repository.UserRepository
import com.yasser.mazr3a_task.ui.home.HomeViewModel
import com.yasser.mazr3a_task.ui.home.cartfragment.CartViewModel
import com.yasser.mazr3a_task.ui.login.LoginViewModel


@Suppress("UNCHECKED_CAST")

class HomeViewModelFactory (
   val application: Application,
    private val userRepo: UserRepository   , private val productRepo: ProductRepository,
     private val orderRepo: OrderRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {


        return HomeViewModel(application,userRepo,productRepo,orderRepo) as T
    }

}