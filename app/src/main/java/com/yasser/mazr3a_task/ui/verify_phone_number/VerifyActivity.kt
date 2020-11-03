package com.yasser.mazr3a_task.ui.verify_phone_number

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableStringBuilder
import androidx.arch.core.executor.TaskExecutor
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.TaskExecutors
import com.google.firebase.FirebaseException
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.yasser.mazr3a_task.R
import com.yasser.mazr3a_task.model.User
import com.yasser.mazr3a_task.repository.UserRepository
import com.yasser.mazr3a_task.ui.home.HomeActivity
import com.yasser.mazr3a_task.utils.Coroutines
import com.yasser.mazr3a_task.utils.HideWithAnimation
import com.yasser.mazr3a_task.utils.showWithAnimation
import com.yasser.mazr3a_task.utils.toast
import kotlinx.android.synthetic.main.activity_verify.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import java.util.concurrent.TimeUnit

class VerifyActivity : AppCompatActivity(), KodeinAware, OnCompleteListener<AuthResult> {
    override val kodein by kodein()
    val userRepo: UserRepository by instance()
    var numberToVerify = ""

    companion object {
        val PHONE = "user_phone"
    }

    var verificationId = ""
    lateinit var mAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verify)
        numberToVerify = intent.getStringExtra(PHONE).toString()
        mAuth = FirebaseAuth.getInstance()
        numberToVerify?.let {
            SendVerificationCode(it)
        }
    }

    fun verifyCode(code: String) {
        val credential = PhoneAuthProvider.getCredential(verificationId, code)
        SignInWithCredential(credential)
    }

    private fun SignInWithCredential(credential: PhoneAuthCredential) {
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this)
    }

    fun SendVerificationCode(number: String) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            number,
            60,
            TimeUnit.SECONDS,
            TaskExecutors.MAIN_THREAD,
            mCallBack
        )
    }

    var mCallBack = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        override fun onVerificationCompleted(p0: PhoneAuthCredential?) {
            val code = p0!!.smsCode
            code?.let {
                val sCode = SpannableStringBuilder(it)
                txt_pin_entry.text = sCode
                progressLoading.showWithAnimation()
                verifyCode(code)
            }
        }

        override fun onVerificationFailed(p0: FirebaseException?) {
            p0?.message?.let { toast(it) }
        }

        override fun onCodeSent(p0: String?, p1: PhoneAuthProvider.ForceResendingToken?) {
            super.onCodeSent(p0, p1)
            p0?.let {
                verificationId = it
            }
        }
    }

    override fun onComplete(p0: Task<AuthResult>) {
        if (p0.isSuccessful) {
            val user = User()
            user.phone = numberToVerify
            userRepo.ChangesUserStatus(true)
            Coroutines.main {
                userRepo.UpdateUserData(user)
                Intent(this, HomeActivity::class.java).also {
                    it.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    it.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    startActivity(it)
                }
            }
        } else {
            progressLoading.HideWithAnimation()
            toast(p0.exception!!.message!!)
        }
    }
}
