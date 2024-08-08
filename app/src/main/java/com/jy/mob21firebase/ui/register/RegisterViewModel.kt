package com.jy.mob21firebase.ui.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jy.mob21firebase.core.services.AuthService
import com.jy.mob21firebase.core.utils.ValidationUtils
import com.jy.mob21firebase.data.model.User
import com.jy.mob21firebase.data.model.ValidationField
import com.jy.mob21firebase.data.repo.UserRepo
import com.jy.mob21firebase.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authService: AuthService,
    private val userRepo:UserRepo
):BaseViewModel() {
    val success: MutableSharedFlow<Unit> = MutableSharedFlow()
    fun register(
        firstName: String,
        lastName:String,
        email: String,
        password: String,
        confirmPassword: String
    ) {
        val error = ValidationUtils.validate(
            ValidationField(firstName, "[a-zA-Z ]{2,20}", "Enter a valid name"),
            ValidationField(lastName, "[a-zA-Z ]{2,20}", "Enter a valid name"),
            ValidationField(email, "[a-zA-Z0-9]+@[a-zA-Z0-9]+.[a-zA-Z0-9]+", "Enter a valid email"),
            ValidationField(password, "[a-zA-Z0-9#$%]{3,20}", "Enter valid password")
        )

        if (error == null) {
            viewModelScope.launch {
                errorHandler {
                    authService.createUserWithEmailAndPassword(email, password)
                    authService.loginEmailWithPassword(email, password)
                }?.let {
                    userRepo.createUser(
                        User(firstName,lastName, email)
                    )
                    success.emit(Unit)
                }

            }
        } else {
            viewModelScope.launch {
                _error.emit(error)
            }
        }

    }
}