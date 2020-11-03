package com.yasser.mazr3a_task.repository

import com.yasser.mazr3a_task.data.network.SafeApiRequest
import com.yasser.mazr3a_task.data.network.ServiceApi
import com.yasser.mazr3a_task.model.Category

class CategoryRepository(private val serviceApi: ServiceApi): SafeApiRequest() {

suspend fun GetCategoreis():List<Category>{

    val response = apiRequest { serviceApi.GetCategories() }

    return response.items
}
}