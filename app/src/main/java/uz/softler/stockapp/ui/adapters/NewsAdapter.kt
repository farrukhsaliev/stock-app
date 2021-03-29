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
import com.bumptech.glide.Glide
import uz.softler.stockapp.R
import uz.softler.stockapp.data.entities.News
import uz.softler.stockapp.databinding.NewsItemListBinding

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

        setAnimation(holder.itemView)
    }

    private fun setAnimation(viewToAnimate: View) {
        val animation: Animation =
                AnimationUtils.loadAnimation(context, R.anim.item_animation_fall_down)
        viewToAnimate.startAnimation(animation)
    }

}

