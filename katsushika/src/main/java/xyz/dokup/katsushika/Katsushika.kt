package xyz.dokup.katsushika

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.ImageView
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import xyz.dokup.katsushika.api.ImageApi
import xyz.dokup.katsushika.ext.await


/**
 * Created by e10dokup on 2017/08/23.
 */
class Katsushika private constructor(private val context: Context) {

    private var url: String? = null

    companion object {
        fun with(context: Context): Katsushika {
            return Katsushika(context)
        }
    }

    fun load(url: String): Katsushika {
        this.url = url
        return this
    }

    fun into(target: ImageView) {
        url ?: return

        val job = launch(UI) {
            val byteArray = async(CommonPool) { ImageApi().getImage(url).await().bytes() }.await()
            val options = async(CommonPool) { getBitmapOptions(byteArray) }.await()
            val bitmap = async(CommonPool) { getScaledBitmap(target, options, byteArray) }.await()
            target.setImageBitmap(bitmap)
        }
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