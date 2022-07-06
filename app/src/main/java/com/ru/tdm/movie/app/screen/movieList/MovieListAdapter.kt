package com.ru.tdm.movie.app.screen.movieList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ru.tdm.movie.R
import com.ru.tdm.movie.databinding.ItemBinding
import com.ru.tdm.movie.network.model.Result
import kotlinx.android.synthetic.main.item.view.*

class MovieListAdapter(diffCallback: DiffUtil.ItemCallback<Result>
) : PagingDataAdapter<Result, MovieListAdapter.MyViewHolder>(diffCallback) {

    class MyViewHolder(binding: ItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemBinding.inflate(inflater, parent, false)
        return MyViewHolder(binding)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val movie = getItem(position) ?: return
        holder.itemView.tag = movie
        holder.itemView.apply {
            Glide
                .with(itemMovePoster.context)
                .load("https://image.tmdb.org/t/p/w500${movie.poster_path}")
                .centerCrop()
                .placeholder(R.drawable.avatar)
                .into(itemMovePoster)
            itemMoveTitle.text = movie.title
            itemMoveRelease.text = movie.release_date
            itemMoveOverview.text = movie.overview
        }
    }

    object UserComparator : DiffUtil.ItemCallback<Result>() {
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            // Id is unique.
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem == newItem
        }
    }
}