package com.github.radkoff26.goodsmarket.core.di

import com.github.radkoff26.goodsmarket.core.Config
import com.github.radkoff26.goodsmarket.data.data_source.ProductsDataSource
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.create

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(Json.asConverterFactory(Config.RESPONSE_MIME_TYPE.toMediaType()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(Config.BASE_URL)
            .build()

    @Provides
    fun provideProductsDataSource(retrofit: Retrofit): ProductsDataSource = retrofit.create()
}