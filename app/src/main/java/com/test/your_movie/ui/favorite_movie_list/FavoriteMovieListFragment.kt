package com.test.your_movie.ui.favorite_movie_list

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.test.your_movie.R
import com.test.your_movie.databinding.FragmentMovieListBinding
import com.test.your_movie.model.MovieModel
import com.test.your_movie.ui.movie_list.list.MovieAdapter
import com.test.your_movie.ui.movie_list.list.MovieHolder
import com.test.your_movie.view_model.FavoriteMovieListViewModel
import java.util.Calendar

class FavoriteMovieListFragment : Fragment() {

    private lateinit var binding: FragmentMovieListBinding
    private lateinit var adapter: MovieAdapter
    private val favoriteMovieListViewModel: FavoriteMovieListViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        binding = FragmentMovieListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        favoriteMovieListViewModel.init(requireContext())
        binding.movieList.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(requireContext())
        binding.movieList.layoutManager = layoutManager

        adapter = MovieAdapter(object : MovieAdapter.OnItemClickListener {
            override fun onItemClick(item: MovieModel, id: Int, boardHolder: MovieHolder) {
                requireActivity().findNavController(R.id.nav_host_fragment)
                        .navigate(FavoriteMovieListFragmentDirections.actionFavoriteMovieListFragmentToMovieInfoFragment(item))
            }
        }, object : MovieAdapter.OnItemClickListener {
            override fun onItemClick(item: MovieModel, id: Int, boardHolder: MovieHolder) {
                val cal = Calendar.getInstance()
                val year = cal.get(Calendar.YEAR)
                val month = cal.get(Calendar.MONTH)
                val day = cal.get(Calendar.DAY_OF_MONTH)

                val dateSetListener = DatePickerDialog.OnDateSetListener { _, yearDate, monthDate, dayOfMonth ->
                    addCalendarEvent(item, yearDate, monthDate, dayOfMonth)
                }

                val dialog = DatePickerDialog(
                        requireContext(),
                        R.style.DialogCalendar,
                        dateSetListener,
                        year, month, day)
                dialog.show()
            }
        })
        binding.movieList.adapter = adapter

        favoriteMovieListViewModel.getFavoriteMovieList().observe(viewLifecycleOwner, Observer<ArrayList<MovieModel>> { item ->
            if (!item.isNullOrEmpty()) {
                adapter.movies.clear()
                adapter.movies.addAll(item)
                adapter.notifyDataSetChanged()
            }
        })
        if (favoriteMovieListViewModel.getFavoriteMovieList().value.isNullOrEmpty()) {
            favoriteMovieListViewModel.loadFavoriteMovieList()
        }
    }

    private fun addCalendarEvent(movie: MovieModel, year: Int, month: Int, day: Int) {
        val cal = Calendar.getInstance()
        cal.set(year, month, day)
        val intent = Intent(Intent.ACTION_EDIT)
        intent.type = "vnd.android.cursor.item/event"
        intent.putExtra("beginTime", cal.timeInMillis)
        intent.putExtra("allDay", true)
        intent.putExtra("title", "Lets watch the movie: " + movie.movieTitle)
        requireActivity().startActivity(intent)
    }

}
