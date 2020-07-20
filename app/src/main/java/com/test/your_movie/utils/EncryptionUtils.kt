package com.test.your_movie.utils

import android.graphics.Bitmap
import android.util.Base64
import java.io.ByteArrayOutputStream
import java.io.UnsupportedEncodingException
import java.nio.charset.StandardCharsets

object EncryptionUtils {

    @Throws(UnsupportedEncodingException::class)
    fun base64Photo(photo: Bitmap?): String {
        if (photo == null) {
            return ""
        }

        val byteArrayOS = ByteArrayOutputStream()
        photo.compress(Bitmap.CompressFormat.JPEG, 30, byteArrayOS)
        val result = Base64.encode(byteArrayOS.toByteArray(), Base64.NO_WRAP)
        return String(result, StandardCharsets.UTF_8)
    }

    @Throws(UnsupportedEncodingException::class)
    fun decodeBase64String(encodedString: String): String {
        return String(Base64.decode(encodedString.toByteArray(charset("UTF-8")), Base64.DEFAULT))
    }

}
