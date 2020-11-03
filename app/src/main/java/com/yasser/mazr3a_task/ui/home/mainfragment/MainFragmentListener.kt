package com.yasser.mazr3a_task.ui.home.mainfragment

interface MainFragmentListener {
    fun onLoading()
    fun onFinishLoading()
    fun onNoErrorHappen(error:String)
}