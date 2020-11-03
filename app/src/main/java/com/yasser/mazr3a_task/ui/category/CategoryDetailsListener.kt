package com.yasser.mazr3a_task.ui.category

import com.yasser.mazr3a_task.model.Product

interface CategoryDetailsListener {
    fun OnAddToCartTapped(product: Product)
    fun showLoadingView()
    fun HideLoadingView()
    fun OnErrorHappen(error:String)

}