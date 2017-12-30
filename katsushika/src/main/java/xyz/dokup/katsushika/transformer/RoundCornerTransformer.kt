package xyz.dokup.katsushika.transformer

import android.graphics.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.suspendCancellableCoroutine

/**
 * Created by e10dokup on 2017/12/30.
 */
class RoundCornerTransformer constructor(
        private val radius: Float = 20f
) : BitmapTransformer {

    override suspend fun transform(bitmap: Bitmap): Bitmap {
        return suspendCancellableCoroutine { continuation ->
            launch(UI) {
                val width = bitmap.width
                val height = bitmap.height

                val rect = Rect(0, 0, width, height)
                val rectF = RectF(0f, 0f, width.toFloat(), height.toFloat())

                val output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
                val canvas = Canvas(output)

                val paint = Paint()
                paint.isAntiAlias = true

                canvas.drawRoundRect(rectF, radius, radius, paint)
                paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
                canvas.drawBitmap(bitmap, rect, rectF, paint)

                continuation.resume(output)
            }
        }
    }

}