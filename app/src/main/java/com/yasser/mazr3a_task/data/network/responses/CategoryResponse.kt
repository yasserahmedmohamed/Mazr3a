package com.yasser.mazr3a_task.data.network.responses

import com.yasser.mazr3a_task.model.Category

data class CategoryResponse(
    val count: Int,
    val items: List<Category>,
    val limit: Int,
    val offset: Int,
    val total: Int
)