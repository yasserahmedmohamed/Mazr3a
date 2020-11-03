package com.yasser.mazr3a_task.ui.home.cartfragment

import com.yasser.mazr3a_task.model.ItemCartData

interface CartFragmentListener {
    fun OnAddBtnTapped(itemData: ItemCartData)
    fun OnSubBtnTapped(itemData:ItemCartData)
    fun OnDeleteBtnTapped(itemData:ItemCartData)
}