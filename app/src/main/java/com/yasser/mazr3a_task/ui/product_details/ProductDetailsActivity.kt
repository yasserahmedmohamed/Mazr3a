package com.yasser.mazr3a_task.ui.product_details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.yasser.mazr3a_task.R
import com.yasser.mazr3a_task.ViewModelFactory.ProductDetailsViewModelFactory
import com.yasser.mazr3a_task.databinding.ActivityProductDetailsBinding
import com.yasser.mazr3a_task.model.Product
import com.yasser.mazr3a_task.ui.category.CategoryDetailsViewModel
import com.yasser.mazr3a_task.utils.Coroutines
import com.yasser.mazr3a_task.utils.toast
import kotlinx.android.synthetic.main.activity_product_details.*
import kotlinx.android.synthetic.main.item_product.*
import kotlinx.coroutines.delay
import org.kodein.di.KodeinAware
import org.kodein.di.generic.instance
import org.kodein.di.android.kodein

class ProductDetailsActivity : AppCompatActivity(), KodeinAware, ProductDetailsListener {
    override val kodein by kodein()
    private val factory: ProductDetailsViewModelFactory by instance()
    private lateinit var viewModel: ProductDetailsViewModel

    companion object {
        val PRODUCT_DATA = "product_Data"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_product_details)
        val databinding: ActivityProductDetailsBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_product_details)
        viewModel = ViewModelProviders.of(this, factory).get(ProductDetailsViewModel::class.java)
        databinding.viewmodel = viewModel
        databinding.lifecycleOwner = this
        viewModel.listener = this
        val product = intent.getSerializableExtra(PRODUCT_DATA) as Product
        viewModel.DisplayProduct.postValue(product)
        viewModel.Displayprice.postValue(product.price)
        viewModel.DisplayItems.postValue("1")
        product.mageUrl?.let { viewModel.SetImage(it) }
    }

    override fun OnProductAdded() {
        Coroutines.main {
            BtnAddToCart.text="✓"
            delay(300)
            BtnAddToCart.text = getString(R.string.add_to_cart)
        }
    }

    override fun OnBackTapped() {
        onBackPressed()
    }
}
