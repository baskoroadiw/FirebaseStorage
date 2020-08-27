package com.baskoroadi.firebasestorage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.rv_list.view.*

class RvAdapter(private val list:ArrayList<Items>) : RecyclerView.Adapter<RvAdapter.MyViewHolder>() {

    var listener: RecyclerViewClickListener? = null

    class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.rv_list,parent,false)
        return MyViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.view.tv_filename.text = list[position].nameFile
        holder.view.setOnClickListener {
            listener?.onItemClicked(it,list[position])
        }
        holder.view.setOnLongClickListener {
            listener?.onLongItemClicked(it,list[position])
            true
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}