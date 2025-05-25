package com.mahyaddin.my_app.presentation.home.banner

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mahyaddin.my_app.R

class BannerPagerAdapter(private val items: List<EventData>) :
    RecyclerView.Adapter<BannerPagerAdapter.BannerPagerViewHolder>() {

    data class EventData(val title: String, val imageRes: Int)

    inner class BannerPagerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.imageEventBanner)
        val title: TextView = itemView.findViewById(R.id.textPagerItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerPagerViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_pager, parent, false)
        return BannerPagerViewHolder(view)
    }

    override fun onBindViewHolder(holder: BannerPagerViewHolder, position: Int) {
        val item = items[position]
        holder.title.text = item.title
        holder.image.setImageResource(item.imageRes)
    }

    override fun getItemCount(): Int = items.size
}
