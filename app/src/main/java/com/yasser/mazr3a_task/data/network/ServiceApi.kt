package com.yasser.mazr3a_task.data.network

import com.google.gson.JsonObject
import com.yasser.mazr3a_task.data.network.responses.CategoryResponse
import com.yasser.mazr3a_task.data.network.responses.ProductResponse
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface ServiceApi {
    companion object {
        const val StoreID = "13995032"
        const val publicToken = "public_eVEGR9tWeKfbbjsc4DpNe9Cx5JcBBGk6"
        const val secretToken = "secret_CfbRUHJDKu32W2Lfk16dKPzdQe57QkqF"
        operator fun invoke(connectionStatus: ConnectionStatus): ServiceApi {


            val clientbuilder = OkHttpClient.Builder()
                .addInterceptor(connectionStatus)
                .build()

            return Retrofit.Builder()
                .client(clientbuilder)

                .baseUrl("https://app.ecwid.com/api/v3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ServiceApi::class.java)

        }
    }

    @GET("${StoreID}/categories?token=${publicToken}")
    suspend fun GetCategories(): Response<CategoryResponse>

    @GET("${StoreID}/products?limit=5&token=${secretToken}")
    suspend fun GetCategoryLimitProducts(
        @Query("category") category: String,
        @Query("offset") offset: String
    ): Response<ProductResponse>

    @GET("${StoreID}/products?token=${secretToken}")
    suspend fun GetProductsByCategoryID(
        @Query("category") category: String,
        @Query("offset") offset: String
    ): Response<ProductResponse>

    @GET("${StoreID}/products?token=${secretToken}")
    suspend fun GetProductsByCategoryIDSorted(
        @Query("category") category: String,
        @Query("sortBy") sortBy: String,
        @Query("offset") offset: String
    ): Response<ProductResponse>


    @POST("${StoreID}/orders?token=${secretToken}")
    suspend fun MakeOrder(@Body data: JsonObject):Response<Any>
}