package com.yasser.mazr3a_task.ui.home

import android.app.Application
import android.view.View
import android.widget.Button
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.yasser.mazr3a_task.R
import com.yasser.mazr3a_task.model.User
import com.yasser.mazr3a_task.repository.OrderRepository
import com.yasser.mazr3a_task.repository.ProductRepository
import com.yasser.mazr3a_task.repository.UserRepository
import com.yasser.mazr3a_task.utils.Coroutines
import com.yasser.mazr3a_task.utils.IsEmail


class HomeViewModel(
    application: Application,
    private val userRepo: UserRepository,
    private val productRepo: ProductRepository,
    private val orderRepo: OrderRepository
) : AndroidViewModel(application) {
    private val context = getApplication<Application>().applicationContext

    val user = userRepo.GetUserLogged()
    val displayUser = MutableLiveData<User>()
    var listener: HomeListener? = null
    val PaymentoptionA = ObservableBoolean()

    fun OrderBtnTapped(view: View) {

        if (displayUser.value!!.email.isNullOrEmpty()) {
            listener?.ShowErrorMessage(
                R.id.userEmail,
                context.getString(R.string.this_field_require)
            )
            return
        }
        if (displayUser.value!!.name.isNullOrEmpty()) {
            listener?.ShowErrorMessage(
                R.id.userName,
                context.getString(R.string.this_field_require)
            )
            return
        }
        if (displayUser.value!!.phone.isNullOrEmpty()) {
            listener?.ShowErrorMessage(
                R.id.userPhone,
                context.getString(R.string.this_field_require)
            )
            return
        }
        if (!displayUser.value!!.email.IsEmail()) {
            listener?.ShowErrorMessage(
                R.id.userEmail,
                context.getString(R.string.enter_valid_email)
            )
            return
        }


        listener?.HideErrorMessage()
        val userData = User()
        userData.email = displayUser.value!!.email
        userData.name = displayUser.value!!.name
        userData.phone = displayUser.value!!.phone
        userData.homeLocation = displayUser.value!!.homeLocation
        Coroutines.io {
            listener?.ShowLoading()
            userRepo.UpdateUserData(userData)
            val OrderItems = productRepo.GetFinalCartItems()
            val shippingCost = productRepo.GetShippingCost()
            var paymentType = "CASH"
            if (!PaymentoptionA.get())
            {
                paymentType = "Credit or debit card"
            }
            orderRepo.MakeOrder(OrderItems, userData, shippingCost,paymentType)
            productRepo.DeleteAllProduct()
            listener?.OnOrderSent()
        }


    }

    fun IsUserLoggedIn():Boolean{
        return userRepo.GetUserStatus()
    }

    fun OnHomeLocationTapped(view: View) {
        // assume we open map and get current on selected location
        val user = displayUser.value!!
        user!!.homeLocation = "Al Saraya Mall, Street 5, First New Cairo, Cairo Governorate"
        displayUser.postValue(user)
        (view as Button).text = "Al Saraya Mall, Street 5, First New Cairo, Cairo Governorate"
    }

}