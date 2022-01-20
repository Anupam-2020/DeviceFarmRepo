package com.dfarm.superheroes.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.dfarm.superheroes.R
import com.dfarm.superheroes.Sqlite.Entities.ImageDetails

data class RecyclerItemOperation(
    val operation: String,
    val id: Long,
    val position: Int
)

class RecViewHolder(
    private val infVw: View
): RecyclerView.ViewHolder(infVw) {

    fun rootView() = infVw

    fun name() = infVw.findViewById<TextView>(R.id.rectext)

    fun image() = infVw.findViewById<ImageView>(R.id.imageHospt)
}


class HomeAdapter(
    private val context: Context,
    private val hospitals: List<ImageDetails>,
) : RecyclerView.Adapter<RecViewHolder>() {

    private val _clkPos = MutableLiveData<Int>()
    val clkPos: LiveData<Int> = _clkPos

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecViewHolder {
        val infVw = LayoutInflater.from(context).inflate(
            R.layout.rec_item,
            parent,
            false
        )

        return RecViewHolder(infVw)
    }

    override fun onBindViewHolder(holder: RecViewHolder, position: Int) {

        holder.name().text = hospitals[position].name

        loadWithGlide(holder.image(),hospitals[position].image)

        holder.rootView().setOnClickListener {
            _clkPos.value = position

        }
    }

    override fun getItemCount() = hospitals.size

    private fun loadWithGlide(img : ImageView, url: String) {

        Glide.with(context)
            .load(url)
            .transition(
                DrawableTransitionOptions.withCrossFade(150)
            )
            .into(img)
    }
}






class OfflineAdapter(
    private val context: Context,
    private val dataSource: List<ImageDetails>
): RecyclerView.Adapter<RecViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecViewHolder {
        val infVw = LayoutInflater.from(context).inflate(
            R.layout.rec_item,
            parent,
            false
        )

        return RecViewHolder(infVw)
    }

    override fun onBindViewHolder(holder: RecViewHolder, position: Int) {
//        holder.name().text = dataSource[position].operation

    }

    override fun getItemCount(): Int {
        return dataSource.size
    }

}