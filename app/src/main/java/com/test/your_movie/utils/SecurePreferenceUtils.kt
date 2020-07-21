package com.test.your_movie.utils

import android.util.Base64
import java.io.UnsupportedEncodingException
import java.nio.charset.Charset
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec

/**Класс для шифрования/дешифрования с помощью KeyStore и base64
 * */
object SecurePreferenceUtils {

    const val SALT = "salt"
    const val IV = "iv"
    const val ENCRYPTED = "encrypted"

    fun encrypt(textToEncrypt: String): HashMap<String, ByteArray> {
        val random = SecureRandom()
        val salt = ByteArray(256)
        random.nextBytes(salt)

        val pbKeySpec = PBEKeySpec(textToEncrypt.toCharArray(), salt, 1324, 256)
        val secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
        val keyBytes = secretKeyFactory.generateSecret(pbKeySpec).encoded
        val keySpec = SecretKeySpec(keyBytes, "AES")

        val ivRandom = SecureRandom()
        val iv = ByteArray(16)
        ivRandom.nextBytes(iv)
        val ivSpec = IvParameterSpec(iv)

        val cipher = Cipher.getInstance("AES/CBC/PKCS7Padding")
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec)
        val encrypted = cipher.doFinal(textToEncrypt.toByteArray(Charset.defaultCharset()))

        val cryptMap = HashMap<String, ByteArray>()
        cryptMap[SALT] = salt
        cryptMap[IV] = iv
        cryptMap[ENCRYPTED] = encrypted

        return cryptMap
    }

    fun decrypt(password: String, encryptedData: ByteArray, salt: ByteArray, iv: ByteArray): String {
        val pbKeySpec = PBEKeySpec(password.toCharArray(), salt, 1324, 256)
        val secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
        val keyBytes = secretKeyFactory.generateSecret(pbKeySpec).encoded
        val keySpec = SecretKeySpec(keyBytes, "AES")

        val cipher = Cipher.getInstance("AES/CBC/PKCS7Padding")
        val ivSpec = IvParameterSpec(iv)
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec)
        val decrypted = cipher.doFinal(encryptedData)
        return decrypted.toString(Charset.defaultCharset())
    }

    @Throws(UnsupportedEncodingException::class)
    fun decodeBase64String(encodedString: String): String {
        return String(Base64.decode(encodedString.toByteArray(charset("UTF-8")), Base64.DEFAULT))
    }

}
