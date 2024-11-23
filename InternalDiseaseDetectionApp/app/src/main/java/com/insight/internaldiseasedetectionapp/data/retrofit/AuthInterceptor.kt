package com.insight.internaldiseasedetectionapp.data.retrofit

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class AuthInterceptor(private var token: String): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var req: Request = chain.request()
        req = if (req.header("No-Authentication") == null && token.isNotEmpty()) {
            val neededToken = "Bearer $token"
            req.newBuilder()
                .addHeader("Authorization", neededToken)
                .build()
        } else {
            req.newBuilder()
                .build()
        }
        return chain.proceed(req)
    }
}