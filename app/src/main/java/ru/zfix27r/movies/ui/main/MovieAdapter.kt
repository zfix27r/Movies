package ru.zfix27r.movies.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import ru.zfix27r.movies.R
import ru.zfix27r.movies.databinding.FragmentMainTopItemBinding
import ru.zfix27r.movies.domain.model.TopResModel

class MovieAdapter(
    private val actionListener: MovieActionListener
) : PagingDataAdapter<TopResModel, MovieViewHolder>(DiffCallback()),
    View.OnClickListener {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FragmentMainTopItemBinding.inflate(inflater, parent, false)

        binding.item.setOnClickListener(this)

        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        getItem(position)?.let {
            with(holder.binding) {
                item.tag = it

                //Glide.with(holder.binding.movieItem.context).load(it.posterUrlPreview).into(preview)

                this.film = it
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<TopResModel>() {
        override fun areItemsTheSame(old: TopResModel, new: TopResModel): Boolean = old.id == new.id
        override fun areContentsTheSame(old: TopResModel, new: TopResModel): Boolean = old == new
    }

    override fun onClick(v: View) {
        val item = v.tag as TopResModel
        when (v.id) {
            R.id.item -> actionListener.onViewDetail(item)
        }
    }
}