package xyz.dokup.katsushika.ext

import android.support.test.runner.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by e10dokup on 2017/12/31.
 */
@RunWith(AndroidJUnit4::class)
class StringExtTest{

    @Test
    @Throws(Exception::class)
    fun checkSimpleStringToMd5() {
        val before = "Hello, World."
        assertEquals(before.md5(), "e9db5cf8349b1166e96a742e198a0dd1")
    }

    @Test
    @Throws(Exception::class)
    fun checkUrlStringToMd5() {
        val before = "http://example.com"
        assertEquals(before.md5(), "a9b9f04336ce0181a08e774e01113b31")
    }

}