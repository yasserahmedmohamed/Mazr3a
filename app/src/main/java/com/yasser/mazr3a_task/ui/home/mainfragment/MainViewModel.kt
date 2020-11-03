package com.yasser.mazr3a_task.ui.home.mainfragment

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yasser.mazr3a_task.data.network.ApiException
import com.yasser.mazr3a_task.model.Category
import com.yasser.mazr3a_task.model.MainModel
import com.yasser.mazr3a_task.repository.CategoryRepository
import com.yasser.mazr3a_task.repository.ProductRepository
import com.yasser.mazr3a_task.utils.Coroutines

class MainViewModel(
    private val repository: CategoryRepository,
    private val productrepository: ProductRepository
) : ViewModel() {
    var DisplayCategProducts = MutableLiveData<ArrayList<MainModel>>()

    var DisplayCategory = MutableLiveData<List<Category>>()
    var mListener: MainFragmentListener? = null

    init {
        DisplayCategProducts.value = ArrayList()
    }

    fun GetCategories() {
        Coroutines.io {
            try {
                mListener?.onLoading()
                val AllCateg = repository.GetCategoreis()
                DisplayCategory.postValue(AllCateg)
                mListener?.onFinishLoading()
            }catch (e: ApiException){
                Log.e("error", e.toString())
                mListener?.onFinishLoading()
                mListener?.onNoErrorHappen(e.message.toString())
            }
        }
    }


    fun getCategoryProducts(category: Category) {
        Coroutines.io {
            val proCatList = DisplayCategProducts.value!!

            val mProduct = productrepository.GetLimitPoducts(category.id.toString(), "0")
            proCatList.add(MainModel(category, mProduct))

            DisplayCategProducts.postValue(proCatList)

        }
    }


    fun getProductRepo(): ProductRepository {
        return productrepository
    }

}