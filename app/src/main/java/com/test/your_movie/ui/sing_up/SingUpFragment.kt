package com.test.your_movie.ui.sing_up

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.test.your_movie.databinding.FragmentSingUpBinding

class SingUpFragment : Fragment() {

    private lateinit var binding: FragmentSingUpBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSingUpBinding.inflate(inflater, container, false)
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
