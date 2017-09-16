package xyz.dokup.katsushika_sample

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import xyz.dokup.katsushika.Katsushika

/**
 * Created by e10dokup on 2017/09/17.
 */
class ImageAdapter(
        private val context: Context,
        private val urls: List<String>
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val inflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(inflater.inflate(R.layout.item_image, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        holder ?: return
        val url = urls[position]
        val viewHolder = holder as ViewHolder
        Katsushika.with(context).load(url).into(viewHolder.itemImage)
    }

    override fun getItemCount(): Int {
        return urls.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val itemImage = itemView.findViewById<ImageView>(R.id.item_image)!!
    }
}