package xyz.dokup.katsushika.ext

import android.content.res.AssetManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.ByteArrayOutputStream

/**
 * Created by e10dokup on 2017/12/31.
 */
@RunWith(AndroidJUnit4::class)
class ByteArrayExtTest {

    private var assetManager: AssetManager? = null
    private lateinit var byteArray: ByteArray

    @Before
    fun setUp() {
        assetManager = InstrumentationRegistry.getTargetContext().assets
        val bitmap = BitmapFactory.decodeStream(assetManager?.open("480x320.png"))
        val bos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos)
        bos.flush()
        byteArray = bos.toByteArray()
        bos.close()
    }

    @Test
    @Throws(Exception::class)
    fun checkGetBitmapOptions() {
        val bitmapOptions = byteArray.getBitmapOptions()
        assertEquals(bitmapOptions.outWidth, 480)
        assertEquals(bitmapOptions.outHeight, 320)
    }

    @Test
    @Throws(Exception::class)
    fun checkGetScaledBitmapWhenLargeEnoughView() {
        val output = byteArray.getScaledBitmap(640, byteArray.getBitmapOptions())
        assertEquals(output.width, 480)
        assertEquals(output.height, 320)
    }

    @Test
    @Throws(Exception::class)
    fun checkGetScaledBitmapWhenSmallView() {
        val output = byteArray.getScaledBitmap(120, byteArray.getBitmapOptions())
        assertEquals(output.width, 120)
        assertEquals(output.height, 80)
    }

}