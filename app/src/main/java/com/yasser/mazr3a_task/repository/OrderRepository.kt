package com.yasser.mazr3a_task.repository

import android.util.Log
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.yasser.mazr3a_task.data.db.AppDatabase
import com.yasser.mazr3a_task.data.network.SafeApiRequest
import com.yasser.mazr3a_task.data.network.ServiceApi
import com.yasser.mazr3a_task.model.ItemCartData
import com.yasser.mazr3a_task.model.User

class OrderRepository(private val serviceApi: ServiceApi, private val db: AppDatabase) :
    SafeApiRequest() {


    suspend fun MakeOrder(orderItems: List<ItemCartData>, userData: User, shippingCost: Int,paymentMethod:String):Boolean {
        val objectData = JsonObject()
        val jsonOrders = JsonArray()
        val jsonPerson = JsonObject()

        var subTotal = 0
        for (item in orderItems) {
            val itemJson = JsonObject()
            itemJson.addProperty("name", item.productName)
            itemJson.addProperty("quantity", item.productItems)
            itemJson.addProperty("productId", item.productID)
            itemJson.addProperty("productPrice", item.productOnePrice)//one item price
            itemJson.addProperty("price", item.productTotlaPrice)//total price
            jsonOrders.add(itemJson)
            subTotal += item.productTotlaPrice
        }
        objectData.addProperty("subtotal",subTotal)
        objectData.addProperty("total",(subTotal+shippingCost))
        objectData.addProperty("email",userData.email)
        objectData.addProperty("paymentStatus","AWAITING_PAYMENT")
        objectData.addProperty("paymentMethod",paymentMethod)


        // order items
        objectData.add("items", jsonOrders)

        // person info PersonInfo
        jsonPerson.addProperty("name",userData.name)
        jsonPerson.addProperty("street",userData.homeLocation)
        jsonPerson.addProperty("phone",userData.phone)

        objectData.add("billingPerson", jsonPerson)

        Log.e("orderData",objectData.toString())
        val response = apiRequest{ serviceApi.MakeOrder(objectData)}

        return true
    }

}