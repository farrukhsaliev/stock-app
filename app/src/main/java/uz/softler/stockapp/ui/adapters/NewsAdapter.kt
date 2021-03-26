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
import uz.softler.stockapp.data.entities.News
import uz.softler.stockapp.databinding.NewsItemListBinding
import java.text.SimpleDateFormat
import java.util.*

class NewsAdapter(var onClickItem: NewsAdapter.Clickable, var context: Context) : ListAdapter<News, NewsAdapter.MyViewHolder>(NewsAdapter.MyDiffUtil()) {

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = NewsItemListBinding.bind(itemView)

        fun onBind(newItem: News) {
            binding.also {
                it.title.text = newItem.headline
                it.description.text = newItem.summary

                Glide
                        .with(context)
                        .load(newItem.image)
                        .placeholder(R.drawable.placeholder)
                        .into(it.image)

                it.background.setOnClickListener {
                    onClickItem.onClickItem(newItem)
                }
            }
        }

        fun getDate(milliSeconds: Long, dateFormat: String?): String? {
//            val formatter = SimpleDateFormat(dateFormat)

//            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
//            String dateString = formatter.format(new Date(dateInMillis)));

            val myFormatter = SimpleDateFormat("dd/MM/yyyy")


            return myFormatter.format(Date(milliSeconds))
//            val calendar: Calendar = Calendar.getInstance()
//            calendar.timeInMillis = milliSeconds
//            return formatter.format(calendar.time)
        }
    }

    class MyDiffUtil : DiffUtil.ItemCallback<News>() {
        override fun areItemsTheSame(oldItem: News, newItem: News): Boolean {
            return oldItem.id == newItem.id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: News, newItem: News): Boolean {
            return oldItem == newItem
        }
    }

    interface Clickable {
        fun onClickItem(news: News)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
                LayoutInflater.from(parent.context).inflate(
                        R.layout.news_item_list,
                        parent,
                        false
                )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

}

