package com.test.your_movie.ui.movie_info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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
//        binding.singIn.setOnClickListener {
//            requireActivity().findNavController(R.id.nav_host_fragment)
//                .navigate(HomeFragmentDirections.actionHomeFragmentToFlagListFragment())
//        }
//        binding.singUp.setOnClickListener {
//            requireActivity().findNavController(R.id.nav_host_fragment)
//                    .navigate(HomeFragmentDirections.actionHomeFragmentToFlagListFragment())
//        }
    }
}
