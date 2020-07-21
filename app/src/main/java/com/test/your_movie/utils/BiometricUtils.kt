package com.test.your_movie.utils

import android.content.Context
import android.widget.Toast
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import com.test.your_movie.R
import java.util.concurrent.Executor

/**Класс для работы с отпечатками
 * */
object BiometricUtils {

    fun getExecutor(context: Context): Executor {
        return ContextCompat.getMainExecutor(context)
    }

    fun biometricInit(context: Context, biometricPrompt: BiometricPrompt) {

        val biometricManager = BiometricManager.from(context)

        when (biometricManager.canAuthenticate()) {
            BiometricManager.BIOMETRIC_SUCCESS -> {
                authUser(context, biometricPrompt)
            }
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE ->
                Toast.makeText(
                        context,
                        context.getString(R.string.error_msg_no_biometric_hardware),
                        Toast.LENGTH_LONG
                ).show()
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE ->
                Toast.makeText(
                        context,
                        context.getString(R.string.error_msg_biometric_hw_unavailable),
                        Toast.LENGTH_LONG
                ).show()
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED ->
                Toast.makeText(
                        context,
                        context.getString(R.string.error_msg_biometric_not_setup),
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
