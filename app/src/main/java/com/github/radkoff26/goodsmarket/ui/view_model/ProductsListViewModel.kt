package com.github.radkoff26.goodsmarket.ui.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava2.cachedIn
import androidx.paging.rxjava2.flowable
import com.github.radkoff26.goodsmarket.core.Config
import com.github.radkoff26.goodsmarket.data.model.Product
import com.github.radkoff26.goodsmarket.data.repository.ProductsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class ProductsListViewModel @Inject constructor(
    private val productsRepository: ProductsRepository
) : ViewModel() {
    val productsFlowable: Flowable<PagingData<Product>> = Pager(
        config = PagingConfig(
            Config.PRODUCTS_LIST_PAGE_SIZE,
            initialLoadSize = Config.PRODUCTS_LIST_PAGE_SIZE
        ),
        pagingSourceFactory = { productsRepository.productsPagingSource() }
    ).flowable.cachedIn(viewModelScope).observeOn(AndroidSchedulers.mainThread())
}