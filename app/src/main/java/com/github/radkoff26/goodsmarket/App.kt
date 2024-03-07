package com.github.radkoff26.goodsmarket

import android.app.Application
import com.github.radkoff26.goodsmarket.data.data_source.ProductsDataSource
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.create

class App: Application() {
    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl("https://dummyjson.com")
            .build()
    }
    val dataSource: ProductsDataSource by lazy {
        retrofit.create()
    }
}