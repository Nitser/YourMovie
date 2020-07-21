package com.test.your_movie.ui.movie_list.list

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.test.your_movie.model.MovieModel
import com.test.your_movie.R

class MovieHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var image = itemView.findViewById<ImageView>(R.id.item_movie_photo)
    var title = itemView.findViewById<TextView>(R.id.item_movie_title)
    var date = itemView.findViewById<TextView>(R.id.item_movie_date)
    var description = itemView.findViewById<TextView>(R.id.item_movie_description)

    var infoButton = itemView.findViewById<TextView>(R.id.item_movie_more_info)
    var scheduleButton = itemView.findViewById<TextView>(R.id.item_movie_schedule_viewing)

    fun bind(item: MovieModel, listener: MovieAdapter.OnItemClickListener, id: Int, button: TextView) {
        button.setOnClickListener { listener.onItemClick(item, id, this) }
    }
}
