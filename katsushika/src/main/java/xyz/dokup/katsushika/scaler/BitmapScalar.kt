package xyz.dokup.katsushika.scaler

import android.graphics.Bitmap

/**
 * Created by e10dokup on 2017/09/18.
 */
interface BitmapScalar {

    suspend fun scaleBitmap(byteArray: ByteArray): Bitmap
}