package com.test.your_movie.ui.movie_list.list

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.test.your_movie.R
import com.test.your_movie.model.MovieModel

class MovieHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var image: ImageView = itemView.findViewById(R.id.item_movie_photo)
    var title: TextView = itemView.findViewById(R.id.item_movie_title)
    var date: TextView = itemView.findViewById(R.id.item_movie_date)
    var description: TextView = itemView.findViewById(R.id.item_movie_description)

    var infoButton: TextView = itemView.findViewById(R.id.item_movie_more_info)
    var scheduleButton: TextView = itemView.findViewById(R.id.item_movie_schedule_viewing)

    fun bind(item: MovieModel, listener: MovieAdapter.OnItemClickListener, id: Int, button: TextView) {
        button.setOnClickListener { listener.onItemClick(item, id, this) }
    }
}
