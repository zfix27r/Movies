package ru.zfix27r.movies.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import ru.zfix27r.movies.databinding.FragmentMainTopItemBinding
import ru.zfix27r.movies.domain.model.BaseResModel
import ru.zfix27r.movies.ui.common.NavigateToFilmDetailCallback

class TopAdapter(
    private val navFilm: NavigateToFilmDetailCallback
) : ListAdapter<BaseResModel, TopMainViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopMainViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FragmentMainTopItemBinding.inflate(inflater, parent, false)
        return TopMainViewHolder(binding, navFilm)
    }

    override fun onBindViewHolder(holder: TopMainViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    class DiffCallback : DiffUtil.ItemCallback<BaseResModel>() {
        override fun areItemsTheSame(old: BaseResModel, new: BaseResModel): Boolean = old.id == new.id
        override fun areContentsTheSame(old: BaseResModel, new: BaseResModel): Boolean = old == new
    }
}