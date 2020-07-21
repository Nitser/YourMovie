package com.test.your_movie.ui.movie_list.list

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.test.your_movie.model.MovieModel
import com.test.your_movie.R
import com.test.your_movie.app.AppData
import java.util.ArrayList

class MovieAdapter(private val infoListener: OnItemClickListener, private val scheduleListener: OnItemClickListener) : RecyclerView.Adapter<MovieHolder>() {
    var movies: ArrayList<MovieModel> = ArrayList()
        set(value) {
            movies.clear()
            movies.addAll(value)
            notifyDataSetChanged()
        }

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MovieHolder {
        val itemView = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_movie, viewGroup, false)
        return MovieHolder(itemView)
    }

    override fun onBindViewHolder(holder: MovieHolder, i: Int) {
        val movie = movies[i]

        holder.title.text = movie.movieTitle
        holder.date.text = movie.releaseDate
        holder.description.text = movie.overview

        Picasso.with(holder.itemView.context).load(AppData.MOVIE_IMAGE_URL + movie.posterPath)
                .error(R.drawable.empty)
                .placeholder(R.drawable.empty)
                .into(holder.image)
        holder.image.scaleType = ImageView.ScaleType.CENTER_CROP

        holder.bind(movie, infoListener, i, holder.infoButton)
        holder.bind(movie, scheduleListener, i, holder.scheduleButton)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    interface OnItemClickListener {
        fun onItemClick(item: MovieModel, id: Int, boardHolder: MovieHolder)
    }

}
