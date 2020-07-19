package com.test.your_movie.ui.sing_in

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.test.your_movie.R
import com.test.your_movie.databinding.FragmentSingInBinding

class SingInFragment : Fragment() {

    private lateinit var binding: FragmentSingInBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSingInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.singInButtonDone.setOnClickListener {
            requireActivity().findNavController(R.id.nav_host_fragment)
                .navigate(SingInFragmentDirections.actionSingInFragmentToMovieListFragment())
        }
//        binding.singUp.setOnClickListener {
//            requireActivity().findNavController(R.id.nav_host_fragment)
//                    .navigate(HomeFragmentDirections.actionHomeFragmentToFlagListFragment())
//        }
    }
}
