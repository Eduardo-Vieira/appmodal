package com.br.appmodal.network

import com.br.appmodal.BuildConfig
import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BuildConfig.API_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

fun provideOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient {
    return OkHttpClient().newBuilder()
        .addInterceptor(authInterceptor)
        .addNetworkInterceptor(StethoInterceptor()) // debug network
        .build()
}

fun provideGithubapi(retrofit: Retrofit): Githubapi = retrofit.create(Githubapi::class.java)

