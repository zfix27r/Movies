package ru.zfix27r.movies.ui.category

import android.view.View
import androidx.databinding.ObservableInt
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import ru.zfix27r.movies.R
import ru.zfix27r.movies.databinding.FragmentCategoryItemBinding
import ru.zfix27r.movies.databinding.FragmentMainTopItemBinding
import ru.zfix27r.movies.domain.model.BaseResModel
import ru.zfix27r.movies.ui.common.NavigateToFilmDetailCallback

class CategoryMainViewHolder(
    private val binding: FragmentCategoryItemBinding,
    private val navFilm: NavigateToFilmDetailCallback
) :
    RecyclerView.ViewHolder(binding.root), View.OnClickListener {

//    private val requestOptions = RequestOptions().placeholder(R.drawable.no_poster)

    val error = ObservableInt(R.string.empty)

    init {
        binding.item.setOnClickListener(this)
    }

    fun bind(model: BaseResModel) {
        binding.apply {
            Glide.with(item.context)
                .load(model.posterUrlPreview)
//                .apply(requestOptions)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(preview)
            item.tag = model
            film = model
        }
    }

    override fun onClick(v: View) {
        val item = v.tag as BaseResModel
        when (v.id) {
            R.id.item -> navFilm.toFilmDetail(item.id)
        }
    }
}