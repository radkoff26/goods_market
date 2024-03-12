package com.github.radkoff26.goodsmarket.data.data_source

import com.github.radkoff26.goodsmarket.data.dto.ProductsResponse
import com.github.radkoff26.goodsmarket.data.model.Product
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductsDataSource {

    @GET("/products?select=title,description,price,discountPercentage,thumbnail")
    fun getProducts(
        @Query("skip") skip: Int,
        @Query("limit") limit: Int
    ): Single<ProductsResponse>

    @GET("/products/{id}?select=title,description,price,discountPercentage,thumbnail,images")
    fun getProductById(
        @Path("id") id: Long
    ): Single<Product>
}