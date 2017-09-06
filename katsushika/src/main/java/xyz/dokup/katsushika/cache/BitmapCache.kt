package xyz.dokup.katsushika.cache

import android.graphics.Bitmap

/**
 * Created by e10dokup on 2017/09/07.
 */
interface BitmapCache {
    fun putBitmap(key: String, bitmap: Bitmap)
    fun getBitmap(key: String): Bitmap?
}