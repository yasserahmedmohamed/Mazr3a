package com.yasser.mazr3a_task.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.telephony.TelephonyManager
import androidx.appcompat.app.AppCompatActivity
import com.yasser.mazr3a_task.R
import com.yasser.mazr3a_task.ui.verify_phone_number.VerifyActivity
import com.yasser.mazr3a_task.utils.toast
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

//        country_picker.setOnCountryChangeListener {
//            toast(country_picker.selectedCountryCodeWithPlus)
//        }


        loginBtn.setOnClickListener {

            val countryCode =  country_picker.selectedCountryCodeWithPlus
            val number = numberEdt.text.toString()
            if (number.isNullOrEmpty()){
                toast(getString(R.string.enter_phone))
                return@setOnClickListener
            }
            if (number.count()<10){
                toast(getString(R.string.enter_valid_phone))
                return@setOnClickListener
            }

            val intent = Intent(this,VerifyActivity::class.java)
            val num = countryCode+number
            intent.putExtra(VerifyActivity.PHONE,num)
            startActivity(intent)

        }
    }
}
