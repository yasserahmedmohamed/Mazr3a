package com.yasser.mazr3a_task.model

data class OrderItems(
    val couponApplied: Boolean,
    val digital: Boolean,
    val fixedShippingRate: Int,
    val fixedShippingRateOnly: Boolean,
    val hdThumbnailUrl: String,
    val id: Int,
    val imageUrl: String,
    val isShippingRequired: Boolean,
    val name: String,
    val price: Int,
    val productAvailable: Boolean,
    val productId: Int,
    val productPrice: Int,
    val quantity: Int,
    val quantityInStock: Int,
    val shipping: Int,
    val smallThumbnailUrl: String,
    val tax: Int,
    val taxable: Boolean,
    val trackQuantity: Boolean,
    val weight: Int
)