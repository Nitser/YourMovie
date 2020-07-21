package com.test.your_movie.view_model

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.your_movie.database.AppDatabase
import com.test.your_movie.database.repository.UserRepository
import com.test.your_movie.database.entity.User
import com.test.your_movie.model.ResourceModel
import com.test.your_movie.utils.SecurePreferenceUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**ViewModel для работы с авторизацией/регистрацией*/
class AuthViewModel : ViewModel() {

    private lateinit var repository: UserRepository
    private val authStatus = MutableLiveData<ResourceModel>()

    fun getAuthStatus(): LiveData<ResourceModel> {
        return authStatus
    }

    fun init(context: Context) {
        val userDao = AppDatabase.getDatabase(context).userDao()
        repository = UserRepository(userDao)
    }

    private fun insert(user: User) {
        repository.insert(user)
    }

    fun checkUser(login: String, password: String) = viewModelScope.launch(Dispatchers.IO) {
        val user = repository.findByLogin(login)
        if (user != null) {
            try {
                val rightPassword = SecurePreferenceUtils.decrypt(password, user.password, user.salt, user.iv)
                if (rightPassword == password)
                    authStatus.postValue(ResourceModel(ResourceModel.Status.COMPLETED))
                else
                    authStatus.postValue(ResourceModel(ResourceModel.Status.ERROR))
            } catch (ex: Exception) {
                Log.i("AuthVM", ex.toString())
                authStatus.postValue(ResourceModel(ResourceModel.Status.ERROR))
            }
        } else {
            authStatus.postValue(ResourceModel(ResourceModel.Status.ERROR))
        }
    }

    fun saveUserData(login: String, password: String) = viewModelScope.launch(Dispatchers.IO) {
        val user = repository.findByLogin(login)
        if (user == null) {
            try {
                val encryptPassword = SecurePreferenceUtils.encrypt(password)
                val newUser = User(
                        login,
                        encryptPassword[SecurePreferenceUtils.ENCRYPTED]!!,
                        encryptPassword[SecurePreferenceUtils.SALT]!!,
                        encryptPassword[SecurePreferenceUtils.IV]!!
                )
                insert(newUser)
                authStatus.postValue(ResourceModel(ResourceModel.Status.COMPLETED))
            } catch (ex: Exception) {
                Log.i("AuthVM", ex.toString())
                authStatus.postValue(ResourceModel(ResourceModel.Status.ERROR))
            }
        } else {
            authStatus.postValue(ResourceModel(ResourceModel.Status.ERROR))
        }
    }

}
