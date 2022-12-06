package ru.zfix27r.movies.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.zfix27r.movies.R
import ru.zfix27r.movies.data.FilmTopResponse
import ru.zfix27r.movies.databinding.FragmentMainItemBinding


class MovieAdapter(
    private val actionListener: MovieActionListener
) :
    ListAdapter<FilmTopResponse.Film, MovieAdapter.MovieViewHolder>(DiffCallback()),
    View.OnClickListener {

    override fun getItemCount() = currentList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FragmentMainItemBinding.inflate(inflater, parent, false)

        //binding.title.setOnClickListener(this)

        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val item = currentList[position]
        with(holder.binding) {
            titleRu.tag = item

            Glide.with(holder.binding.movieItem.context).load(item.posterUrlPreview).into(preview)

            this.film = item
        }
    }

    class MovieViewHolder(val binding: FragmentMainItemBinding) : RecyclerView.ViewHolder(binding.root)

    class DiffCallback : DiffUtil.ItemCallback<FilmTopResponse.Film>() {
        override fun areItemsTheSame(old: FilmTopResponse.Film, new: FilmTopResponse.Film): Boolean = old.id == new.id
        override fun areContentsTheSame(old: FilmTopResponse.Film, new: FilmTopResponse.Film): Boolean = old == new
    }

    override fun onClick(v: View) {
        val item = v.tag as FilmTopResponse.Film
        when (v.id) {
            R.id.titleRu -> actionListener.onViewDetail(item)
        }
    }
}
