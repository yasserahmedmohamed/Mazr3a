package com.yasser.mazr3a_task.ViewModelFactory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yasser.mazr3a_task.repository.ProductRepository
import com.yasser.mazr3a_task.repository.UserRepository
import com.yasser.mazr3a_task.ui.category.CategoryDetailsViewModel
import com.yasser.mazr3a_task.ui.login.LoginViewModel


@Suppress("UNCHECKED_CAST")

class CategoryDetailsViewModelFactory (
    private val repository: ProductRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {


        return CategoryDetailsViewModel(repository) as T
    }

}