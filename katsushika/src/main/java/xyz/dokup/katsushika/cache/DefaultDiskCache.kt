package xyz.dokup.katsushika.cache

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment
import xyz.dokup.katsushika.ext.md5
import xyz.dokup.katsushika.util.DiskLruCache
import java.io.*

/**
 * Created by e10dokup on 2017/09/06.
 */
class DefaultDiskCache(
        private val context: Context
): BitmapCache {

    companion object {
        private const val MAX_DISK = (50 * 1024 * 1024).toLong()
        private const val BUFFER_SIZE = 8 * 1024
        private const val DISK_CACHE_SUB_DIR = "cache"
    }

    private var diskCache: DiskLruCache

    init {
        // create cache dir
        val cacheDir = getCacheDir(DISK_CACHE_SUB_DIR)
        diskCache = DiskLruCache.open(cacheDir, 1, 1, MAX_DISK)
    }

    override fun putBitmap(key: String, bitmap: Bitmap) {
        getBitmap(key) ?: return
        val editor = diskCache.edit(key.md5())
        editor ?: return
        if (writeBitmapToFile(bitmap, editor)) {
            diskCache.flush()
            editor.commit()
        } else {
            editor.abort()
        }
    }

    override fun getBitmap(key: String): Bitmap? {
        val snapshot = diskCache.get(key.md5())
        snapshot ?: return null
        val inputStream = snapshot.getInputStream(0)

        snapshot.use {
            val bufferedInputStream = BufferedInputStream(inputStream, BUFFER_SIZE)
            return BitmapFactory.decodeStream(bufferedInputStream)
        }
    }

    private fun getCacheDir(dirName: String) : File {
        val cachePath = if (isAbleToUseExternalDir()) {
            context.externalCacheDir.path
        } else {
            context.cacheDir.path
        }
        return File(cachePath + File.separator + dirName)
    }

    private fun isAbleToUseExternalDir() : Boolean {
        return (Environment.getExternalStorageDirectory().equals(Environment.MEDIA_MOUNTED) || !Environment.isExternalStorageRemovable())
    }

    @Throws(IOException::class, FileNotFoundException::class)
    private fun writeBitmapToFile(bitmap: Bitmap, editor: DiskLruCache.Editor): Boolean {
        val out = BufferedOutputStream(editor.newOutputStream(0), BUFFER_SIZE)
        out.use {
            return bitmap.compress(Bitmap.CompressFormat.PNG, 80, out)
        }
    }
}