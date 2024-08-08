package com.jy.mob21firebase.ui.login

import androidx.lifecycle.viewModelScope
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.jy.mob21firebase.core.services.AuthService
import com.jy.mob21firebase.data.model.User
import com.jy.mob21firebase.data.repo.UserRepo
import com.jy.mob21firebase.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authService: AuthService,
    private val userRepo: UserRepo
): BaseViewModel() {
    val success: MutableSharedFlow<Unit> = MutableSharedFlow()
    fun login(email:String, password:String) {
        viewModelScope.launch (Dispatchers.IO){
            errorHandler {
                validate(email, password)
                authService.loginEmailWithPassword(email,password)
            }?.let {
                success.emit(Unit)
            }
        }
   }

    fun login(credential: GoogleIdTokenCredential) {
        viewModelScope.launch {
            authService.signInWithGoogle(credential)
            userRepo.createUser(
                User(credential.displayName?: "", "", credential.id, credential.profilePictureUri.toString())
            )
            success.emit(Unit)
        }
    }

    private fun validate(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            throw Exception("Email or password cannot be empty")
        }
    }
}