package com.yasser.mazr3a_task.ui.orders

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yasser.mazr3a_task.model.Order
import com.yasser.mazr3a_task.repository.OrderRepository
import com.yasser.mazr3a_task.repository.UserRepository
import com.yasser.mazr3a_task.utils.Coroutines

class OrderViewModel(private val userRepo:UserRepository, private val orderRepo: OrderRepository):ViewModel() {
    val displayOrder = MutableLiveData<List<Order>>()
    var listener:OrderListener?=null
    fun GetOrders(){
        Coroutines.io {
            listener?.OnLoading()
            val user = userRepo.GetUserData()
            if (user.email.isNotEmpty()){
                val orders = orderRepo.GetUserOrder(user.email)
                displayOrder.postValue(orders)
            }
            listener?.OnFinishLoading()
        }
    }

}