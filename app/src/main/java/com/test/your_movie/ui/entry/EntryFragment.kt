package com.test.your_movie.ui.entry

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.test.your_movie.R
import com.test.your_movie.databinding.FragmentEntryBinding
/**
 * Начальный экран singIn/singUp кнопками
 * По хорошему тут надо было доставать сохраненные данные вошедшего пользователя, но времени на это не хватило
 * */
class EntryFragment : Fragment() {

    private lateinit var binding: FragmentEntryBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        binding = FragmentEntryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.singIn.setOnClickListener {
            requireActivity().findNavController(R.id.nav_host_fragment)
                    .navigate(EntryFragmentDirections.actionEntryFragmentToSingInFragment())
        }
        binding.singUp.setOnClickListener {
            requireActivity().findNavController(R.id.nav_host_fragment)
                    .navigate(EntryFragmentDirections.actionEntryFragmentToSingUpFragment())
        }
    }
}
