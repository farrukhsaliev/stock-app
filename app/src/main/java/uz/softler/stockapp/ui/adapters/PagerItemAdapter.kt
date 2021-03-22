package uz.softler.stockapp.ui.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uz.softler.stockapp.R
import uz.softler.stockapp.data.entities.ActiveStock
import uz.softler.stockapp.data.entities.Stock
import uz.softler.stockapp.data.entities.StockItem
import uz.softler.stockapp.databinding.PagerItemListBinding

//class PagerItemAdapter(var onClickItem: Clickable, var context: Context): RecyclerView.Adapter<PagerItemAdapter.MyViewHolder>() {
//    private var oldData = emptyList<Stock>()
//
//    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val binding = PagerItemListBinding.bind(itemView)
//
//        fun onBind(stock: Stock, position: Int) {
//            binding.also {
//                it.company.text = stock.name
//                it.ticker.text = stock.ticker
//                it.price.text = stock.shareOutstanding.toString()
//
//                Glide
//                        .with(itemView.context)
//                        .load(stock.logo)
//                        .centerCrop()
//                        .placeholder(R.drawable.ic_launcher_foreground)
//                        .into(it.logo)
//
//                if (position % 2 == 1) {
//                    it.constraintLayout.visibility = View.INVISIBLE
//                } else {
//                    it.constraintLayout.visibility = View.VISIBLE
//                }
//            }
//        }
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
//        return MyViewHolder(
//                LayoutInflater.from(parent.context).inflate(
//                        R.layout.pager_item_list,
//                        parent,
//                        false
//                )
//        )
//    }
//
//    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
//        holder.onBind(oldData[position], position)
//    }
//
//    override fun getItemCount(): Int {
//        return oldData.size
//    }
//
//    fun setData(newData: List<Stock>){
//        oldData = newData
//        notifyDataSetChanged()
//    }
//
//    interface Clickable {
//        fun onClickItem(stock: Stock)
//        fun onClickStar(stock: Stock)
//    }
//
//}

class PagerItemAdapter(var onClickItem: Clickable, var context: Context) : ListAdapter<StockItem, PagerItemAdapter.MyViewHolder>(MyDiffUtil()) {

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = PagerItemListBinding.bind(itemView)

        fun onBind(stock: StockItem, position: Int) {
            binding.also {
                it.company.text = stock.displayName
                it.ticker.text = stock.symbol
                it.price.text = stock.postMarketPrice.toString()
                it.change.text = stock.fiftyTwoWeekHighChange.toString()

//                Glide
//                        .with(itemView.context)
//                        .load(stock.logo)
//                        .centerCrop()
//                        .placeholder(R.drawable.yndx)
//                        .into(it.logo)

//                if (!stock) {
//                    it.star.setImageResource(R.drawable.ic_unliked)
//                } else {
//                    it.star.setImageResource(R.drawable.ic_liked)
//                }

                if (position % 2 == 1) {
                    it.background.visibility = View.INVISIBLE
                } else {
                    it.background.visibility = View.VISIBLE
                }




                binding.star.setOnClickListener {
                    onClickItem.onClickStar(stock)
                }

                binding.item.setOnClickListener {
                    onClickItem.onClickItem(stock)
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

    class MyDiffUtil : DiffUtil.ItemCallback<StockItem>() {
        override fun areItemsTheSame(oldItem: StockItem, newItem: StockItem): Boolean {
            return oldItem.id == newItem.id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: StockItem, newItem: StockItem): Boolean {
            return oldItem == newItem
        }
    }

    interface Clickable {
        fun onClickItem(stock: StockItem)
        fun onClickStar(stock: StockItem)
    }
}