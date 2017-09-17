package xyz.dokup.katsushika.cache

import android.content.Context
import android.graphics.Bitmap

/**
 * Created by e10dokup on 2017/09/07.
 */
class DefaultCombinedCache (
        context: Context
): BitmapCache {

    private val memoryCache = DefaultMemoryCache()
    private val diskCache = DefaultDiskCache(context)

    override fun putBitmap(key: String, bitmap: Bitmap) {
        memoryCache.putBitmap(key, bitmap)
        diskCache.putBitmap(key, bitmap)
    }

    override fun getBitmap(key: String): Bitmap? {
        val bitmap = memoryCache.getBitmap(key)
        bitmap ?: return diskCache.getBitmap(key)
        return bitmap
    }
}