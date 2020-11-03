package com.yasser.mazr3a_task.ViewModelFactory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yasser.mazr3a_task.repository.OrderRepository
import com.yasser.mazr3a_task.repository.ProductRepository
import com.yasser.mazr3a_task.repository.UserRepository
import com.yasser.mazr3a_task.ui.home.cartfragment.CartViewModel
import com.yasser.mazr3a_task.ui.login.LoginViewModel
import com.yasser.mazr3a_task.ui.orders.OrderViewModel


@Suppress("UNCHECKED_CAST")

class OrderViewModelFactory (
    private val userRepo:UserRepository, private val orderRepo: OrderRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {


        return OrderViewModel(userRepo,orderRepo) as T
    }

}