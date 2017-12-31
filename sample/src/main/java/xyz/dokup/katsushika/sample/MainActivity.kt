package xyz.dokup.katsushika.sample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import xyz.dokup.katsushika.Katsushika
import xyz.dokup.katsushika.cache.DefaultCombinedCache
import xyz.dokup.katsushika.scaler.AdjustScalar

class MainActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imageView = findViewById(R.id.image_view)

        loadImage()
    }

    private fun loadImage() {
        Katsushika.with(this)
                .load("http://placehold.jp/1024x768.png")
                .errorRes(R.drawable.ic_error_black_24dp)
                .cache(DefaultCombinedCache(this))
                .scale(AdjustScalar(imageView))
                .into(imageView)
    }
}
