package com.github.radkoff26.goodsmarket.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.github.radkoff26.goodsmarket.R
import com.github.radkoff26.goodsmarket.data.model.Product
import com.github.radkoff26.goodsmarket.databinding.FragmentProductItemBinding
import com.github.radkoff26.goodsmarket.ui.adapter.ProductImagesPagerAdapter
import com.github.radkoff26.goodsmarket.ui.base.BaseFragment
import com.github.radkoff26.goodsmarket.ui.view_model.ProductItemViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductItemFragment :
    BaseFragment<FragmentProductItemBinding>(R.layout.fragment_product_item) {

    private val viewModel: ProductItemViewModel by viewModels()

    private val args: ProductItemFragmentArgs by navArgs()

    private val adapter: ProductImagesPagerAdapter = ProductImagesPagerAdapter()

    private var onPageChangeCallback: OnPageChangeCallback? = null

    private var lastSelectedImage: Int = 0

    override fun onCreateBinding(view: View): FragmentProductItemBinding =
        FragmentProductItemBinding.bind(view)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.productLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is ProductItemViewModel.UiState.Loading -> binding.showLoader()
                is ProductItemViewModel.UiState.Loaded -> binding.showData(it.product)
                is ProductItemViewModel.UiState.Failed -> processFailure(it.throwable)
            }
        }
        viewModel.loadProduct(args.productId)
        binding.initUI()
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        savedInstanceState ?: return
        lastSelectedImage = savedInstanceState.getInt(LAST_PAGE_COUNT, 0)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(LAST_PAGE_COUNT, binding.productLayout.productImagesPager.currentItem)
    }

    private fun processFailure(throwable: Throwable) {
        Log.e(TAG, "showToastFailure: failed to load product", throwable)
        Toast.makeText(requireContext(), R.string.products_failed, Toast.LENGTH_SHORT).show()
    }

    private fun FragmentProductItemBinding.initUI() {
        productLayout.productImagesPager.adapter = adapter
        onPageChangeCallback = object : OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                productLayout.imagesPageCount.text =
                    getString(R.string.pager_count, position + 1, adapter.itemCount)
            }
        }
        productLayout.productImagesPager.registerOnPageChangeCallback(onPageChangeCallback!!)
        toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun FragmentProductItemBinding.showData(product: Product) {
        productLayout.root.visibility = View.VISIBLE
        loaderLayout.root.visibility = View.GONE
        with(productLayout) {
            productDescription.text = product.description
            imagesPageCount.text =
                getString(
                    R.string.pager_count,
                    productImagesPager.currentItem + 1,
                    adapter.itemCount
                )
            productDiscount.text = "-${product.discountPercentage.toInt()}%"
            productPrice.text = getString(R.string.price, product.price)
            productTitle.text = product.title
        }
        adapter.setNewData(product.images)
        binding.productLayout.productImagesPager.setCurrentItem(lastSelectedImage, false)
    }

    private fun FragmentProductItemBinding.showLoader() {
        productLayout.root.visibility = View.GONE
        loaderLayout.root.visibility = View.VISIBLE
    }

    companion object {
        private const val LAST_PAGE_COUNT = "PageCount"
        const val TAG = "ProductItemFragment_TAG"
    }
}