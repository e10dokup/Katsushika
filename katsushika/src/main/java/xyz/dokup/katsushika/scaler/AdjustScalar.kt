package xyz.dokup.katsushika.scaler

import android.graphics.Bitmap
import android.widget.ImageView
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.suspendCancellableCoroutine
import xyz.dokup.katsushika.ext.getBitmapOptions
import xyz.dokup.katsushika.ext.getScaledBitmap

/**
 * Created by e10dokup on 2017/09/18.
 */
class AdjustScalar constructor(
        private val target: ImageView
): BitmapScalar {

    override suspend fun scaleBitmap(byteArray: ByteArray): Bitmap {
        return suspendCancellableCoroutine { continuation ->
            launch(UI) {
                val options = async { byteArray.getBitmapOptions() }.await()
                val bitmap = async { byteArray.getScaledBitmap(target, options) }.await()
                continuation.resume(bitmap)
            }
        }
    }
}