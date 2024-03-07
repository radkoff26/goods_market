package com.github.radkoff26.goodsmarket

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.radkoff26.goodsmarket.ui.fragment.ProductsListFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction().apply {
            add(R.id.container, ProductsListFragment.newInstance())
            commit()
        }
    }
}