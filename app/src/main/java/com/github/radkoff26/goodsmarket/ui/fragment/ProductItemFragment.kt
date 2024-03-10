package com.github.radkoff26.goodsmarket.ui.fragment

import android.view.View
import com.github.radkoff26.goodsmarket.R
import com.github.radkoff26.goodsmarket.databinding.FragmentProductItemBinding
import com.github.radkoff26.goodsmarket.ui.base.BaseFragment

class ProductItemFragment :
    BaseFragment<FragmentProductItemBinding>(R.layout.fragment_product_item) {

    override fun onCreateBinding(view: View): FragmentProductItemBinding =
        FragmentProductItemBinding.bind(view)

    companion object {
        @JvmStatic
        fun newInstance() = ProductItemFragment()
    }
}