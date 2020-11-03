package com.yasser.mazr3a_task.ui.home

interface HomeListener {
    fun ShowErrorMessage(viewID:Int,message:String)
    fun HideErrorMessage()
    fun OnOrderSent()
    fun ShowLoading()

}