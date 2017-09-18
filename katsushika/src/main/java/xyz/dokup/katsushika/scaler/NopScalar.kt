package xyz.dokup.katsushika.scaler

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.suspendCancellableCoroutine

/**
 * Created by e10dokup on 2017/09/18.
 */
class NopScalar : BitmapScalar {

    override suspend fun scaleBitmap(byteArray: ByteArray): Bitmap {
        return suspendCancellableCoroutine { continuation ->
            launch(UI) {
                continuation.resume(BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size))
            }
        }
    }
}