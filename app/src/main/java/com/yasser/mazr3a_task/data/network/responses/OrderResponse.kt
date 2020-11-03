package com.yasser.mazr3a_task.data.network.responses

import com.yasser.mazr3a_task.model.Order

data class OrderResponse(
    val count: Int,
    val items: List<Order>,
    val limit: Int,
    val offset: Int,
    val total: Int
)