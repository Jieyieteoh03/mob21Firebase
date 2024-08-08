package com.jy.mob21firebase.ui.login

import android.os.Binder
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.material.snackbar.Snackbar
import com.jy.mob21firebase.BuildConfig
import com.jy.mob21firebase.R
import com.jy.mob21firebase.databinding.FragmentLoginBinding
import com.jy.mob21firebase.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>() {
    override val viewModel: LoginViewModel by viewModels()
    override fun getLayoutResource(): Int = R.layout.fragment_login

    override fun onBindView(view: View) {
        super.onBindView(view)

        binding?.btnLinkRegister?.setOnClickListener{
            findNavController().navigate(
                LoginFragmentDirections.actionLoginToRegister()
            )
        }

        binding?.btnLoginWithGoogle?.setOnClickListener {
            val email = binding?.etEmail?.text.toString()
            val password = binding?.etPassword?.text.toString()

            viewModel.login(email,password)
        }

        binding?.btnLoginWithGoogle?.setOnClickListener {
            signInWithGoogle()
        }
    }

    override fun onBindData(view: View) {
        super.onBindData(view)

        lifecycleScope.launch {
            viewModel.success.collect{
                findNavController().navigate(
                    LoginFragmentDirections.actionLoginToHome()
                )
            }
        }
    }

    private fun signInWithGoogle() {
        val googleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId(BuildConfig.oauth_client_id)
            .setNonce("qwerqwer")
            .setAutoSelectEnabled(true)
            .build()

        val request = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        lifecycleScope.launch {
            try {
                val result = CredentialManager.create(requireContext()).getCredential(
                    context = requireContext(),
                    request = request
                )
                handleSignInResult(result)
            } catch (e: Exception) { e.printStackTrace() }
        }
    }

    private fun handleSignInResult(result: GetCredentialResponse) {
        val credential = result.credential
        if (credential !is CustomCredential) return
        if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
            val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
            viewModel.login(googleIdTokenCredential)
            Log.d("debugging", googleIdTokenCredential.displayName.toString())
        }
    }
}