package com.yasser.mazr3a_task.ui.home.mainfragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.yasser.mazr3a_task.R
import com.yasser.mazr3a_task.ViewModelFactory.MainViewModelFactory
import com.yasser.mazr3a_task.adapter.MainAdapter
import com.yasser.mazr3a_task.model.Category
import com.yasser.mazr3a_task.model.Product
import com.yasser.mazr3a_task.ui.category.CategoryDetailsActivity
import com.yasser.mazr3a_task.ui.product_details.ProductDetailsActivity
import com.yasser.mazr3a_task.utils.Coroutines
import com.yasser.mazr3a_task.utils.HideWithAnimation
import com.yasser.mazr3a_task.utils.showWithAnimation
import com.yasser.mazr3a_task.utils.toast
import kotlinx.android.synthetic.main.fragment_main.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

/**
 * A simple [Fragment] subclass.
 */
class MainFragment : Fragment(), KodeinAware, MainFragmentListener, MainAdapterListener,
    SwipeRefreshLayout.OnRefreshListener {
    override val kodein by kodein()
    private val factory: MainViewModelFactory by instance()
    private lateinit var viewModel: MainViewModel
    lateinit var mainAdapter: MainAdapter
    lateinit var layoutManager: LinearLayoutManager
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewModel = ViewModelProviders.of(this, factory).get(MainViewModel::class.java)
        viewModel.mListener = this

        mainAdapter = MainAdapter(requireContext())
        layoutManager = LinearLayoutManager(context)
        mainAdapter.listener = this


        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        swipeRefresh.setOnRefreshListener(this)
        mainRecycleView.layoutManager = layoutManager
        mainRecycleView.adapter = mainAdapter
        viewModel.DisplayCategory.observe(requireActivity(), Observer {

            mainAdapter.updateCategoryList(it)
            for (item in it){
                viewModel.getCategoryProducts(item)
            }
        })
        viewModel.DisplayCategProducts.observe(requireActivity(), Observer {
            mainAdapter.updateCategoryWithProductList(it)

        })
        viewModel.GetCategories()
    }
    override fun onResume() {
        super.onResume()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }




    override fun onLoading() {

        Coroutines.main {
            loagingProgress.showWithAnimation()
        }


    }

    override fun onFinishLoading() {
        Coroutines.main {
          try {

              loagingProgress.HideWithAnimation()
          }catch (e:Exception){

          }
        }
        swipeRefresh.isRefreshing = false
    }

    override fun onNoErrorHappen(error: String) {
        requireActivity().runOnUiThread {
            requireContext().toast(error)
        }

    }

    override fun onCategoryTapped(cat: Category) {
        //action_mainFragment_to_categoryFragment
        val intent = Intent(requireContext(), CategoryDetailsActivity::class.java)
        intent.putExtra(CategoryDetailsActivity.CATEGORY_NAME_BUNDLE, cat.name)
        intent.putExtra(CategoryDetailsActivity.CATEGORY_ID_BUNDLE, cat.id.toString())
        startActivity(intent)

    }

    override fun onProductTapped(product: Product) {
        val intent = Intent(requireContext(), ProductDetailsActivity::class.java)
        intent.putExtra(ProductDetailsActivity.PRODUCT_DATA, product)
        startActivity(intent)
    }

    override fun onRefresh() {
        viewModel.GetCategories()

    }

}
