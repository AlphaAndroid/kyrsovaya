package com.alphan.mainactivity.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

fun getRetrofitInstance(): API {

    val interceptorBodyLevel = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    val interceptorHeadersLevel = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS)

    val client = OkHttpClient.Builder()
        .addInterceptor(interceptorBodyLevel)
        .addInterceptor(interceptorHeadersLevel)
        .connectTimeout(10000L, TimeUnit.MILLISECONDS)
        .readTimeout(10000L, TimeUnit.MILLISECONDS)
        .build()


    val retrofit = retrofit2.Retrofit.Builder()
        .client(client)
        .baseUrl("BASE URL")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()

    return retrofit.create(API::class.java)
}

interface API {
}