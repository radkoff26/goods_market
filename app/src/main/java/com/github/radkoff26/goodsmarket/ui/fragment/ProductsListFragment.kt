package com.github.radkoff26.goodsmarket.ui.fragment

import android.view.View
import com.github.radkoff26.goodsmarket.R
import com.github.radkoff26.goodsmarket.databinding.FragmentProductsListBinding
import com.github.radkoff26.goodsmarket.ui.base.BaseFragment

class ProductsListFragment :
    BaseFragment<FragmentProductsListBinding>(R.layout.fragment_products_list) {

    override fun onCreateBinding(view: View): FragmentProductsListBinding =
        FragmentProductsListBinding.bind(view)

    companion object {

        @JvmStatic
        fun newInstance() = ProductsListFragment()
    }
}