package com.test.your_movie.model

data class Resource(
        val status: Status
//        val hashMap: HashMap<String, ByteArray>?
//        val data: T?,
//        val exception: Exception?
) {
    enum class Status {
        ERROR,
        COMPLETED
    }
}
