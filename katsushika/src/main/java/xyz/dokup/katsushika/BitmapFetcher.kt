package xyz.dokup.katsushika

import android.graphics.Bitmap
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.suspendCancellableCoroutine
import xyz.dokup.katsushika.api.ImageApi
import xyz.dokup.katsushika.cache.BitmapCache
import xyz.dokup.katsushika.ext.await
import xyz.dokup.katsushika.ext.md5
import xyz.dokup.katsushika.scaler.BitmapScalar

/**
 * Created by e10dokup on 2017/09/07.
 */
internal class BitmapFetcher {

    suspend fun fetch(url: String, cache: BitmapCache?, scalar: BitmapScalar): Bitmap {
        return suspendCancellableCoroutine { continuation ->
            val cached = cache?.getBitmap(url.md5())

            if (cached != null) {
                continuation.resume(cached)
            } else {
                val job = launch(UI) {
                    val byteArray = async(CommonPool) {ImageApi().getImage(url).await().bytes()}.await()
                    continuation.resume(scalar.scaleBitmap(byteArray))
                }
            }
        }
    }
}