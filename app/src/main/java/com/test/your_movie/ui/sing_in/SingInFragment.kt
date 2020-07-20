package com.test.your_movie.ui.sing_in

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.biometric.BiometricPrompt
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.test.your_movie.R
import com.test.your_movie.databinding.FragmentSingInBinding
import com.test.your_movie.utils.ApiFingerUtils

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
        binding.singInButtonFinger.setOnClickListener {
            authFinger()
        }
        authFinger()
    }

    private fun authFinger() {
        val biometricPrompt = BiometricPrompt(this, ApiFingerUtils.getExecutor(requireContext()),
                object : BiometricPrompt.AuthenticationCallback() {
                    override fun onAuthenticationSucceeded(
                            result: BiometricPrompt.AuthenticationResult
                    ) {
                        super.onAuthenticationSucceeded(result)
                        requireActivity().findNavController(R.id.nav_host_fragment)
                                .navigate(SingInFragmentDirections.actionSingInFragmentToMovieListFragment())
                    }

                    override fun onAuthenticationError(
                            errorCode: Int, errString: CharSequence
                    ) {
                        super.onAuthenticationError(errorCode, errString)
                        Toast.makeText(
                                requireContext(),
                                getString(R.string.error_msg_auth_error),
                                Toast.LENGTH_SHORT
                        ).show()
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
        ApiFingerUtils.some(requireContext(), biometricPrompt)
    }
}
