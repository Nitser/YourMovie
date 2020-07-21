package com.test.your_movie.ui.sing_up

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult
import com.test.your_movie.R
import com.test.your_movie.databinding.FragmentSingUpBinding
import com.test.your_movie.model.ResourceModel
import com.test.your_movie.utils.SecurePreferenceUtils
import com.test.your_movie.view_model.AppStateViewModel
import com.test.your_movie.view_model.AuthViewModel

/**
 * Экран регистрации с помощью QR-кода*/
class SingUpFragment : Fragment() {

    private lateinit var binding: FragmentSingUpBinding
    private val appStateViewModel: AppStateViewModel by activityViewModels()
    private val authViewModel: AuthViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSingUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        authViewModel.init(requireContext())
        authViewModel.getAuthStatus().observe(viewLifecycleOwner, Observer<ResourceModel> { item ->
            when (item.status) {
                ResourceModel.Status.COMPLETED -> {
                    requireActivity().findNavController(R.id.nav_host_fragment)
                            .navigate(SingUpFragmentDirections.actionSingUpFragmentToMovieListFragment())
                }
                ResourceModel.Status.ERROR -> {
                    Toast.makeText(requireContext(), "Login is occupied", Toast.LENGTH_SHORT).show()
                }
            }
        })

        binding.singUpButtonScan.setOnClickListener {
            qrCodeStart()
        }

        qrCodeStart()
    }

    private fun qrCodeStart() {
        run {
            if (appStateViewModel.getQRCodeState().value == null || appStateViewModel.getQRCodeState().value == false) {
                appStateViewModel.setQRCodeState(true)

                val scanner = IntentIntegrator.forSupportFragment(this)
                scanner.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
                scanner.setOrientationLocked(false)
                scanner.setCameraId(0)
                scanner.initiateScan()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result: IntentResult? = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents != null) {
                try {
                    val url = SecurePreferenceUtils.decodeBase64String(result.contents)
                    val uri = Uri.parse(url)
                    val login = uri.getQueryParameter("login")
                    val password = uri.getQueryParameter("password")
                    if (!login.isNullOrEmpty() && !password.isNullOrEmpty()) {
                        authViewModel.saveUserData(login, password)
                    } else {
                        Toast.makeText(requireContext(), "Unsuitable QR-code", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(requireContext(), "Ex: Unsuitable QR-code", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireContext(), "Content is null", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(requireContext(), "Result is null", Toast.LENGTH_SHORT).show()
            super.onActivityResult(requestCode, resultCode, data)
        }
        appStateViewModel.setQRCodeState(false)
    }
}
