package com.yasser.mazr3a_task.repository

import androidx.lifecycle.LiveData
import com.yasser.mazr3a_task.data.db.AppDatabase
import com.yasser.mazr3a_task.data.network.SafeApiRequest
import com.yasser.mazr3a_task.data.network.ServiceApi
import com.yasser.mazr3a_task.model.ItemCartData
import com.yasser.mazr3a_task.model.Product

class ProductRepository(private val serviceApi: ServiceApi,private val db:AppDatabase) : SafeApiRequest(){

    suspend fun GetLimitPoducts(cateID:String, offset:String):List<Product>{
        val response = apiRequest { serviceApi.GetCategoryLimitProducts(cateID,offset) }

        return response.items
    }


    suspend fun GetCategoryProducts(cateID:String, offset:String):List<Product>{
        val response = apiRequest { serviceApi.GetProductsByCategoryID(cateID,offset) }

        return response.items
    }

    suspend fun GetCategoryProductsBySort(cateID:String,sortType:String, offset:String):List<Product>{
        val response = apiRequest { serviceApi.GetProductsByCategoryIDSorted(cateID,sortType,offset) }

        return response.items
    }
    suspend fun AddProdutToCart(item:ItemCartData){
        db.getCartDao().insert(item)
    }

    fun GetShippingCost():Int{
        return 50
    }


    suspend fun GetFinalCartItems():List<ItemCartData>{
        return db.getCartDao().GetFinalProduct()
    }
     fun GetCartItems():LiveData<List<ItemCartData>>{
        return db.getCartDao().GetAllRecipe()
    }

    suspend fun UpdateProduct(item: ItemCartData)
    {
        db.getCartDao().update(item)
    }

    suspend fun DeleteProduct(ID:Int){
        db.getCartDao().DeleteProduct(ID)
    }

    suspend fun DeleteAllProduct(){
        db.getCartDao().DeleteAllData()
    }
}