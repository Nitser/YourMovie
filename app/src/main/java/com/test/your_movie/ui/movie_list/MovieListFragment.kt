package com.test.your_movie.ui.movie_list

import android.accounts.NetworkErrorException
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.test.your_movie.Interfaces.EndlessRecyclerViewScrollListener
import com.test.your_movie.model.MovieModel
import com.test.your_movie.R
import com.test.your_movie.databinding.FragmentMovieListBinding
import com.test.your_movie.ui.movie_list.list.MovieAdapter
import com.test.your_movie.ui.movie_list.list.MovieHolder
import com.test.your_movie.view_model.MovieListViewModel
import java.util.Calendar

class MovieListFragment : Fragment() {

    private lateinit var binding: FragmentMovieListBinding
    private lateinit var adapter: MovieAdapter
    private val movieListViewModel: MovieListViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        binding = FragmentMovieListBinding.inflate(inflater, container, false)

        binding.movieList.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(requireContext())
        binding.movieList.layoutManager = layoutManager

        adapter = MovieAdapter(object : MovieAdapter.OnItemClickListener {
            override fun onItemClick(item: MovieModel, id: Int, boardHolder: MovieHolder) {
                requireActivity().findNavController(R.id.nav_host_fragment)
                        .navigate(MovieListFragmentDirections.actionMovieListFragmentToMovieInfoFragment())
            }
        }, object : MovieAdapter.OnItemClickListener {
            override fun onItemClick(item: MovieModel, id: Int, boardHolder: MovieHolder) {
                val cal = Calendar.getInstance()
                val year = cal.get(Calendar.YEAR)
                val month = cal.get(Calendar.MONTH)
                val day = cal.get(Calendar.DAY_OF_MONTH)

                val dateSetListener = DatePickerDialog.OnDateSetListener { _, yearDate, monthDate, dayOfMonth ->
                    //save like notification
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

        val scrollListener = object : EndlessRecyclerViewScrollListener(layoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                binding.load.visibility = View.VISIBLE
                loadMovieList(page + 1)
            }
        }
        binding.movieList.addOnScrollListener(scrollListener)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        movieListViewModel.getBoardList().observe(viewLifecycleOwner, Observer<ArrayList<MovieModel>> { item ->
            adapter.movies = item
            adapter.notifyDataSetChanged()
        })

        loadMovieList(1)
    }

    private fun loadMovieList(page: Int) {
        movieListViewModel.loadMovieList(2020, page, object : MovieListViewModel.MovieListCallback {
            override fun onSuccess() {
                binding.load.visibility = View.INVISIBLE
            }

            override fun onError(networkError: NetworkErrorException) {
                Toast.makeText(requireContext(), getText(R.string.fragment_movie_list_no_internet), Toast.LENGTH_SHORT).show()
            }
        })
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
