package com.yasser.mazr3a_task.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import com.yasser.mazr3a_task.R
import com.yasser.mazr3a_task.ViewModelFactory.HomeViewModelFactory
import com.yasser.mazr3a_task.databinding.ActivityHomeBinding
import com.yasser.mazr3a_task.model.User
import com.yasser.mazr3a_task.ui.BaseActivity
import com.yasser.mazr3a_task.ui.home.cartfragment.CartFragment
import com.yasser.mazr3a_task.ui.home.mainfragment.MainFragment
import com.yasser.mazr3a_task.ui.home.userfragment.UserFragment
import com.yasser.mazr3a_task.ui.login.LoginActivity
import com.yasser.mazr3a_task.utils.Coroutines
import com.yasser.mazr3a_task.utils.HideWithAnimation
import com.yasser.mazr3a_task.utils.showWithAnimation
import kotlinx.android.synthetic.main.activity_home.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance


class HomeActivity : BaseActivity(), HomeListener,
    BottomNavigationView.OnNavigationItemSelectedListener {

    private val factory: HomeViewModelFactory by instance()
    private lateinit var viewModel: HomeViewModel

    lateinit var mainFragment: MainFragment
    lateinit var cartFragment: CartFragment
    lateinit var userFragment: UserFragment
    override fun InternetConnectionChanged(isConnected: Boolean) {
//        runOnUiThread(object :Runnable{
//            override fun run() {
        if (isConnected) {
//            mainFragment = MainFragment()
//            loadFragment(mainFragment)
            NoConnectionLayout.HideWithAnimation()
        } else {

            NoConnectionLayout.showWithAnimation()
        }
        //  }

        //})

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_home)
        val databinding: ActivityHomeBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_home)
        viewModel = ViewModelProviders.of(this, factory).get(HomeViewModel::class.java)
        databinding.viewmodel = viewModel
        databinding.lifecycleOwner = this
        viewModel.listener = this

        mainFragment = MainFragment()
        cartFragment = CartFragment()
        userFragment = UserFragment()

        loadFragment(mainFragment)
        bottomNavBar.setOnNavigationItemSelectedListener(this)


        viewModel.user.observe(this, Observer {
            if (it == null) {
                val user = User()
                viewModel.displayUser.postValue(user)

            } else {

                viewModel.displayUser.postValue(it)
            }
        })
        sliding_layout.addPanelSlideListener(object : SlidingUpPanelLayout.PanelSlideListener {
            override fun onPanelSlide(panel: View?, slideOffset: Float) {
            }

            override fun onPanelStateChanged(
                panel: View?,
                previousState: SlidingUpPanelLayout.PanelState?,
                newState: SlidingUpPanelLayout.PanelState?
            ) {
                if (newState == SlidingUpPanelLayout.PanelState.EXPANDED) {
                    dragView.background = ContextCompat.getDrawable(
                        this@HomeActivity,
                        R.drawable.layout_white_background
                    )
                    top_line.HideWithAnimation()
                } else {
                    dragView.background = ContextCompat.getDrawable(
                        this@HomeActivity,
                        R.drawable.corner_white_background
                    )
                    top_line.showWithAnimation()
                }

            }

        })

    }


    fun CheckOut() {
        if (viewModel.IsUserLoggedIn()) {
            sliding_layout.setAnchorPoint(0.7f);

            sliding_layout.setPanelState(SlidingUpPanelLayout.PanelState.ANCHORED)
        } else {
            Intent(this, LoginActivity::class.java).also {
                it.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                it.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(it)
            }
        }
    }

    override fun onBackPressed() {
        if (sliding_layout != null &&
            (sliding_layout.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED || sliding_layout.getPanelState() == SlidingUpPanelLayout.PanelState.ANCHORED)
        ) {
            sliding_layout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
        }else if (bottomNavBar.selectedItemId != R.id.mainFragment){
            bottomNavBar.selectedItemId = R.id.mainFragment
        }

        else {
            super.onBackPressed();
        }
    }

    override fun ShowErrorMessage(viewID: Int, message: String) {
        clearError()
        when (viewID) {
            R.id.userName -> {
                userName.error = message
            }
            R.id.userEmail -> {
                userEmail.error = message
            }
            R.id.userPhone -> {
                userPhone.error = message
            }
        }
    }

    override fun HideErrorMessage() {
        clearError()
    }

    override fun OnOrderSent() {
        sliding_layout.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
        LoadingProgress.HideWithAnimation()
    }

    override fun ShowLoading() {
        Coroutines.main {
            LoadingProgress.showWithAnimation()
        }
    }

    fun clearError() {
        userPhone.isErrorEnabled = false
        userEmail.isErrorEnabled = false
        userName.isErrorEnabled = false
    }

    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        when (p0.itemId) {
            R.id.mainFragment -> {
                if (bottomNavBar.selectedItemId != R.id.mainFragment) {
                    loadFragment(mainFragment)
                }
                return true
            }
            R.id.cartFragment -> {
                if (bottomNavBar.selectedItemId != R.id.cartFragment) {
                    loadFragment(cartFragment)
                }
                return true
            }
            R.id.userFragment -> {

                if (!viewModel.IsUserLoggedIn()) {
                    // openLoginNumber()
                    Intent(this, LoginActivity::class.java).also {
                        it.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        it.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        startActivity(it)
                    }
                    return false
                } else {
                    if (bottomNavBar.selectedItemId != R.id.userFragment) {
                        loadFragment(userFragment)
                    }
                    return true
                }
            }

        }
        return false
    }

    private fun loadFragment(fragment: Fragment) {
        // load fragment
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.main_fragment, fragment)
        //transaction.addToBackStack(null)
        transaction.commit()
    }

}
