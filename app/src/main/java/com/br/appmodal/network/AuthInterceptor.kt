package com.br.appmodal.network

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var req = chain.request()
        req = req.newBuilder().addHeader("Authorization", "token 36ccded42c578e6ab2bc281a8eb660d6f046efd7").build()
        return chain.proceed(req)
    }
}