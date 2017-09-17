package xyz.dokup.katsushika

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.ImageView
import xyz.dokup.katsushika.cache.BitmapCache
import xyz.dokup.katsushika.ext.md5


/**
 * Created by e10dokup on 2017/08/23.
 */
class Katsushika private constructor(private val context: Context) {

    private var url: String? = null
    private var cache: BitmapCache? = null

    companion object {
        fun with(context: Context): Katsushika {
            return Katsushika(context)
        }
    }

    fun cache(cache: BitmapCache): Katsushika {
        this.cache = cache
        return this
    }

    fun load(url: String): Katsushika {
        this.url = url
        return this
    }

    fun into(target: ImageView) {
        url ?: return

        val fetcher = BitmapFetcher()
        fetcher.fetch(url!!, cache,
            onFetchFromCache = {
                target.setImageBitmap(it)
            },
            onFetchFromUrl = {
                val options = getBitmapOptions(it)
                val bitmap = getScaledBitmap(target, options, it)
                target.setImageBitmap(bitmap)
                cache?.putBitmap(url!!.md5(), bitmap)
            }
        )

    }

    private fun getBitmapOptions(byteArray: ByteArray): BitmapFactory.Options {
        val imageOptions = BitmapFactory.Options()
        imageOptions.inJustDecodeBounds = true
        BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size, imageOptions)
        return imageOptions
    }

    private fun getScaledBitmap(target: ImageView, options: BitmapFactory.Options, byteArray: ByteArray): Bitmap {
        val widthScale = options.outWidth/target.width
        val bitmap : Bitmap
        if (widthScale > 2) {
            val imageOptions = BitmapFactory.Options()

            var i = 2
            while (i <= widthScale) {
                imageOptions.inSampleSize = i
                i *= 2
            }

            bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size, imageOptions)
        } else {
            bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
        }
        return bitmap
    }

}