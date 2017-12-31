package xyz.dokup.katsushika.api

import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Request

/**
 * Created by e10dokup on 2017/08/27.
 */
class ImageApi constructor(
        private val client: OkHttpClient = OkHttpClient()
) {

    fun getImage(url: String): Call {
        val request = Request.Builder()
                .url(url)
                .build()

        return client.newCall(request)
    }


}