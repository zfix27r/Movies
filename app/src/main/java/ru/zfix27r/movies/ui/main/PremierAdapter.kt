package ru.zfix27r.movies.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import ru.zfix27r.movies.R
import ru.zfix27r.movies.databinding.FragmentMainPremierItemBinding
import ru.zfix27r.movies.domain.model.PremiereResModel

class PremierAdapter(
    private val navFilmListener: NavFilmListener
) : ListAdapter<PremiereResModel, PremierAdapterMainViewHolder>(DiffCallback()),
    View.OnClickListener {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PremierAdapterMainViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FragmentMainPremierItemBinding.inflate(inflater, parent, false)

        binding.item.setOnClickListener(this)

        return PremierAdapterMainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PremierAdapterMainViewHolder, position: Int) {
        getItem(position)?.let {
            with(holder.binding) {
                item.tag = it

                //Glide.with(holder.binding.movieItem.context).load(it.posterUrlPreview).into(preview)

                this.film = it
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<PremiereResModel>() {
        override fun areItemsTheSame(old: PremiereResModel, new: PremiereResModel) =
            old.id == new.id

        override fun areContentsTheSame(old: PremiereResModel, new: PremiereResModel) =
            old == new
    }

    override fun onClick(v: View) {
        val item = v.tag as PremiereResModel
        when (v.id) {
            R.id.item -> navFilmListener.navigate(item.id)
        }
    }
}