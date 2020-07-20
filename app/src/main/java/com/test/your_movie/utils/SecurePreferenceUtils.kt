package com.test.your_movie.utils

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.spec.GCMParameterSpec

object SecurePreferenceUtils {

    private const val alias = "keyYourMovie"
    private const val KEYSTORE_PROVIDER_ANDROID_KEYSTORE = "AndroidKeyStore"

    private var keyGenerator: KeyGenerator
    private var keyGenParameterSpec: KeyGenParameterSpec
    private val cipher: Cipher

    init {
        keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, KEYSTORE_PROVIDER_ANDROID_KEYSTORE)
        keyGenParameterSpec = KeyGenParameterSpec.Builder(alias,
                KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT)
                .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                .build()
        cipher = Cipher.getInstance("AES/GCM/NoPadding")
    }

    fun encrypt(textToEncrypt: String): ByteArray {
        keyGenerator.init(keyGenParameterSpec)
        val secretKey = keyGenerator.generateKey()
        cipher.init(Cipher.ENCRYPT_MODE, secretKey)
        return cipher.doFinal(textToEncrypt.toByteArray(Charsets.UTF_8))
    }

    fun decrypt(encryptedData: ByteArray): String {
        val keyStore = KeyStore.getInstance(KEYSTORE_PROVIDER_ANDROID_KEYSTORE)
        keyStore.load(null)

        val secretKeyEntry = keyStore.getEntry(alias, null) as KeyStore.SecretKeyEntry
        val secretKey = secretKeyEntry.secretKey

        val iv = cipher.iv
        val spec = GCMParameterSpec(128, iv)
        cipher.init(Cipher.DECRYPT_MODE, secretKey, spec)
        val decodedData = cipher.doFinal(encryptedData)
        return String(decodedData, Charsets.UTF_8)
    }

}

//SecurePreferenceUtils.putString(SecurePreferenceUtils.SecurePreferenceKeys.AccessToken, AndroidKeyStore.encrypt(""))
//
//val token = AndroidKeyStore.decrypt(SecurePreferenceUtils.getString(SecurePreferenceUtils.SecurePreferenceKeys.AccessToken, AndroidKeyStore.encrypt("")))