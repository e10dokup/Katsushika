package xyz.dokup.katsushika.ext

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.widget.ImageView

/**
 * Created by e10dokup on 2017/09/18.
 */
fun ByteArray.getBitmapOptions(): BitmapFactory.Options {
    val imageOptions = BitmapFactory.Options()
    imageOptions.inJustDecodeBounds = true
    BitmapFactory.decodeByteArray(this, 0, this.size, imageOptions)
    return imageOptions
}

fun ByteArray.getScaledBitmap(target: ImageView, options: BitmapFactory.Options): Bitmap {
    Log.d("hoge", target.width.toString())
    val widthScale = options.outWidth/target.width
    val bitmap : Bitmap
    if (widthScale > 2) {
        val imageOptions = BitmapFactory.Options()

        var i = 2
        while (i <= widthScale) {
            imageOptions.inSampleSize = i
            i *= 2
        }

        bitmap = BitmapFactory.decodeByteArray(this, 0, this.size, imageOptions)
    } else {
        bitmap = BitmapFactory.decodeByteArray(this, 0, this.size)
    }
    return bitmap
}