package com.example.techchallenge.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.techchallenge.R
import com.example.techchallenge.model.Item
import com.example.techchallenge.model.ModelResult
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_model.view.*
import com.squareup.picasso.Picasso

class DataListAdapter(private val modelItems: ModelResult,
                      private val itemClick: (Item) -> Unit) : RecyclerView.Adapter<DataListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_model, parent, false)
        return ViewHolder(view, itemClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindModelResult(modelItems.items[position])
    }

    override fun getItemCount() = modelItems.items.size

    //class accepts the listener lambda
    class ViewHolder(override val containerView: View, private val itemClick: (Item) -> Unit) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        //binds the item data to the ui elements
        fun bindModelResult(item: Item) {
            with(item) {
                containerView.titleTextView.text = title
                containerView.timeTextView.text = lastUpdatedText
                containerView.smallImageView.contentDescription = image.altText
                //sets the listener to the view
                itemView.setOnClickListener { itemClick(this) }
                //picasso used for the image loading/caching
                Picasso.with(itemView.context).load(image.small).into(containerView.smallImageView)
            }
        }
    }
}
