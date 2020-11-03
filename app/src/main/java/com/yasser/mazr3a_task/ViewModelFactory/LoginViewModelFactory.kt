package com.yasser.mazr3a_task.ViewModelFactory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yasser.mazr3a_task.repository.UserRepository
import com.yasser.mazr3a_task.ui.login.LoginViewModel


@Suppress("UNCHECKED_CAST")

class LoginViewModelFactory (
    private val repository: UserRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {


        return LoginViewModel(repository) as T
    }

}