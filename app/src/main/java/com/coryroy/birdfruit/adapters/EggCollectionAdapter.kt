package com.coryroy.birdfruit.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.coryroy.birdfruit.R
import com.coryroy.birdfruit.viewmodels.EggCollectionViewModel

class EggCollectionAdapter(private val viewModel: EggCollectionViewModel, lifecycleOwner: LifecycleOwner) : RecyclerView.Adapter<EggCollectionAdapter.EggCollectionViewHolder>() {

    init {
        viewModel.eggCollectionList.observe(lifecycleOwner, Observer {
            notifyDataSetChanged()
        })
    }

    class EggCollectionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dayOfWeek: TextView = itemView.findViewById(R.id.day_of_week)
        val eggsCollected: TextView = itemView.findViewById(R.id.eggs_collected)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EggCollectionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.egg_collection_item, parent, false)
        return EggCollectionViewHolder(view)
    }

    override fun onBindViewHolder(holder: EggCollectionViewHolder, position: Int) {
        val currentItem = viewModel.eggCollectionList.value!![position]
        holder.dayOfWeek.text = currentItem.dayOfWeek
        holder.eggsCollected.text = currentItem.eggsCollected.toString()
    }

    override fun getItemCount() = viewModel.eggCollectionList.value?.size ?: 0
}