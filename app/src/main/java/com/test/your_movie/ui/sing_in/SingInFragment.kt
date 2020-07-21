package com.test.your_movie.ui.sing_in

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.biometric.BiometricPrompt
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.test.your_movie.R
import com.test.your_movie.databinding.FragmentSingInBinding
import com.test.your_movie.model.ResourceModel
import com.test.your_movie.utils.BiometricUtils
import com.test.your_movie.view_model.AuthViewModel

/**
 * Экран авторизации. Есть возможность зайти с помощью отпечатка пальца, который уже есть на телефоне,
 * или с помощью логина/пароля, которые были зарегистрированы после сканирования QR-кода.
 * В данной реализации отпечаток пальца не приклеплен к логину/паролю, так как не хватило времени.*/
class SingInFragment : Fragment() {

    private lateinit var binding: FragmentSingInBinding
    private val authViewModel: AuthViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSingInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        authViewModel.init(requireContext())
        authViewModel.getAuthStatus().observe(viewLifecycleOwner, Observer<ResourceModel> { item ->
            when (item.status) {
                ResourceModel.Status.COMPLETED -> {
                    requireActivity().findNavController(R.id.nav_host_fragment)
                            .navigate(SingInFragmentDirections.actionSingInFragmentToMovieListFragment())
                }
                ResourceModel.Status.ERROR -> {
                    Toast.makeText(requireContext(), "Wrong login or password", Toast.LENGTH_SHORT).show()
                }
            }
        })
        binding.singInButtonDone.setOnClickListener {
            authViewModel.checkUser(binding.singInFieldLogin.text.toString(), binding.singInFieldPassword.text.toString())
        }
        binding.singInButtonFinger.setOnClickListener {
            authFinger()
        }
        authFinger()
    }

    private fun authFinger() {
        val biometricPrompt = BiometricPrompt(this, BiometricUtils.getExecutor(requireContext()),
                object : BiometricPrompt.AuthenticationCallback() {
                    override fun onAuthenticationSucceeded(
                            result: BiometricPrompt.AuthenticationResult
                    ) {
                        super.onAuthenticationSucceeded(result)
                        requireActivity().findNavController(R.id.nav_host_fragment)
                                .navigate(SingInFragmentDirections.actionSingInFragmentToMovieListFragment())
                    }

                    override fun onAuthenticationFailed() {
                        super.onAuthenticationFailed()
                        Toast.makeText(
                                requireContext(),
                                getString(R.string.error_msg_auth_failed),
                                Toast.LENGTH_SHORT
                        ).show()
                    }
                })
        BiometricUtils.biometricInit(requireContext(), biometricPrompt)
    }
}
