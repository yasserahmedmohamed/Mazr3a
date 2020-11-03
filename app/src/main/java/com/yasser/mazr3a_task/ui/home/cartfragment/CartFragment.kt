package com.yasser.mazr3a_task.ui.home.cartfragment

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.yasser.mazr3a_task.R
import com.yasser.mazr3a_task.ViewModelFactory.CartViewModelFactory
import com.yasser.mazr3a_task.adapter.ItemsCartAdapter
import com.yasser.mazr3a_task.model.ItemCartData
import com.yasser.mazr3a_task.ui.home.HomeActivity
import com.yasser.mazr3a_task.utils.HideWithAnimation
import com.yasser.mazr3a_task.utils.showWithAnimation
import kotlinx.android.synthetic.main.fragment_cart.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance


/**
 * A simple [Fragment] subclass.
 * Use the [CartFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CartFragment : Fragment(), KodeinAware, CartFragmentListener {

    override val kodein by kodein()
    private val factory: CartViewModelFactory by instance()
    private lateinit var viewModel: CartViewModel
    lateinit var cartsAdapter: ItemsCartAdapter
    lateinit var layoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewModel = ViewModelProviders.of(this, factory).get(CartViewModel::class.java)
        cartsAdapter = ItemsCartAdapter(requireContext())
        layoutManager = LinearLayoutManager(context)
        cartsAdapter.listener = this
        return inflater.inflate(R.layout.fragment_cart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.items.observe(viewLifecycleOwner, Observer {
            if (it.size > 0) {
                mainContainerLayout.showWithAnimation()
                noDataLayout.HideWithAnimation()
                var subtotal = 0
                for (item in it) {
                    var itemPrice = item.productTotlaPrice
                    subtotal += itemPrice
                }
                subTotalCost.text = "${getString(R.string.EGP)} ${subtotal}"
                shippingCost.text = "${getString(R.string.EGP)} ${viewModel.GetShippingCost()}"
                totalCost.text =
                    "${getString(R.string.EGP)} ${subtotal + viewModel.GetShippingCost()}"
                cartsAdapter.UpdateItems(it)
            }
            else{
                mainContainerLayout.HideWithAnimation()
                noDataLayout.showWithAnimation()
            }
        })

        ContinueBtn.setOnClickListener {
            (activity as HomeActivity).CheckOut()

        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        cartItemRecycle.layoutManager = layoutManager
        cartItemRecycle.adapter = cartsAdapter

    }

    override fun OnAddBtnTapped(itemData: ItemCartData) {
        var it = itemData.productItems
        it++
        itemData.productItems = it
        viewModel.UpdateProductOrder(itemData)

    }

    override fun OnSubBtnTapped(itemData: ItemCartData) {
        var it = itemData.productItems
        if (it>1){
            var it = itemData.productItems
            it--
            itemData.productItems = it
            viewModel.UpdateProductOrder(itemData)
        }
        else{
            DeleteItem(itemData)
        }
    }

    override fun OnDeleteBtnTapped(itemData: ItemCartData) {
        DeleteItem(itemData)

    }

    fun DeleteItem(itemData: ItemCartData){
        AlertDialog.Builder(context)
            .setTitle(getString(R.string.deleteProduct))
            .setMessage(getString(R.string.are_you_sure))
            .setPositiveButton(getString(R.string.yes),
                DialogInterface.OnClickListener { dialog, which ->
                    viewModel.RemoveProductFromCart(itemData.productID)
                })
            .setNegativeButton(getString(R.string.no), null)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .show()

    }
}
