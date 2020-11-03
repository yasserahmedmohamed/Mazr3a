package com.yasser.mazr3a_task.data.network.responses

import com.yasser.mazr3a_task.model.Product

data class ProductResponse(
    val count: Int,
    val items: List<Product>,
    val limit: Int,
    val offset: Int,
    val total: Int
)