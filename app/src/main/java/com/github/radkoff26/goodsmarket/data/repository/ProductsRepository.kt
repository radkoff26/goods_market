package com.github.radkoff26.goodsmarket.data.repository

import com.github.radkoff26.goodsmarket.data.data_source.ProductsDataSource
import com.github.radkoff26.goodsmarket.data.model.Product
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ProductsRepository @Inject constructor(
    private val productsPagingSource: ProductsPagingSource,
    private val productsDataSource: ProductsDataSource,
) {

    fun productsPagingSource() = productsPagingSource

    fun getProductById(id: Long): Single<Product> =
        productsDataSource.getProductById(id)
            .subscribeOn(Schedulers.io())
}