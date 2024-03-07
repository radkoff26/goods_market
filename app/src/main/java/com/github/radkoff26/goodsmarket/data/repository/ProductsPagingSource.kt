package com.github.radkoff26.goodsmarket.data.repository

import androidx.paging.PagingState
import androidx.paging.rxjava2.RxPagingSource
import com.github.radkoff26.goodsmarket.data.data_source.ProductsDataSource
import com.github.radkoff26.goodsmarket.data.model.Product
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import kotlin.math.max

class ProductsPagingSource @Inject constructor(
    private val productsDataSource: ProductsDataSource
) : RxPagingSource<Int, Product>() {

    override fun getRefreshKey(state: PagingState<Int, Product>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val closestPage = state.closestPageToPosition(anchorPosition) ?: return null
        val closestItem = state.closestItemToPosition(anchorPosition) ?: return null
        val closestItemIndex = closestPage.data.indexOfFirst { it.id == closestItem.id }
        return closestItemIndex - state.config.pageSize / 2
    }

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, Product>> {
        val startIndex = params.key ?: START_KEY
        return productsDataSource.getProducts(startIndex, params.loadSize)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map<LoadResult<Int, Product>> {
                LoadResult.Page(
                    data = it.products,
                    prevKey = when (startIndex) {
                        START_KEY -> null
                        else -> validateKey(startIndex - params.loadSize)
                    },
                    nextKey = if (it.products.size < params.loadSize) null else startIndex + params.loadSize
                )
            }
            .onErrorReturn {
                LoadResult.Error(it)
            }
    }

    private fun validateKey(key: Int) = max(key, START_KEY)

    companion object {
        private const val START_KEY = 0
    }
}