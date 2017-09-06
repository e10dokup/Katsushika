package xyz.dokup.katsushika.cache

import android.graphics.Bitmap
import android.util.LruCache

/**
 * Created by e10dokup on 2017/09/06.
 */
class DefaultMemoryCache : BitmapCache {

    private val maxMemory = (Runtime.getRuntime().maxMemory() / 1024).toInt()
    private val cacheSize by lazy { maxMemory / 8 }
    private var memoryCache: LruCache<String, Bitmap>

    init {
        // Create memory cache
        memoryCache = object : LruCache<String, Bitmap>(cacheSize) {
            override fun sizeOf(key: String?, value: Bitmap?): Int {
                value?: return super.sizeOf(key, value)
                return value.rowBytes * value.height / 1024
            }
        }
    }

    override fun putBitmap(key: String, bitmap: Bitmap) {
        getBitmap(key) ?: return
        memoryCache.put(key, bitmap)
    }

    override fun getBitmap(key: String): Bitmap? = memoryCache.get(key)
}