package com.yasser.mazr3a_task.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yasser.mazr3a_task.R
import com.yasser.mazr3a_task.model.Product
import com.yasser.mazr3a_task.ui.category.CategoryDetailsListener
import com.yasser.mazr3a_task.utils.Format
import com.yasser.mazr3a_task.utils.LoadImage

class ProductsAdapter(val context: Context) :
    RecyclerView.Adapter<ProductsAdapter.ProductViewHolder>() {

    private val mProducts = ArrayList<Product>()
    var listener: CategoryDetailsListener? = null
    fun UpdateProductList(list: List<Product>) {
        mProducts.clear()
        mProducts.addAll(list)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_product,
            parent, false
        )
        val vHolder = ProductViewHolder(view)

        return vHolder
    }

    override fun getItemCount(): Int {
        return mProducts.size
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val item = mProducts.get(position)
        holder.OnBind(context, item)
        holder.container.setOnClickListener {
            listener?.OnAddToCartTapped(item)
        }
        holder.addtoCartBtn.setOnClickListener {
            listener?.OnAddToCartTapped(item)
        }
    }

    class ProductViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        var container:View
        var productImage: ImageView
        var productName: TextView
        var productWeight: TextView
        var productDescription: TextView
        var productPrice: TextView
        var addtoCartBtn: Button

        init {
            container = item
            productImage = item.findViewById(R.id.productImage)
            productName = item.findViewById(R.id.productName)
            productWeight = item.findViewById(R.id.productWeight)
            productDescription = item.findViewById(R.id.productDescription)
            productPrice = item.findViewById(R.id.productPrice)
            addtoCartBtn = item.findViewById(R.id.addtoCartBtn)

        }

        fun OnBind(context: Context, item: Product) {
            item.mageUrl?.let { productImage.LoadImage(it, context) }
            productName.text = item.name
            productDescription.text = item.fromatedDescription
            productPrice.text = "${context.getString(R.string.EGP)} ${item.price}"
            productWeight.text = item.getWeight()
        }


    }

}