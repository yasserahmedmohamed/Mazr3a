package com.yasser.mazr3a_task.adapter

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.yasser.mazr3a_task.model.Category
import com.yasser.mazr3a_task.model.MainModel
import com.yasser.mazr3a_task.model.Product
import com.yasser.mazr3a_task.ui.home.mainfragment.MainAdapterListener

abstract class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    open fun onBind(listener: MainAdapterListener?, context: Context, item: MainModel) {}
    open fun onBind(context: Context, item: Category) {}
    open fun onBind(context: Context, item: Product) {}
    open fun onBind(listener: MainAdapterListener?,context: Context, item: ArrayList<Category>) {}

}