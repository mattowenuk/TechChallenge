package com.example.techchallenge.adapter

import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DataListAdapter(private val dataItems: MutableList<String>) : RecyclerView.Adapter<DataListAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(TextView(parent.context))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = dataItems[position]
    }

    override fun getItemCount() = dataItems.size

    class ViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView)
}

