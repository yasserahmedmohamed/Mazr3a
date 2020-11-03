package com.yasser.mazr3a_task.kdi

import android.app.Application
import com.yasser.mazr3a_task.ViewModelFactory.*
import com.yasser.mazr3a_task.data.db.AppDatabase
import com.yasser.mazr3a_task.data.network.ConnectionStatus
import com.yasser.mazr3a_task.data.network.ServiceApi
import com.yasser.mazr3a_task.repository.CategoryRepository
import com.yasser.mazr3a_task.repository.OrderRepository
import com.yasser.mazr3a_task.repository.ProductRepository
import com.yasser.mazr3a_task.repository.UserRepository
import com.yasser.mazr3a_task.ui.product_details.ProductDetailsViewModel
import com.yasser.quran.data.preference.PreferenceProvider
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.androidCoreModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

class MainApplication : Application(), KodeinAware {
    override val kodein = Kodein.lazy {

        import(androidCoreModule(this@MainApplication))

        bind() from singleton { ConnectionStatus(instance()) }
        bind() from singleton { ServiceApi(instance()) }
        bind() from singleton { AppDatabase(instance()) }
        bind() from singleton { PreferenceProvider(instance()) }


        bind() from singleton { CategoryRepository(instance()) }
        bind() from singleton { ProductRepository(instance(),instance()) }
        bind() from singleton { UserRepository(instance(),instance(),instance()) }
        bind() from singleton { OrderRepository(instance(),instance()) }


        bind() from singleton { MainViewModelFactory(instance(),instance()) }
        bind() from singleton { CategoryDetailsViewModelFactory(instance()) }
        bind() from singleton { ProductDetailsViewModelFactory(instance(),instance()) }
        bind() from singleton { CartViewModelFactory(instance()) }
        bind() from singleton { HomeViewModelFactory(instance(),instance(),instance(),instance()) }
        bind() from singleton { OrderViewModelFactory(instance(),instance()) }


    }

}
