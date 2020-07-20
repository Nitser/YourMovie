package com.test.your_movie.utils

import android.content.Context
import android.widget.Toast
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import com.test.your_movie.R
import java.util.concurrent.Executor

object ApiFingerUtils {

    fun getExecutor(context: Context): Executor {
        return ContextCompat.getMainExecutor(context)
    }

    fun some(context: Context, biometricPrompt: BiometricPrompt) {

        val biometricManager = BiometricManager.from(context)

        when (biometricManager.canAuthenticate()) {
            BiometricManager.BIOMETRIC_SUCCESS -> {
//                Toast.makeText(
//                        context,
////                        getString(R.string.error_msg_no_biometric_hardware),
//                        "Some",
//                        Toast.LENGTH_LONG
//                ).show()
                authUser(context, biometricPrompt)
            }
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE ->
                Toast.makeText(
                        context,
//                        getString(R.string.error_msg_no_biometric_hardware),
                        "Some",
                        Toast.LENGTH_LONG
                ).show()
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE ->
                Toast.makeText(
                        context,
//                        getString(R.string.error_msg_biometric_hw_unavailable),
                        "Some",
                        Toast.LENGTH_LONG
                ).show()
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED ->
                Toast.makeText(
                        context,
//                        getString(R.string.error_msg_biometric_not_setup),
                        "Some",
                        Toast.LENGTH_LONG
                ).show()
        }
    }

    private fun authUser(context: Context, biometricPrompt: BiometricPrompt) {
        val promptInfo = BiometricPrompt.PromptInfo.Builder()
                .setTitle(context.getString(R.string.auth_title))
                .setSubtitle(context.getString(R.string.auth_subtitle))
                .setDescription(context.getString(R.string.auth_description))
                .setDeviceCredentialAllowed(false)
                .setNegativeButtonText("No text")
                .build()

        biometricPrompt.authenticate(promptInfo)
    }

}
