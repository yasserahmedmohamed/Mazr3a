package com.yasser.mazr3a_task.ui.product_details

import android.app.Application
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import com.yasser.mazr3a_task.model.ItemCartData
import com.yasser.mazr3a_task.model.Product
import com.yasser.mazr3a_task.repository.ProductRepository
import com.yasser.mazr3a_task.utils.Coroutines

class ProductDetailsViewModel(application:Application,private val productRepository: ProductRepository):AndroidViewModel(application) {
    private val context = getApplication<Application>().applicationContext
    var listener:ProductDetailsListener?=null
    val DisplayProduct=MutableLiveData<Product>()
    val Displayprice=MutableLiveData<String>()
    val DisplayItems=MutableLiveData<String>()

    var ProductImage: ObservableField<Drawable>
    lateinit var bindableFieldTarget: BindableFieldTarget

    init {
        ProductImage =  ObservableField<Drawable>()

    }

    fun AddItem(view:View){
        val items = DisplayItems.value
       var inIt =  items!!.toInt()
        inIt +=1
        DisplayItems.postValue((inIt).toString())
        val price = DisplayProduct.value!!.price
        val inPrice = price.toInt()
        val newPrice = inIt*inPrice
        Displayprice.postValue(newPrice.toString())
    }
    fun subItem(view:View){
        val items = DisplayItems.value
        var inIt =  items!!.toInt()
        if (inIt>1) {
            inIt -= 1
            DisplayItems.postValue((inIt).toString())
            val price = DisplayProduct.value!!.price
            val inPrice = price.toInt()
            val newPrice = inIt * inPrice
            Displayprice.postValue(newPrice.toString())
        }
    }

fun AddToCart(view: View)
{
    val productID = DisplayProduct.value!!.id
    val productName = DisplayProduct.value!!.name
    val productOnePrice = DisplayProduct.value!!.price
    val productItems = DisplayItems.value!!

    val productImage=DisplayProduct.value!!.mageUrl
    val itemCart= ItemCartData(productID,productName,productOnePrice.toInt(),productItems.toInt(),productImage!!)
    Coroutines.io {
        productRepository.AddProdutToCart(itemCart)
        listener?.OnProductAdded()
    }
}
    fun OnBackTapped(view: View)
    {
        listener?.OnBackTapped()
    }




    fun SetImage(url:String){
        Coroutines.main {
            bindableFieldTarget = BindableFieldTarget(ProductImage, context.resources)
            Picasso.with(context)
                .load(url)
                //.placeholder(R.drawable.placeholder)
                .into(bindableFieldTarget)
        }
    }

    class BindableFieldTarget(var observableField: ObservableField<Drawable>, var resources: Resources):
        Target {

        override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
            observableField.set(placeHolderDrawable);
        }

        override fun onBitmapFailed(errorDrawable: Drawable?) {
            observableField.set(errorDrawable)
        }

        override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
            observableField.set( BitmapDrawable(resources, bitmap))
        }


    }

}