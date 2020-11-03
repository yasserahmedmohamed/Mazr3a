package com.yasser.mazr3a_task.ui.home.userfragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.yasser.mazr3a_task.R
import com.yasser.mazr3a_task.repository.UserRepository
import com.yasser.mazr3a_task.ui.login.LoginActivity
import kotlinx.android.synthetic.main.fragment_user.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

/**
 * A simple [Fragment] subclass.
 */
class UserFragment : Fragment(), KodeinAware {
    override val kodein by kodein()
    private val userRepo: UserRepository by instance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        logutBtn.setOnClickListener {
            userRepo.ChangesUserStatus(false)
            Intent(requireContext(), LoginActivity::class.java).also {
                it.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                it.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(it)
            }
        }
    }

}
