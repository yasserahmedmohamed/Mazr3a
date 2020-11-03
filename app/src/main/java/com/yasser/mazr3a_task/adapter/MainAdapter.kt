package com.yasser.mazr3a_task.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yasser.mazr3a_task.R
import com.yasser.mazr3a_task.model.Category
import com.yasser.mazr3a_task.model.MainModel
import com.yasser.mazr3a_task.ui.home.mainfragment.MainAdapterListener


class MainAdapter(
    val context: Context
) :
    RecyclerView.Adapter<BaseViewHolder>() {
    private val Categories = ArrayList<Category>()

    private val CategoriesWithProducts = ArrayList<MainModel>()
    var listener: MainAdapterListener? = null

    companion object {
        val VIEW_TYPE_IMAGE = 0
        val VIEW_TYPE_ALL_CATEGORY = 1
        val VIEW_TYPE_NORMAL = 2
    }

    fun updateCategoryList(items: List<Category>){
        Categories.clear()
        Categories.addAll(items)
        notifyDataSetChanged()
    }

    fun updateCategoryWithProductList(items: ArrayList<MainModel>) {
        CategoriesWithProducts.clear()
        CategoriesWithProducts.addAll(items)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        when (viewType) {
            VIEW_TYPE_IMAGE -> {
                val view = LayoutInflater.from(parent.context).inflate(
                    R.layout.item_icon,
                    parent, false
                )
                val vHolder = ImageViewHolder(view)

                return vHolder
            }
            VIEW_TYPE_ALL_CATEGORY -> {
                val view = LayoutInflater.from(parent.context).inflate(
                    R.layout.item_all_categories,
                    parent, false
                )

                val vHolder = CategoriesViewHolder(view)
                return vHolder
            }
            else -> {
                val view = LayoutInflater.from(parent.context).inflate(
                    R.layout.item_category_with_products,
                    parent, false
                )
                val vHolder = CategoryWithProductsViewHolder(view)

                return vHolder
            }
        }
    }

    override fun getItemCount(): Int {
        return (CategoriesWithProducts.size + 2)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
//        if (holder )
        if (holder.itemViewType == VIEW_TYPE_NORMAL) {
            if ((position-2)<CategoriesWithProducts.size) {
                val item = CategoriesWithProducts.get(position - 2)
                holder.onBind(listener, context, item)
                (holder as CategoryWithProductsViewHolder).showAllProductBtn.setOnClickListener {
                    listener?.onCategoryTapped(item.category)
                }
            }
        } else if (holder.itemViewType == VIEW_TYPE_ALL_CATEGORY) {

            holder.onBind(listener,context, Categories)
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0)
            return VIEW_TYPE_IMAGE
        else if (position == 1)
            return VIEW_TYPE_ALL_CATEGORY
        else {
            return VIEW_TYPE_NORMAL
        }
    }


    class CategoriesViewHolder(item: View) : BaseViewHolder(item) {
        var AllCategry_picker: RecyclerView
        var adapter: AllCategoryAdapter

        init {
            AllCategry_picker = item.findViewById(R.id.AllCategry_picker)
            adapter = AllCategoryAdapter(item.context)
            val layoutManager =
                LinearLayoutManager(item.context, LinearLayoutManager.HORIZONTAL, false);

            AllCategry_picker.setAdapter(adapter)
            AllCategry_picker.layoutManager = layoutManager

        }

        override fun onBind(listener: MainAdapterListener?,context: Context, item: ArrayList<Category>) {
            super.onBind(listener,context, item)
            adapter.listener = listener
            adapter.UpdateCategories(item)
        }

    }

    class ImageViewHolder(item: View) : BaseViewHolder(item) {}


    class CategoryWithProductsViewHolder(item: View) :
        BaseViewHolder(item) {

        var category_name: TextView
        var productsRecycleView: RecyclerView
        var showAllProductBtn: Button
        lateinit var mProductCategory: CategoryWithProductsAdapter

        init {
            category_name = item.findViewById(R.id.category_name)
            productsRecycleView = item.findViewById(R.id.productsRecycleView)
            showAllProductBtn = item.findViewById(R.id.showAllProductBtn)
            val layoutManager =
                LinearLayoutManager(item.context, LinearLayoutManager.HORIZONTAL, false);

            productsRecycleView.layoutManager = layoutManager
        }

        override fun onBind(listener: MainAdapterListener?,context: Context, item: MainModel) {
            super.onBind(listener,context, item)
            mProductCategory = CategoryWithProductsAdapter(context)
            productsRecycleView.adapter = mProductCategory
            category_name.text = item.category.name
            mProductCategory.UpdateProducts(item.categProducts)
            mProductCategory.listener = listener
        }

    }
}