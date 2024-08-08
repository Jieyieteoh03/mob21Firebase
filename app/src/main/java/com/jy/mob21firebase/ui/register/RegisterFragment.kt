package com.jy.mob21firebase.ui.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.jy.mob21firebase.R
import com.jy.mob21firebase.databinding.FragmentLoginBinding
import com.jy.mob21firebase.databinding.FragmentRegisterBinding
import com.jy.mob21firebase.ui.base.BaseFragment
import com.jy.mob21firebase.ui.home.HomeFragmentDirections
import com.jy.mob21firebase.ui.login.LoginFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding>() {
   override val viewModel: RegisterViewModel by viewModels()
    override fun getLayoutResource(): Int = R.layout.fragment_register

    override fun onBindView(view: View) {
        super.onBindView(view)

        binding?.btnRegister?.setOnClickListener {
            viewModel.register(
                firstName = binding?.etFirstName?.text.toString(),
                lastName = binding?.etLastName?.text.toString(),
                email = binding?.etEmail?.text.toString(),
                password = binding?.etPassword?.text.toString(),
                confirmPassword = binding?.etConfirmPassword?.text.toString()
            )
        }
    }

    override fun onBindData(view: View) {
        super.onBindData(view)

        lifecycleScope.launch {
            viewModel.success.collect{
                findNavController().navigate(
                    RegisterFragmentDirections.actionRegisterToHome()
                )
            }
        }
    }
}