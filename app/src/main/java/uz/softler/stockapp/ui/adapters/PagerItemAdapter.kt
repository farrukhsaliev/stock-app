package uz.softler.stockapp.ui.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uz.softler.stockapp.R
import uz.softler.stockapp.data.entities.Stock
import uz.softler.stockapp.databinding.PagerItemListBinding

class PagerItemAdapter : ListAdapter<Stock, PagerItemAdapter.MyViewHolder>(MyDiffUtil()) {

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = PagerItemListBinding.bind(itemView)

        fun onBind(stock: Stock, position: Int) {
            binding.also {
                it.company.text = stock.name
                it.ticker.text = stock.ticker
                it.price.text = stock.shareOutstanding.toString()

                Glide
                        .with(itemView.context)
                        .load(stock.logo)
                        .centerCrop()
                        .placeholder(R.drawable.ic_launcher_foreground)
                        .into(it.logo)

                if (position % 2 == 1) {
                    it.constraintLayout.visibility = View.INVISIBLE
                } else {
                    it.constraintLayout.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
                LayoutInflater.from(parent.context).inflate(
                        R.layout.pager_item_list,
                        parent,
                        false
                )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.onBind(getItem(position), position)
    }

    class MyDiffUtil : DiffUtil.ItemCallback<Stock>() {
        override fun areItemsTheSame(oldItem: Stock, newItem: Stock): Boolean {
            return oldItem.id == newItem.id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Stock, newItem: Stock): Boolean {
            return oldItem == newItem
        }
    }

    interface Clickable {
        fun onClickItem(stock: Stock)
        fun onClickStar(stock: Stock)
    }
}