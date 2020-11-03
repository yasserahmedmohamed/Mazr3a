package com.yasser.mazr3a_task.ui.orders

import android.os.Bundle
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.yasser.mazr3a_task.R
import com.yasser.mazr3a_task.ViewModelFactory.OrderViewModelFactory
import com.yasser.mazr3a_task.adapter.OrderAdapter
import com.yasser.mazr3a_task.ui.BaseActivity
import com.yasser.mazr3a_task.utils.Coroutines
import com.yasser.mazr3a_task.utils.HideWithAnimation
import com.yasser.mazr3a_task.utils.showWithAnimation
import kotlinx.android.synthetic.main.activity_order.*
import org.kodein.di.generic.instance

class OrderActivity : BaseActivity(), OrderListener, SwipeRefreshLayout.OnRefreshListener {

    private val factory: OrderViewModelFactory by instance()
    private lateinit var viewModel: OrderViewModel
    lateinit var mOrderAdapter: OrderAdapter


    override fun InternetConnectionChanged(isConnected: Boolean) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)
        viewModel = ViewModelProviders.of(this, factory).get(OrderViewModel::class.java)
        viewModel.listener = this
        mOrderAdapter = OrderAdapter(this)
        OrderRecyleView.layoutManager = LinearLayoutManager(this)
        OrderRecyleView.adapter = mOrderAdapter


        viewModel.GetOrders()
        viewModel.displayOrder.observe(this, Observer {
            mOrderAdapter.UpdateOrders(it)
        })

        swipeRefresh.setOnRefreshListener(this)
        backBtn.setOnClickListener { finish() }
    }

    override fun OnLoading() {
        Coroutines.main {
            loagingProgress.showWithAnimation()
        }
    }

    override fun OnFinishLoading() {
        loagingProgress.HideWithAnimation()
        swipeRefresh.isRefreshing = false
    }

    override fun onRefresh() {

            viewModel.GetOrders()

    }
}
