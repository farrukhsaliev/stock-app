package uz.softler.stockapp.ui.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import uz.softler.stockapp.R
import uz.softler.stockapp.data.entities.Result
import uz.softler.stockapp.data.entities.StockItem
import uz.softler.stockapp.databinding.SearchItemListBinding

class SearchAdapter(var context: Context) : ListAdapter<Result, SearchAdapter.MyViewHolder>(SearchAdapter.MyDiffUtil()) {

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = SearchItemListBinding.bind(itemView)

        fun onBind(item: Result) {
            binding.ticker.text = item.symbol
            binding.company.text = item.description
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
                LayoutInflater.from(parent.context).inflate(
                        R.layout.search_item_list,
                        parent,
                        false
                )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.onBind(getItem(position))

        setAnimation(holder.itemView)
    }

    class MyDiffUtil : DiffUtil.ItemCallback<Result>() {
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.id == newItem.id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem == newItem
        }

        override fun getChangePayload(oldItem: Result, newItem: Result): Any {
            return false
        }
    }

    private fun setAnimation(viewToAnimate: View) {
        val animation: Animation =
                AnimationUtils.loadAnimation(context, R.anim.item_animation_fall_down)
        viewToAnimate.startAnimation(animation)
    }
}