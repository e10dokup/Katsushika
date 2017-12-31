package xyz.dokup.katsushika.fetcher

import android.graphics.Bitmap
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.suspendCancellableCoroutine
import okhttp3.OkHttpClient
import xyz.dokup.katsushika.api.ImageApi
import xyz.dokup.katsushika.cache.BitmapCache
import xyz.dokup.katsushika.ext.md5
import xyz.dokup.katsushika.ext.start
import xyz.dokup.katsushika.scaler.BitmapScalar

/**
 * Created by e10dokup on 2017/09/07.
 */
class OkHttpBitmapFetcher constructor (
        okHttpClient: OkHttpClient
): BitmapFetcher {

    private val imageApi = ImageApi(okHttpClient)

    override suspend fun fetch(url: String, cache: BitmapCache?, scalar: BitmapScalar): Bitmap {
        return suspendCancellableCoroutine { continuation ->
            val cached = cache?.getBitmap(url.md5())

            if (cached != null) {
                continuation.resume(cached)
            } else {
                launch(UI) {
                    try {
                        val byteArray = async {imageApi.getImage(url).start().bytes()}.await()
                        val bitmap = scalar.scaleBitmap(byteArray)
                        cache?.putBitmap(url, bitmap)
                        continuation.resume(bitmap)
                    } catch (e: Exception) {
                        continuation.resumeWithException(e)
                    }
                }
            }
        }
    }
}