package xyz.dokup.katsushika_sample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var imageRecycler: RecyclerView

    private val imageAdapter by lazy { ImageAdapter(this, urls) }

    private val urls = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imageRecycler = findViewById(R.id.gallery_recycler)
        imageRecycler.layoutManager = GridLayoutManager(this, 3)
        imageRecycler.adapter = imageAdapter

        createImageList()
    }

    private fun createImageList() {
        for (i in 1 .. 100) {
            val width = Random().nextInt(1200) + 1
            val height = Random().nextInt(1200) + 1
            val url = "http://placehold.jp/%dx%d.png".format(width, height)
            urls.add(url)
        }

        imageAdapter.notifyDataSetChanged()
    }
}
