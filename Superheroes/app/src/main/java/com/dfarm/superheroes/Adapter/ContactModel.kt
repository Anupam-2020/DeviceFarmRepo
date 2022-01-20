package com.dfarm.superheroes.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.dfarm.superheroes.R

class ContactModel {
    lateinit var name: String
    lateinit var number: String
}

class MainAdapter(
    private val context: Context,
    private val arrayList: ArrayList<ContactModel>
): RecyclerView.Adapter<ConViewHolder>() {

    private val _clkPos = MutableLiveData<Int>()
    val clkPos: LiveData<Int> = _clkPos

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConViewHolder {
        val infVw = LayoutInflater.from(context).inflate(
            R.layout.recycler_item,
            parent,
            false
        )
        return ConViewHolder(infVw)
    }


    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: ConViewHolder, position: Int) {
        holder.tvName().text = arrayList[position].name
        holder.tvNumber().text = arrayList[position].number
        holder.rootView().setOnClickListener {
            _clkPos.value = position
        }
    }
}


class ConViewHolder(
    private val infVw: View
): RecyclerView.ViewHolder(infVw) {
    fun rootView() = infVw
    fun tvName() = infVw.findViewById<TextView>(R.id.tv_name)
    fun tvNumber() = infVw.findViewById<TextView>(R.id.tv_number)
}