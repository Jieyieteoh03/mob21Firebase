package com.jy.mob21firebase.core.services

import android.content.Context
import android.content.IntentSender
import android.credentials.CredentialManager
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.jy.mob21firebase.data.model.User
import com.jy.mob21firebase.data.repo.UserRepo
import kotlinx.coroutines.tasks.await

class AuthService (
    private val context: Context
){
    private val auth = FirebaseAuth.getInstance()
    suspend fun createUserWithEmailAndPassword(email: String, password: String):Boolean {
        val res = auth.createUserWithEmailAndPassword(
            email,password
        ).await()
        return res.user != null
    }

    suspend fun loginEmailWithPassword(email: String,password: String): FirebaseUser? {
        val res = auth.signInWithEmailAndPassword(email,password).await()
        return res.user
    }

    fun isLoggedIn(): Boolean = auth.currentUser != null

    // use suspend function only when we use await
    fun logOut() { auth.signOut() }

    fun getCurrentUser(): FirebaseUser? = auth.currentUser

    fun getUid(): String? {
        return auth.currentUser?.uid
    }

    suspend fun signInWithGoogle(credential: GoogleIdTokenCredential) {
        val firebaseCredential = GoogleAuthProvider.getCredential(credential.idToken, null)
        auth.signInWithCredential(firebaseCredential).await()
    }

    fun getCredential() {

    }
}