package ru.zfix27r.movies.ui.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import ru.zfix27r.movies.databinding.FragmentCategoryItemBinding
import ru.zfix27r.movies.databinding.FragmentMainTopItemBinding
import ru.zfix27r.movies.domain.model.BaseResModel
import ru.zfix27r.movies.ui.common.NavigateToFilmDetailCallback

class CategoryAdapter(
    private val navFilm: NavigateToFilmDetailCallback
) : PagingDataAdapter<BaseResModel, CategoryMainViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryMainViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FragmentCategoryItemBinding.inflate(inflater, parent, false)
        return CategoryMainViewHolder(binding, navFilm)
    }

    override fun onBindViewHolder(holder: CategoryMainViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    class DiffCallback : DiffUtil.ItemCallback<BaseResModel>() {
        override fun areItemsTheSame(old: BaseResModel, new: BaseResModel): Boolean = old.id == new.id
        override fun areContentsTheSame(old: BaseResModel, new: BaseResModel): Boolean = old == new
    }
}