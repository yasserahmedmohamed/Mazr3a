package com.yasser.mazr3a_task.ui.category

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.yasser.mazr3a_task.R
import com.yasser.mazr3a_task.ViewModelFactory.CategoryDetailsViewModelFactory
import com.yasser.mazr3a_task.adapter.ProductsAdapter
import com.yasser.mazr3a_task.model.Product
import com.yasser.mazr3a_task.ui.BaseActivity
import com.yasser.mazr3a_task.ui.product_details.ProductDetailsActivity
import com.yasser.mazr3a_task.utils.HideWithAnimation
import com.yasser.mazr3a_task.utils.showWithAnimation
import com.yasser.mazr3a_task.utils.toast
import kotlinx.android.synthetic.main.activity_category_details.*
import kotlinx.android.synthetic.main.activity_category_details.loagingProgress
import kotlinx.android.synthetic.main.activity_category_details.swipeRefresh
import kotlinx.android.synthetic.main.fragment_main.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class CategoryDetailsActivity : BaseActivity(), CategoryDetailsListener,
    PopupMenu.OnMenuItemClickListener, SwipeRefreshLayout.OnRefreshListener {
    private val factory: CategoryDetailsViewModelFactory by instance()
    private lateinit var viewModel: CategoryDetailsViewModel
    var CategoryID:String=""

    companion object {
        val CATEGORY_NAME_BUNDLE = "Category_name"

        val CATEGORY_ID_BUNDLE = "Category_ID"
    }

    lateinit var productAdapter: ProductsAdapter

    override fun InternetConnectionChanged(isConnected: Boolean) {
        if (isConnected) {
//            mainFragment = MainFragment()
//            loadFragment(mainFragment)
            NoConnectionLayout.HideWithAnimation()
        } else {

            NoConnectionLayout.showWithAnimation()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_details)
        viewModel = ViewModelProviders.of(this, factory).get(CategoryDetailsViewModel::class.java)
        viewModel.listener = this
        category_name.text = intent.getStringExtra(CATEGORY_NAME_BUNDLE)
        intent.getStringExtra(CATEGORY_ID_BUNDLE)?.let {
            CategoryID = it
            viewModel.GetProducts(it)

        }

        productAdapter = ProductsAdapter(this)
        InitRecycleView()
        viewModel.DisplayProducts.observe(this, Observer {
            productAdapter.UpdateProductList(it)
        })

        backBtn.setOnClickListener {
            onBackPressed()
        }
        sortSpinner.setOnClickListener {
            val popup = PopupMenu(this, it)
            popup.setOnMenuItemClickListener(this)

            popup.inflate(R.menu.sort_menu)
            popup.show()
        }
        swipeRefresh.setOnRefreshListener(this)
    }

    fun InitRecycleView() {
        productAdapter.listener = this
        productRecyleView.setHasFixedSize(true)
        productRecyleView.setLayoutManager(GridLayoutManager(this, 2))
        productRecyleView.adapter = productAdapter
    }

    override fun OnAddToCartTapped(product: Product) {
        val intent = Intent(this, ProductDetailsActivity::class.java)
        intent.putExtra(ProductDetailsActivity.PRODUCT_DATA, product)
        startActivity(intent)

    }

    override fun showLoadingView() {
        loagingProgress.showWithAnimation()
    }

    override fun HideLoadingView() {
        loagingProgress.HideWithAnimation()
        swipeRefresh.isRefreshing = false
    }

    override fun OnErrorHappen(error: String) {
        toast(error)
        loagingProgress.HideWithAnimation()
        swipeRefresh.isRefreshing = false
    }


    override fun onMenuItemClick(p0: MenuItem?): Boolean {
        when(p0?.itemId){
            R.id.filter_2->{
                viewModel.GetProductsBySort(CategoryID,"ADDED_TIME_DESC")
            }//new products
            R.id.filter_3->{
                viewModel.GetProductsBySort(CategoryID,"PRICE_ASC")
            }//price less -> up PRICE_ASC
            R.id.filter_4->{
                viewModel.GetProductsBySort(CategoryID,"PRICE_DESC")
            }//price up -> less PRICE_DESC
            R.id.filter_5->{
                viewModel.GetProductsBySort(CategoryID,"NAME_ASC")
            }//name a -> z NAME_ASC
            R.id.filter_6->{
                viewModel.GetProductsBySort(CategoryID,"NAME_DESC")
            }//name z -> a NAME_DESC
        }
        return false
    }

    override fun onRefresh() {
        viewModel.GetProducts(CategoryID)
    }

}
