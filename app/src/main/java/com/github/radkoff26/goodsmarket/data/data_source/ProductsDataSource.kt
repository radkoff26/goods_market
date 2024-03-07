package com.github.radkoff26.goodsmarket.data.data_source

import com.github.radkoff26.goodsmarket.data.dto.ProductsResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductsDataSource {

    @GET("/products")
    fun getProducts(
        @Query("skip") skip: Int,
        @Query("limit") limit: Int
    ): Single<ProductsResponse>
}