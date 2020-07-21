package com.test.your_movie.ui.movie_info

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.TextAppearanceSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import com.test.your_movie.R
import com.test.your_movie.app.AppData.MOVIE_BIG_IMAGE_URL
import com.test.your_movie.databinding.FragmentMovieInfoBinding
import com.test.your_movie.model.MovieModel

class MovieInfoFragment : Fragment() {

    private lateinit var binding: FragmentMovieInfoBinding
    private lateinit var movie: MovieModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentMovieInfoBinding.inflate(inflater, container, false)
        movie = MovieInfoFragmentArgs.fromBundle(requireArguments()).movie
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (!movie.voteAverage.isNullOrEmpty()) {
            val vote = java.lang.Double.valueOf(movie.voteAverage!!)
            binding.movieInfoProgressCircular.progress = java.lang.Double.valueOf(vote * 10).toInt()
            binding.movieInfoProgressNumber.text = movie.voteAverage
        } else {
            binding.movieInfoProgressNumber.text = "NO"
        }

        val strDate = resources.getText(R.string.release_date).toString() + " " + movie.releaseDate
        val strPopularity = resources.getText(R.string.popularity).toString() + " " + movie.popularity

        Picasso.with(requireContext()).load(MOVIE_BIG_IMAGE_URL + movie.posterPath)
                .error(R.drawable.empty)
                .placeholder(R.drawable.empty)
                .into(binding.movieInfoMoviePhoto)

        binding.movieInfoTitle.text = movie.movieTitle
        binding.movieInfoOriginalTitle.text = movie.movieOriginalTitle
        binding.movieInfoDescription.text = movie.overview

        val spanDate = SpannableString(strDate)
        spanDate.setSpan(TextAppearanceSpan(requireContext(), R.style.MediumText), 0, 12, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        spanDate.setSpan(TextAppearanceSpan(requireContext(), R.style.LightText), 13, strDate.length - 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        binding.movieInfoDate.setText(spanDate, TextView.BufferType.SPANNABLE)

        val spanPopularity = SpannableString(strPopularity)
        spanPopularity.setSpan(TextAppearanceSpan(requireContext(), R.style.MediumText), 0, 10, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        spanPopularity.setSpan(TextAppearanceSpan(requireContext(), R.style.LightText), 11, strPopularity.length - 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        binding.movieInfoPopularity.setText(spanPopularity, TextView.BufferType.SPANNABLE)
    }
}
