package com.yasser.mazr3a_task.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.yasser.mazr3a_task.R
import com.yasser.mazr3a_task.model.Order
import com.yasser.mazr3a_task.utils.LoadImage
import com.yasser.mazr3a_task.utils.showWithAnimation

class OrderIAdapter(val context: Context) :
    RecyclerView.Adapter<OrderIAdapter.OrderItemViewHolder>() {
    val orders = ArrayList<Order>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_order,
            parent, false
        )
        val vHolder = OrderItemViewHolder(view)

        return vHolder
    }

    override fun getItemCount(): Int {
        return orders.size
    }

    override fun onBindViewHolder(holder: OrderItemViewHolder, position: Int) {
        val order = orders.get(position)
        holder.onBind(context, order)
        holder.order_number_layout.setOnClickListener {
//            holder.arrowBtn.background =  ContextCompat.getDrawable(
//                context,
//                R.drawable.layout_white_background
//            )
            holder.arrowBtn.rotation = 90F
            holder.subToalLayout.showWithAnimation()
        }
    }


    class OrderItemViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        var order_number: TextView
        var OrderAddress: TextView
        var OrderPaymentWay: TextView
        var OrderTime: TextView
        var order_deliver_cost: TextView
        var fulfillmentStatus: TextView
        var order_total_cost: TextView
        var items_layout: LinearLayout
        var subToalLayout: LinearLayout
        var order_number_layout: LinearLayout
        var arrowBtn: Button


        init {
            order_number = itemView.findViewById(R.id.order_number)
            OrderAddress = itemView.findViewById(R.id.OrderAddress)
            OrderPaymentWay = itemView.findViewById(R.id.OrderPaymentWay)
            OrderTime = itemView.findViewById(R.id.OrderTime)
            order_deliver_cost = itemView.findViewById(R.id.order_deliver_cost)
            fulfillmentStatus = itemView.findViewById(R.id.fulfillmentStatus)
            order_total_cost = itemView.findViewById(R.id.order_total_cost)
            items_layout = itemView.findViewById(R.id.items_layout)
            subToalLayout = itemView.findViewById(R.id.subToalLayout)
            order_number_layout = itemView.findViewById(R.id.order_number_layout)
            arrowBtn = itemView.findViewById(R.id.arrowBtn)

        }

        fun onBind(context: Context, mOrder: Order) {
            val inflator =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

            order_number.text = "${mOrder.id}#"
            OrderAddress.text = mOrder.billingPerson.street
            OrderPaymentWay.text = mOrder.paymentMethod
            OrderTime.text = mOrder.createDate
            order_deliver_cost.text = (mOrder.total - mOrder.subtotal).toString()
            fulfillmentStatus.text = mOrder.fulfillmentStatus
            order_total_cost.text = mOrder.total.toString()
            for (item in mOrder.items) {
                val ActionView = inflator.inflate(R.layout.item_order_item, null)
                val OrderitemImage: ImageView = ActionView.findViewById(R.id.OrderitemImage)
                val OrderItemName: TextView = ActionView.findViewById(R.id.OrderItemName)
                val OrderItemPrice: TextView = ActionView.findViewById(R.id.OrderItemPrice)
                val OrderItemQuantity: TextView = ActionView.findViewById(R.id.OrderItemQuantity)

                OrderitemImage.LoadImage(item.imageUrl, context)
                OrderItemName.text = item.name
                OrderItemPrice.text = "${context.getString(R.string.EGP)} ${item.price}"
                OrderItemQuantity.text = " :الكميه ${item.quantity}"
                items_layout.addView(ActionView)

            }
        }

    }

}