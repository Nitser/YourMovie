package com.test.your_movie.ui.movie_info

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.TextAppearanceSpan
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.squareup.picasso.Picasso
import com.test.your_movie.R
import com.test.your_movie.app.AppData.MOVIE_BIG_IMAGE_URL
import com.test.your_movie.databinding.FragmentMovieInfoBinding
import com.test.your_movie.model.MovieModel
import com.test.your_movie.view_model.FavoriteMovieListViewModel
/**
 * Экран с подробной информацией о фильме*/
class MovieInfoFragment : Fragment() {

    private lateinit var binding: FragmentMovieInfoBinding
    private lateinit var movie: MovieModel
    private var isFavorite = false
    private val favoriteMovieListViewModel: FavoriteMovieListViewModel by activityViewModels()
    private lateinit var curMenu: Menu

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_movie_info, menu)
        curMenu = menu
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_movie_info_like -> {
                try {
                    isFavorite = !isFavorite
                    if (isFavorite) {
                        item.setIcon(R.drawable.ic_favorite_black_24dp)
                        movie.isFavorite = isFavorite
                        favoriteMovieListViewModel.saveFavoriteMovie(movie)
                    } else {
                        item.setIcon(R.drawable.ic_favorite_border_black_24dp)
                        movie.isFavorite = isFavorite
                        favoriteMovieListViewModel.deleteFavoriteMovie(movie)
                    }
                } catch (ex: Exception) {
                }

                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentMovieInfoBinding.inflate(inflater, container, false)
        movie = MovieInfoFragmentArgs.fromBundle(requireArguments()).movie
        isFavorite = movie.isFavorite
        favoriteMovieListViewModel.init(requireContext())
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

        favoriteMovieListViewModel.getIsFavoriteMovie().observe(viewLifecycleOwner, Observer<Boolean> { item ->
            if (item) {
                isFavorite = item
                curMenu[0].setIcon(R.drawable.ic_favorite_black_24dp)
                favoriteMovieListViewModel.throwOffIsFavoriteMovie()
            }

        })
        favoriteMovieListViewModel.checkMovieIsSaved(movie)
    }

}
