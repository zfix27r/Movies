package ru.zfix27r.movies.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.zfix27r.movies.R
import ru.zfix27r.movies.databinding.FragmentMainItemBinding
import ru.zfix27r.movies.domain.model.TopResModel

class MovieAdapter(
    private val actionListener: MovieActionListener
) :
    ListAdapter<TopResModel, MovieAdapter.MovieViewHolder>(DiffCallback()),
    View.OnClickListener {

    override fun getItemCount() = currentList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FragmentMainItemBinding.inflate(inflater, parent, false)

        binding.movieItem.setOnClickListener(this)

        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val item = currentList[position]
        with(holder.binding) {
            movieItem.tag = item

            Glide.with(holder.binding.movieItem.context).load(item.posterUrlPreview).into(preview)

            this.film = item
        }
    }

    class MovieViewHolder(val binding: FragmentMainItemBinding) : RecyclerView.ViewHolder(binding.root)

    class DiffCallback : DiffUtil.ItemCallback<TopResModel>() {
        override fun areItemsTheSame(old: TopResModel, new: TopResModel): Boolean = old.id == new.id
        override fun areContentsTheSame(old: TopResModel, new: TopResModel): Boolean = old == new
    }

    override fun onClick(v: View) {
        val item = v.tag as TopResModel
        when (v.id) {
            R.id.movie_item -> actionListener.onViewDetail(item)
        }
    }
}
