package com.example.fitness_app.Adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.fitness_app.R
import com.example.fitness_app.databinding.DataSelectedListItemBinding
import com.example.fitness_app.utils.Objects.DataSelectorModel

class DataSelectorAdapter(private val listener:Listener) :  ListAdapter<DataSelectorModel, DataSelectorAdapter.Holder>(Comparator()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view  = LayoutInflater.from(parent.context).inflate(R.layout.data_selected_list_item,parent,false)
        return Holder(view,listener)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getItem(position))
    }
    class Holder(view: View,private val listener:Listener): RecyclerView.ViewHolder(view){
        private val binding =  DataSelectedListItemBinding.bind(view)

        fun bind(dataSelectorModel: DataSelectorModel){
            binding.item.text = dataSelectorModel.text
            if(dataSelectorModel.siSelected){
                binding.item.setTextColor(Color.WHITE)
                binding.item.setBackgroundResource(R.drawable.data_selected_bg)
            }
            else{
                binding.item.setTextColor(Color.GRAY)
                binding.item.setBackgroundResource(R.drawable.data_not_selected_bg)
            }
            binding.item.setOnClickListener{listener.onItemClick(adapterPosition)}

        }
    }




    class Comparator: DiffUtil.ItemCallback<DataSelectorModel>(){
        override fun areItemsTheSame(
            oldItem: DataSelectorModel,
            newItem: DataSelectorModel
        ): Boolean {
            return oldItem.text == newItem.text
        }

        override fun areContentsTheSame(
            oldItem: DataSelectorModel,
            newItem: DataSelectorModel
        ): Boolean {
            return oldItem == newItem
        }
    }



    interface Listener{
        fun onItemClick(index:Int)
    }
}