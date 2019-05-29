package com.alphan.mainactivity.api

import com.alphan.mainactivity.utils.Constants.BASE_URL_PLACES
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

fun getRetrofitInstancePlaces(): API {

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
            .baseUrl(BASE_URL_PLACES)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

    return retrofit.create(API::class.java)
}

interface API {

    @Headers("Content-Type: application/json")
    @GET("json")
    fun getNearbyPlaces(
            @Query("language") language: String,
            @Query("location") location: String,
            @Query("radius") radius: String,
            @Query("type") type: String,
            @Query("key") key: String
    ): Single<NearbyPlacesResponse>

    @Headers("Content-Type: application/json")
    @GET("json")
    fun getNearbyPlacesByKeyword(
            @Query("language") language: String,
            @Query("location") location: String,
            @Query("radius") radius: String,
            @Query("keyword") keyword: String,
            @Query("key") key: String
    ): Single<NearbyPlacesResponse>
}