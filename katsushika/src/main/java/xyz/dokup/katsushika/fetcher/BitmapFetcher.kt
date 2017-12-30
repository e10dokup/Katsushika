package xyz.dokup.katsushika.fetcher

import android.graphics.Bitmap
import xyz.dokup.katsushika.cache.BitmapCache
import xyz.dokup.katsushika.scaler.BitmapScalar

/**
 * Created by e10dokup on 2017/12/30.
 */
interface BitmapFetcher {

    suspend fun fetch(url: String, cache: BitmapCache?, scalar: BitmapScalar): Bitmap
}