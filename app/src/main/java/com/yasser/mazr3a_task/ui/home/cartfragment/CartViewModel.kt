package com.yasser.mazr3a_task.ui.home.cartfragment

import androidx.lifecycle.ViewModel
import com.yasser.mazr3a_task.model.ItemCartData
import com.yasser.mazr3a_task.repository.ProductRepository
import com.yasser.mazr3a_task.utils.Coroutines

class CartViewModel(val Repo: ProductRepository):ViewModel() {
    val items = Repo.GetCartItems()

     fun UpdateProductOrder(item:ItemCartData){
         Coroutines.io {
             Repo.UpdateProduct(item)

         }
    }

    fun RemoveProductFromCart(ID:Int){
        Coroutines.io {

            Repo.DeleteProduct(ID)

        }
    }

    fun GetShippingCost():Int{

        // call api to get shipping price for now assume it cost 50 EGP
        return Repo.GetShippingCost()
    }
}