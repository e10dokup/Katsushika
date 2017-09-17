package xyz.dokup.katsushika

import android.graphics.Bitmap
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import xyz.dokup.katsushika.api.ImageApi
import xyz.dokup.katsushika.cache.BitmapCache
import xyz.dokup.katsushika.ext.await
import xyz.dokup.katsushika.ext.md5

/**
 * Created by e10dokup on 2017/09/07.
 */
internal class BitmapFetcher {

    fun fetch(url: String, cache: BitmapCache?, onFetchFromCache: ((Bitmap) -> Unit), onFetchFromUrl: ((ByteArray) -> Unit)) {
        val cached = cache?.getBitmap(url.md5())

        if (cached != null) {
            onFetchFromCache(cached)
        } else {
            fetchFromUrl(url, onFetchFromUrl)
        }
    }

    private fun fetchFromUrl(url: String, onFetch: ((ByteArray) -> Unit)) {
        val job = launch(UI) {
            val byteArray = async(CommonPool) {ImageApi().getImage(url).await().bytes()}.await()
            onFetch(byteArray)
        }
    }
}