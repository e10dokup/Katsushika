package xyz.dokup.katsushika

import android.content.Context
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
                val bitmap = BitmapFactory.decodeByteArray(it, 0, it.size)
                target.setImageBitmap(bitmap)
                cache?.putBitmap(url!!.md5(), bitmap)
            }
        )

    }
}