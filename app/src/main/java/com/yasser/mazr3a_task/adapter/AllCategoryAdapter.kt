package com.yasser.mazr3a_task.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.yasser.mazr3a_task.R
import com.yasser.mazr3a_task.model.Category
import com.yasser.mazr3a_task.ui.home.mainfragment.MainAdapterListener
import com.yasser.mazr3a_task.utils.LoadImage

class AllCategoryAdapter(val context: Context) :
    RecyclerView.Adapter<BaseViewHolder>() {
    private val catList = ArrayList<Category>()
    var listener:MainAdapterListener?=null
    fun UpdateCategories(items:ArrayList<Category>){
        catList.clear()
        catList.addAll(items)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_category,
            parent, false
        )
        val vHolder = CategoryViewHolder(view)

        return vHolder
    }

    override fun getItemCount(): Int {
        return catList.size
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {

        val item = catList.get(position)
        holder.onBind(context,item )
        (holder as CategoryViewHolder).container.setOnClickListener {
            listener?.onCategoryTapped(item)
        }
    }

    class CategoryViewHolder(item: View) : BaseViewHolder(item) {
        lateinit var cat_image: ImageView
        lateinit var cat_name: TextView
        var container:View
        init {
            container = item
            cat_image = item.findViewById(R.id.cat_image)
            cat_name = item.findViewById(R.id.cat_name)
        }

        override fun onBind(context: Context, item: Category) {

            if (item.imageUrl != null) {
                cat_image.LoadImage(item.imageUrl, context)
            } else {
                cat_image.LoadImage(item.originalImageUrl, context)

            }

            cat_name.text = item.name
        }
    }
}