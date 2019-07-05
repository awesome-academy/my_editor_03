package com.sun_asterisk.myeditor03.data.source.remote.api

import com.sun_asterisk.myeditor03.BuildConfig
import okhttp3.Interceptor
import okhttp3.Interceptor.Chain
import okhttp3.Response

internal class RequestInterceptor : Interceptor {
    override fun intercept(chain: Chain): Response {
        return chain.proceed(chain.request().let {
            it.newBuilder().url(
                it.url.newBuilder()
                    .addQueryParameter("client_id", BuildConfig.API_KEY)
                    .build()
            ).build()
        })
    }
}
