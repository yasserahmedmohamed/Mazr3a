package com.yasser.mazr3a_task.ViewModelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yasser.mazr3a_task.repository.CategoryRepository
import com.yasser.mazr3a_task.repository.ProductRepository
import com.yasser.mazr3a_task.repository.UserRepository
import com.yasser.mazr3a_task.ui.home.mainfragment.MainViewModel
import com.yasser.mazr3a_task.ui.login.LoginViewModel


@Suppress("UNCHECKED_CAST")

class MainViewModelFactory(
    private val repository: CategoryRepository,
    private val repository2: ProductRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {


        return MainViewModel(repository,repository2) as T
    }

}