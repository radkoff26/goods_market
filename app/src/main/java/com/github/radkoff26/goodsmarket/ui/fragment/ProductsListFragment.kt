package com.github.radkoff26.goodsmarket.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
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
        binding.bindAdapter(adapter)
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

    private fun FragmentProductsListBinding.bindAdapter(
        productsListAdapter: ProductsListAdapter
    ) {
        productsRecyclerView.adapter = productsListAdapter
        productsRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
    }

    companion object {

        @JvmStatic
        fun newInstance() = ProductsListFragment()
    }
}