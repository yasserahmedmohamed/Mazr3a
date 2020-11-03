package com.yasser.mazr3a_task.ui

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.yasser.mazr3a_task.utils.Coroutines
import com.yasser.mazr3a_task.utils.NetworkUtil
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein

 abstract  class BaseActivity : AppCompatActivity(), KodeinAware {

    override val kodein by kodein()

    @Override
    protected abstract fun InternetConnectionChanged(isConnected: Boolean)

     private val networkStateReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(p0: Context?, p1: Intent?) {

          //  Coroutines.main {
                p0?.let {
                    val status: Int = NetworkUtil.getConnectivityStatusString(it)
                    if (NetworkUtil.CONNECTIVITY_ACTION == p1!!.action) {
                        if (status == NetworkUtil.NETWORK_STATUS_NOT_CONNECTED) {
                            InternetConnectionChanged(false)
                        } else {
                            InternetConnectionChanged(true)

                        }
                    }
                }
            //}
        }
    }

    override fun onResume() {
        super.onResume()
        registerReceiver(networkStateReceiver, IntentFilter(NetworkUtil.CONNECTIVITY_ACTION))

    }

    override fun onPause() {
        unregisterReceiver(networkStateReceiver)
        super.onPause()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

}