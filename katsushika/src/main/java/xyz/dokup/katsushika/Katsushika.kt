package xyz.dokup.katsushika

import android.content.Context
import android.widget.ImageView
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import xyz.dokup.katsushika.cache.BitmapCache
import xyz.dokup.katsushika.fetcher.BitmapFetcher
import xyz.dokup.katsushika.fetcher.OkHttpBitmapFetcher
import xyz.dokup.katsushika.scaler.BitmapScalar
import xyz.dokup.katsushika.scaler.NopScalar
import xyz.dokup.katsushika.transformer.BitmapTransformer


/**
 * Created by e10dokup on 2017/08/23.
 */
class Katsushika private constructor(private val context: Context) {

    private var url: String? = null
    private var cache: BitmapCache? = null
    private var fetcher: BitmapFetcher = OkHttpBitmapFetcher()
    private var scalar: BitmapScalar = NopScalar()

    private val transformers = ArrayList<BitmapTransformer>()

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

    fun scale(scalar: BitmapScalar): Katsushika {
        this.scalar = scalar
        return this
    }

    fun transform(transformer: BitmapTransformer): Katsushika {
        this.transformers.add(transformer)
        return this
    }

    fun into(target: ImageView) {
        url ?: return

        launch(UI) {
            var bitmap = async { fetcher.fetch(url!!, cache, scalar) }.await()

            for(transformer in transformers) {
                bitmap = async { transformer.transform(bitmap) }.await()
            }

            target.setImageBitmap(bitmap)
        }

    }
}