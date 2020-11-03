package com.yasser.mazr3a_task.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yasser.mazr3a_task.R
import com.yasser.mazr3a_task.model.Category
import com.yasser.mazr3a_task.model.Product
import com.yasser.mazr3a_task.ui.home.mainfragment.MainAdapterListener
import com.yasser.mazr3a_task.ui.home.mainfragment.MainFragmentListener
import com.yasser.mazr3a_task.utils.Format
import com.yasser.mazr3a_task.utils.LoadImage

class CategoryWithProductsAdapter(val context: Context) :
    RecyclerView.Adapter<BaseViewHolder>() {
    val productList = ArrayList<Product>()
    var listener: MainAdapterListener?=null
    fun UpdateProducts(list: List<Product>) {
        productList.clear()
        productList.addAll(list)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_product_in_category,
            parent, false
        )
        val vHolder = productViewHolder(view)

        return vHolder
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val item = productList.get(position)
        holder.onBind(context,item )
        (holder as productViewHolder).container.setOnClickListener {
            listener?.onProductTapped(item)
        }
    }

    class productViewHolder(item: View) : BaseViewHolder(item) {

        var productImage:ImageView
        var productName:TextView
        var productDescription:TextView
        var productPrice:TextView
        var productWeight:TextView
        var container:View
        init {
            container = item
            productImage = item.findViewById(R.id.productImage)
            productName = item.findViewById(R.id.productName)
            productDescription = item.findViewById(R.id.productDescription)
            productPrice = item.findViewById(R.id.productPrice)
            productWeight = item.findViewById(R.id.productWeight)

        }


        override fun onBind(context: Context, item: Product) {
            if (item != null) {
                item.mageUrl?.let { productImage.LoadImage(it, context) }
                productName.text = item.name
                productDescription.text = item.fromatedDescription
                productPrice.text = "${context.getString(R.string.EGP)} ${item.price}"
                productWeight.text = item.getWeight()
            }
        }
    }
}