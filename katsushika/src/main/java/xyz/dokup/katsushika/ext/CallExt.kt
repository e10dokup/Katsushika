package xyz.dokup.katsushika.ext

import kotlinx.coroutines.experimental.suspendCancellableCoroutine
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import okhttp3.ResponseBody
import java.io.IOException

/**
 * Created by e10dokup on 2017/09/06.
 */
suspend fun Call.start(): ResponseBody {
    return suspendCancellableCoroutine { continuation ->
        enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                val responseBody = response.body()
                if (responseBody == null) {
                    continuation.resumeWithException(Exception("ResponseBody is null")) } else {
                    continuation.resume(responseBody) }
            }
            override fun onFailure(call: Call, e: IOException) { if (continuation.isCancelled) return
                continuation.resumeWithException(e) }
        }) }
}