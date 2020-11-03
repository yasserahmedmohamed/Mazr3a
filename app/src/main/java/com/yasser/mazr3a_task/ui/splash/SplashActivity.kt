package com.yasser.mazr3a_task.ui.splash

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.yasser.mazr3a_task.R
import com.yasser.mazr3a_task.ui.home.HomeActivity
import com.yasser.mazr3a_task.ui.login.LoginActivity
import com.yasser.mazr3a_task.utils.Coroutines
import com.yasser.mazr3a_task.utils.toast
import kotlinx.coroutines.delay

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

            openMainPage()
        //}
    }



    override fun onResume() {
        super.onResume()

    }


    fun openLoginNumber() {
        Coroutines.main {
            delay(1000)
            Intent(this, LoginActivity::class.java).also {
                it.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                it.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(it)
            }
        }
    }

    fun openMainPage() {
        Coroutines.main {
            delay(1000)
        Intent(this, HomeActivity::class.java).also {
            it.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            it.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(it)
        }
        }
    }

}
