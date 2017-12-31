package xyz.dokup.katsushika.scaler

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.ByteArrayOutputStream

/**
 * Created by e10dokup on 2017/12/31.
 */
@RunWith(AndroidJUnit4::class)
class NopScalerTest {

    private lateinit var scaler: BitmapScalar
    private lateinit var byteArray: ByteArray
    private lateinit var bitmap: Bitmap

    @Before
    fun setUp() {
        scaler = NopScalar()

        val assetManager = InstrumentationRegistry.getTargetContext().assets
        bitmap = BitmapFactory.decodeStream(assetManager?.open("480x320.png"))
        val bos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos)
        bos.flush()
        byteArray = bos.toByteArray()
        bos.close()
    }

    @Test
    @Throws(Exception::class)
    fun checkDoNothing() {
        launch {
            val output = async { scaler.scaleBitmap(byteArray) }.await()
            assertEquals(480, output.width)
            assertEquals(320, output.height)
        }
    }

}