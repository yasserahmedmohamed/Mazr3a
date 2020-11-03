package com.yasser.mazr3a_task.ui.category

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yasser.mazr3a_task.data.network.ApiException
import com.yasser.mazr3a_task.model.MainModel
import com.yasser.mazr3a_task.model.Product
import com.yasser.mazr3a_task.repository.ProductRepository
import com.yasser.mazr3a_task.utils.Coroutines

class CategoryDetailsViewModel(private val productRepository: ProductRepository) : ViewModel() {
    var DisplayProducts = MutableLiveData<List<Product>>()
    var listener: CategoryDetailsListener? = null


    fun GetProducts(catID: String) {
        Coroutines.main {
            try {
                listener?.showLoadingView()
                val res = productRepository.GetCategoryProducts(catID, "0")
                DisplayProducts.postValue(res)
                listener?.HideLoadingView()
            } catch (e: ApiException) {
                listener?.OnErrorHappen(e.message.toString())

            }
        }
    }

    fun GetProductsBySort(catID: String, sort: String) {
        Coroutines.main {
            try {
                listener?.showLoadingView()
                val res = productRepository.GetCategoryProductsBySort(catID, sort, "0")
                DisplayProducts.postValue(res)
                listener?.HideLoadingView()

            } catch (e: ApiException) {
                listener?.OnErrorHappen(e.message.toString())
            }
        }
    }


}