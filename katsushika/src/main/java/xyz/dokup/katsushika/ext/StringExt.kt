package xyz.dokup.katsushika.ext

import java.math.BigInteger
import java.security.MessageDigest

/**
 * Created by e10dokup on 2017/09/06.
 */
fun String.md5(): String {
    val m = MessageDigest.getInstance("MD5")
    m.update(toByteArray(charset("UTF-8")))
    val digest = m.digest()
    val bigInt = BigInteger(1, digest)
    return bigInt.toString(16)
}