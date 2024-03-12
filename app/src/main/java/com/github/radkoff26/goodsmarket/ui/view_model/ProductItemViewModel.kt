package com.github.radkoff26.goodsmarket.ui.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.radkoff26.goodsmarket.data.model.Product
import com.github.radkoff26.goodsmarket.data.repository.ProductsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

@HiltViewModel
class ProductItemViewModel @Inject constructor(
    private val productsRepository: ProductsRepository
) : ViewModel() {
    private val mutableProductLiveData: MutableLiveData<UiState> = MutableLiveData(UiState.Loading)
    val productLiveData: LiveData<UiState> = mutableProductLiveData

    private val compositeDisposable = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    fun loadProduct(id: Long) {
        val currentUiState = productLiveData.value!!
        if (currentUiState is UiState.Loaded) {
            if (currentUiState.product.id == id) {
                // Product has already been loaded
                return
            }
        }
        compositeDisposable.add(
            productsRepository.getProductById(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        mutableProductLiveData.value = UiState.Loaded(it)
                    },
                    {
                        mutableProductLiveData.value = UiState.Failed(it)
                    }
                )
        )
    }

    sealed class UiState {

        object Loading : UiState()

        data class Loaded(val product: Product) : UiState()

        data class Failed(val throwable: Throwable) : UiState()
    }
}