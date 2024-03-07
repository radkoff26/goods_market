package com.github.radkoff26.goodsmarket.data.repository

import javax.inject.Inject

class ProductsRepository @Inject constructor(
    private val productsPagingSource: ProductsPagingSource
) {

    fun productsPagingSource() = productsPagingSource
}