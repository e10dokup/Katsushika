package xyz.dokup.katsushika.transformer

import android.graphics.Bitmap

/**
 * Created by e10dokup on 2017/12/30.
 */
interface BitmapTransformer {

    suspend fun transform(bitmap: Bitmap): Bitmap

}