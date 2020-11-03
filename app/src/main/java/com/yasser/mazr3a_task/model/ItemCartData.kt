package com.yasser.mazr3a_task.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity()
data class ItemCartData(
    @PrimaryKey
    var productID:Int,
    val productName:String,
    val productOnePrice :Int,
    var productItems :Int,
    val productImage :String
){
    val productTotlaPrice :Int
    get() {
        return (productOnePrice*productItems)
    }

}