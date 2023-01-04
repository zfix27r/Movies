package ru.zfix27r.movies.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import ru.zfix27r.movies.R
import ru.zfix27r.movies.databinding.FragmentMainTopItemBinding
import ru.zfix27r.movies.domain.model.TopResModel

class TopAdapter(
    private val navFilmListener: NavFilmListener
) : ListAdapter<TopResModel, TopAdapterMainViewHolder>(DiffCallback()),
    View.OnClickListener {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopAdapterMainViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FragmentMainTopItemBinding.inflate(inflater, parent, false)

        binding.item.setOnClickListener(this)

        return TopAdapterMainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TopAdapterMainViewHolder, position: Int) {
        getItem(position)?.let {
            with(holder.binding) {
                item.tag = it

                //Glide.with(holder.binding.movieItem.context).load(it.posterUrlPreview).into(preview)

                this.film = it
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<TopResModel>() {
        override fun areItemsTheSame(old: TopResModel, new: TopResModel) = old.id == new.id
        override fun areContentsTheSame(old: TopResModel, new: TopResModel) = old == new
    }

    override fun onClick(v: View) {
        val item = v.tag as TopResModel
        when (v.id) {
            R.id.item -> navFilmListener.navigate(item.id)
        }
    }
}