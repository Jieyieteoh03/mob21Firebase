package com.jy.mob21firebase.ui.profile

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jy.mob21firebase.data.model.User
import com.jy.mob21firebase.data.repo.UserRepo
import com.jy.mob21firebase.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepo: UserRepo
): BaseViewModel() {

    val user = MutableLiveData<User>()

    init {
        getUser()
    }

    fun getUser() {
        viewModelScope.launch {
            errorHandler {
                userRepo.getUser()
            }?.let {
                user.value = it
                Log.d("debugging", it.toString())
            }
        }
    }

    fun updateProfile(name:String) {
        viewModelScope.launch {
            errorHandler {
                user.value?.let {
                   userRepo.updateUser(it.copy(profilePic = name))
                }

            }
        }
    }
}