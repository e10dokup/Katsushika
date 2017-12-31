package xyz.dokup.katsushika.cache

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.ByteArrayOutputStream

/**
 * Created by e10dokup on 2017/12/31.
 */
@RunWith(AndroidJUnit4::class)
class DefaultMemoryCacheTest {

    companion object {
        @JvmStatic
        private val CACHE_KEY = "sample_key"
    }

    private lateinit var byteArray: ByteArray
    private lateinit var before: Bitmap
    private lateinit var cache: BitmapCache

    @Before
    fun setUp() {
        val assetManager = InstrumentationRegistry.getTargetContext().assets
        before = BitmapFactory.decodeStream(assetManager?.open("480x320.png"))
        val bos = ByteArrayOutputStream()
        before.compress(Bitmap.CompressFormat.PNG, 100, bos)
        bos.flush()
        byteArray = bos.toByteArray()
        bos.close()
        cache = DefaultMemoryCache()
    }

    @Test
    @Throws(Exception::class)
    fun checkNotFoundFromCache() {
        var bitmap = cache.getBitmap(CACHE_KEY)
        assertNull(bitmap)
    }

    @Test
    @Throws(Exception::class)
    fun checkFoundFromCache() {
        cache.putBitmap(CACHE_KEY, before)

        val bitmap = cache.getBitmap(CACHE_KEY)
        assertEquals(before.width, bitmap?.width)
        assertEquals(before.height, bitmap?.height)
    }

}