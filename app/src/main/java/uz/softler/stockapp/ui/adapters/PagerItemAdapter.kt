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
import com.bumptech.glide.load.engine.DiskCacheStrategy
import uz.softler.stockapp.R
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

        fun onBind(stock: StockItem, position: Int, oldPosition: Int) {
            binding.also {
                it.company.text = stock.shortName
                it.ticker.text = stock.symbol
                it.price.text = "$${stock.regularMarketPrice.toString()}"
                if (stock.regularMarketChange.toString()[0] == '-') {
                    it.change.setTextColor(Color.parseColor("#B22424"))
                } else {
                    it.change.setTextColor(Color.parseColor("#24B25D"))
                }

                if (stock.regularMarketChange.toString().length > 4) {
                    it.change.text = "${stock.regularMarketChange.toString().substring(0, stock.regularMarketChange.toString().indexOf('.')+3)} (${stock.regularMarketChangePercent.toString().substring(0, stock.regularMarketChangePercent.toString().indexOf( '.')+2)}%)"
                } else {
                    it.change.text = "${stock.regularMarketChange.toString()} (${stock.regularMarketChangePercent.toString().substring(0, stock.regularMarketChangePercent.toString().indexOf( '.')+2)}%)"
                }

                Glide
                        .with(itemView.context)
                        .load(stock.logo)
                        .diskCacheStrategy(DiskCacheStrategy.DATA)
                        .centerCrop()
                        .placeholder(R.drawable.placeholder)
                        .into(it.logo)


                if (!stock.isLiked) {
                    it.star.setImageResource(R.drawable.ic_unliked)
                } else {
                    it.star.setImageResource(R.drawable.ic_liked)
                }

                if (position % 2 == 1) {
                    it.background.visibility = View.INVISIBLE
                } else {
                    it.background.visibility = View.VISIBLE
                }

                binding.star.setOnClickListener {
//                    onClickItem.onClickStar(stock)
                    onClickItem.onClickStar(stock, position, oldPosition)
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
        holder.onBind(getItem(position), position, holder.oldPosition)
    }

    class MyDiffUtil : DiffUtil.ItemCallback<StockItem>() {
        override fun areItemsTheSame(oldItem: StockItem, newItem: StockItem): Boolean {
            return oldItem.id == newItem.id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: StockItem, newItem: StockItem): Boolean {
            return oldItem == newItem
        }

        override fun getChangePayload(oldItem: StockItem, newItem: StockItem): Any {
            return false
        }
    }

    interface Clickable {
        fun onClickItem(stock: StockItem)
        fun onClickStar(stock: StockItem, position: Int, count: Int)
    }
}