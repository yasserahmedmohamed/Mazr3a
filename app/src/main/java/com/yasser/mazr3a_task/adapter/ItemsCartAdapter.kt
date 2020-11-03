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
import com.yasser.mazr3a_task.model.ItemCartData
import com.yasser.mazr3a_task.ui.home.cartfragment.CartFragmentListener
import com.yasser.mazr3a_task.utils.LoadImage

class ItemsCartAdapter(val context: Context) :RecyclerView.Adapter<ItemsCartAdapter.CartItemViewHolder>(){

   private val items = ArrayList<ItemCartData>()
    var listener: CartFragmentListener?=null
    fun UpdateItems(it:List<ItemCartData>){
        items.clear()
        items.addAll(it)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_cart_data,
            parent, false
        )
        val vHolder = CartItemViewHolder(view)

        return vHolder
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: CartItemViewHolder, position: Int) {
        val item = items.get(position)
       holder.onBind(context,item)
        holder.addcountBtn.setOnClickListener {
            listener?.OnAddBtnTapped(item)
        }
        holder.subcountBtn.setOnClickListener {
            listener?.OnSubBtnTapped(item)

        }
        holder.deleteBtn.setOnClickListener {
            listener?.OnDeleteBtnTapped(item)

        }
    }

    class CartItemViewHolder(itemView:View): RecyclerView.ViewHolder(itemView){
        var productImage:ImageView
        var productName:TextView
        var productPrice:TextView
        var productItems:TextView
        var addcountBtn:Button
        var subcountBtn:Button
        var deleteBtn:Button
        init {
            productImage = itemView.findViewById(R.id.productImage)
            productName = itemView.findViewById(R.id.productName)
            productPrice = itemView.findViewById(R.id.productPrice)
            productItems = itemView.findViewById(R.id.productItems)
            addcountBtn = itemView.findViewById(R.id.addcountBtn)
            subcountBtn = itemView.findViewById(R.id.subcountBtn)
            deleteBtn = itemView.findViewById(R.id.deleteBtn)

        }

        fun onBind(context: Context,itemData:ItemCartData){
            productImage.LoadImage(itemData.productImage,context)
            productName.text = itemData.productName
            productPrice.text = "${context.getString(R.string.EGP)} ${itemData.productTotlaPrice}"
            productItems.text = itemData.productItems.toString()

        }

    }

}