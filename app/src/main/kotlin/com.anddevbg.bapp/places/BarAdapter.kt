package com.anddevbg.bapp.places

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import butterknife.bindView
import com.anddevbg.bapp.R
import com.anddevbg.bapp.domain.place.Place

/**
 * Created by teodorpenkov on 5/23/16.
 */
class BarAdapter : RecyclerView.Adapter<BarAdapter.BarViewHolder>() {

    class BarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView by bindView(R.id.barName)
        //        val vicinity: TextView by bindView(R.id.vicinity)
        val distanceTo: TextView by bindView(R.id.distanceTo)
    }

    private var mItems: List<Place> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BarViewHolder {
        val context = parent.context
        val itemView = LayoutInflater.from(context).inflate(R.layout.view_bar_item, parent, false)

        val viewHolder = BarViewHolder(itemView)

        return viewHolder
    }

    override fun onBindViewHolder(holder: BarViewHolder, position: Int) {
        val place = mItems[position]
        holder.name.text = place.name
//        holder.vicinity.text = place.vicinity
        holder.distanceTo.text = "${place.distanceTo} meters"
    }

    override fun getItemCount(): Int {
        return mItems.count()
    }

    fun updateItems(items: List<Place>) {
        mItems = items;
        notifyItemRangeChanged(0, mItems.count() - 1)
    }

    fun itemAt(position: Int): Place = mItems[position]
}