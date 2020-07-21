package com.test.your_movie.ui.movie_list

import android.accounts.NetworkErrorException
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
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
import com.test.your_movie.custom.EndlessRecyclerViewScrollListener
import com.test.your_movie.R
import com.test.your_movie.app.HomeActivity
import com.test.your_movie.databinding.FragmentMovieListBinding
import com.test.your_movie.model.MovieModel
import com.test.your_movie.ui.movie_list.list.MovieAdapter
import com.test.your_movie.ui.movie_list.list.MovieHolder
import com.test.your_movie.view_model.MovieListViewModel
import java.util.Calendar

/**
 * Экран со списком фильмов, полученных с сервера*/
class MovieListFragment : Fragment() {

    private lateinit var binding: FragmentMovieListBinding
    private lateinit var adapter: MovieAdapter
    private val movieListViewModel: MovieListViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_home, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_home_exit -> {
                (requireActivity() as HomeActivity).setBottomNavigationViewVisible(false)
                activity!!.findNavController(R.id.nav_host_fragment)
                        .navigate(MovieListFragmentDirections.actionMovieListFragmentToEntryFragment())
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        binding = FragmentMovieListBinding.inflate(inflater, container, false)
        (requireActivity() as HomeActivity).setBottomNavigationViewVisible(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.movieList.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(requireContext())
        binding.movieList.layoutManager = layoutManager

        adapter = MovieAdapter(object : MovieAdapter.OnItemClickListener {
            override fun onItemClick(item: MovieModel, id: Int, boardHolder: MovieHolder) {
                requireActivity().findNavController(R.id.nav_host_fragment)
                        .navigate(MovieListFragmentDirections.actionMovieListFragmentToMovieInfoFragment(item))
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

        val scrollListener = object : EndlessRecyclerViewScrollListener(layoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                binding.load.visibility = View.VISIBLE
                loadMovieList()
            }
        }
        binding.movieList.addOnScrollListener(scrollListener)

        movieListViewModel.getMovieList().observe(viewLifecycleOwner, Observer<ArrayList<MovieModel>> { item ->
            adapter.movies.clear()
            adapter.movies.addAll(item)
            adapter.notifyDataSetChanged()
        })
        if (movieListViewModel.getMovieList().value.isNullOrEmpty()) {
            loadMovieList()
        }
    }

    private fun loadMovieList() {
        movieListViewModel.loadMovieList(2020, object : MovieListViewModel.MovieListCallback {
            override fun onSuccess() {
                binding.load.visibility = View.INVISIBLE
            }

            override fun onError(networkError: NetworkErrorException) {
                Toast.makeText(requireContext(), getText(R.string.fragment_movie_list_no_internet), Toast.LENGTH_SHORT).show()
            }
        })
    }

    /**
     * Создать событие в календаре о просмотре фильма*/
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
