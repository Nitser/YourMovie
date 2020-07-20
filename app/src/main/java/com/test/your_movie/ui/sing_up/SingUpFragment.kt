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
import androidx.navigation.findNavController
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult
import com.test.your_movie.R
import com.test.your_movie.databinding.FragmentSingUpBinding
import com.test.your_movie.utils.EncryptionUtils
import com.test.your_movie.view_model.AppStateViewModel
import com.test.your_movie.view_model.InitializationViewModel

class SingUpFragment : Fragment() {

    private lateinit var binding: FragmentSingUpBinding
    private val appStateViewModel: AppStateViewModel by activityViewModels()
    private val initializationViewModel: InitializationViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSingUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        run {
            if (appStateViewModel.getQRCodeState().value == null || appStateViewModel.getQRCodeState().value == false) {
                appStateViewModel.setQRCodeState(true)

                val scanner = IntentIntegrator.forSupportFragment(this)
                scanner.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
                scanner.setOrientationLocked(false)
                scanner.setCameraId(0)
//            integrator.setOrientationLocked(true);
//            scanner.setBeepEnabled(true)
                scanner.initiateScan()
            }
        }
    }

    //safe orientation
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result: IntentResult? = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents != null) {
                try {
                    val url = EncryptionUtils.decodeBase64String(result.contents)
                    val uri = Uri.parse(url)
                    val login = uri.getQueryParameter("login")
                    val password = uri.getQueryParameter("password")
                    initializationViewModel.saveUserData(login, password, object : InitializationViewModel.InitializationCallback {
                        override fun onSuccess() {
                            requireActivity().findNavController(R.id.nav_host_fragment)
                                    .navigate(SingUpFragmentDirections.actionSingUpFragmentToMovieListFragment())
                        }

                        override fun onError() {
                            Toast.makeText(requireContext(), "Unsuitable QR-code", Toast.LENGTH_SHORT).show()
                            requireActivity().findNavController(R.id.nav_host_fragment).popBackStack()
                        }
                    })
                } catch (e: Exception) {
                    Toast.makeText(requireContext(), "Unsuitable QR-code", Toast.LENGTH_SHORT).show()
                    requireActivity().findNavController(R.id.nav_host_fragment).popBackStack()
                }
            } else {
                Toast.makeText(requireContext(), "Content is null", Toast.LENGTH_SHORT).show()
                requireActivity().findNavController(R.id.nav_host_fragment).popBackStack()
            }
        } else {
            Toast.makeText(requireContext(), "Result is null", Toast.LENGTH_SHORT).show()
            requireActivity().findNavController(R.id.nav_host_fragment).popBackStack()
            super.onActivityResult(requestCode, resultCode, data)
        }
        appStateViewModel.setQRCodeState(false)
    }
}
