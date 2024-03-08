package com.github.radkoff26.goodsmarket.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.github.radkoff26.goodsmarket.R
import com.github.radkoff26.goodsmarket.databinding.FragmentProductsListBinding
import com.github.radkoff26.goodsmarket.ui.adapter.ProductsListAdapter
import com.github.radkoff26.goodsmarket.ui.base.BaseFragment
import com.github.radkoff26.goodsmarket.ui.view_model.ProductsListViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy

@AndroidEntryPoint
class ProductsListFragment :
    BaseFragment<FragmentProductsListBinding>(R.layout.fragment_products_list) {

    private val viewModel: ProductsListViewModel by viewModels()

    private val compositeDisposable = CompositeDisposable()

    override fun onCreateBinding(view: View): FragmentProductsListBinding =
        FragmentProductsListBinding.bind(view)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = ProductsListAdapter()
        binding.initUI(adapter)
        viewModel.isDataLoadedLiveData.observe(viewLifecycleOwner) {
            if (it) {
                binding.showList()
            } else {
                binding.showLoader()
            }
        }
        adapter.addOnPagesUpdatedListener {
            viewModel.markDataAsLoaded()
        }
        compositeDisposable.add(
            viewModel.productsFlowable.subscribeBy {
                adapter.submitData(lifecycle, it)
            }
        )
    }

    override fun onDestroyView() {
        compositeDisposable.dispose()
        super.onDestroyView()
    }

    private fun FragmentProductsListBinding.initUI(adapter: ProductsListAdapter) {
        productsRecyclerView.adapter = adapter
        toolbar.title = getString(R.string.products_title)
    }

    private fun FragmentProductsListBinding.showList() {
        loaderContainer.visibility = View.GONE
        productsRecyclerView.visibility = View.VISIBLE
    }

    private fun FragmentProductsListBinding.showLoader() {
        loaderContainer.visibility = View.VISIBLE
        productsRecyclerView.visibility = View.GONE
    }

    companion object {

        @JvmStatic
        fun newInstance() = ProductsListFragment()
    }
}