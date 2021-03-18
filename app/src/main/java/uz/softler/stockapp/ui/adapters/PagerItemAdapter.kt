package uz.softler.stockapp.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import uz.softler.stockapp.R
import uz.softler.stockapp.databinding.PagerItemListBinding
import uz.softler.stockapp.db.models.Stock

class PagerItemAdapter(var onClickItem: Clickable, var context: Context): RecyclerView.Adapter<PagerItemAdapter.MyViewHolder>() {
    private var oldData = emptyList<Stock>()
    private var lastPosition = -1

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = PagerItemListBinding.bind(itemView)

        fun onBind(stock: Stock, position: Int) {
            binding.also {
                it.company.text = stock.companyName
                it.ticker.text = stock.tiker
                it.change.text = stock.changes
                it.logo.setImageResource(stock.logo)
                it.price.text = stock.price

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
        holder.onBind(oldData[position], position)
    }

    override fun getItemCount(): Int {
        return oldData.size
    }

    fun setData(newData: List<Stock>){
        oldData = newData
        notifyDataSetChanged()
    }

    interface Clickable {
        fun onClickItem(stock: Stock)
        fun onClickStar(stock: Stock)
    }

}