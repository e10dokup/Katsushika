package xyz.dokup.katsushika.scaler

import android.graphics.Bitmap
import android.widget.ImageView
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.suspendCancellableCoroutine
import xyz.dokup.katsushika.ext.getBitmapOptions
import xyz.dokup.katsushika.ext.getScaledBitmap

/**
 * Created by e10dokup on 2017/09/18.
 */
class AdjustScalar : BitmapScalar {

    private val targetWidth: Int

    constructor(target: ImageView) {
        targetWidth = target.width
    }
    constructor(targetWidth: Int) {
        this.targetWidth = targetWidth
    }

    override suspend fun scaleBitmap(byteArray: ByteArray): Bitmap {
        return suspendCancellableCoroutine { continuation ->
            launch(UI) {
                val options = async { byteArray.getBitmapOptions() }.await()
                val bitmap = async { byteArray.getScaledBitmap(targetWidth, options) }.await()
                continuation.resume(bitmap)
            }
        }
    }
}