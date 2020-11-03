package com.yasser.mazr3a_task.ui.home.mainfragment

import com.yasser.mazr3a_task.model.Category
import com.yasser.mazr3a_task.model.Product

interface MainAdapterListener {
    fun onCategoryTapped(cat:Category)
    fun onProductTapped(product: Product)
}