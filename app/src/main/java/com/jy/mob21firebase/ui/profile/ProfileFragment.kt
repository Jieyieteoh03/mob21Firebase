package com.jy.mob21firebase.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.jy.mob21firebase.R
import com.jy.mob21firebase.core.services.StorageService
import com.jy.mob21firebase.databinding.FragmentProfileBinding
import com.jy.mob21firebase.ui.base.BaseFragment
import com.jy.mob21firebase.ui.base.BaseViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment() : BaseFragment<FragmentProfileBinding>() {
    override val viewModel: ProfileViewModel by viewModels()
    private lateinit var imagePickerLauncher: ActivityResultLauncher<String>
    private var imageName: String?= null
    @Inject
    lateinit var storageService: StorageService
    override fun getLayoutResource(): Int = R.layout.fragment_profile

    override fun onBindView(view: View) {
        super.onBindView(view)
        imagePickerLauncher = registerForActivityResult(
            ActivityResultContracts.GetContent()
        ) {
            it?.let { uri ->
                binding?.ivProfile?.setImageURI(uri)
                storageService.uploadImage(uri = it, imageName) { name ->
                    if (name != null) {
                        viewModel.updateProfile(name)
                        imageName = name
                    }
                }

            }
        }
        binding?.btnProfilePic?.setOnClickListener {
            imagePickerLauncher.launch("image/*")
        }

    }

    override fun onBindData(view: View) {
        super.onBindData(view)
        viewModel.user.observe(viewLifecycleOwner) {
            imageName = it.profilePic
            binding?.run {
                etName.text = it.firstName
                etEmail.text = it.email
                showProfile(ivProfile, imageName)
            }
        }
    }

    private fun showProfile(imageView: ImageView, name: String?) {
        if (name.isNullOrEmpty()) return
        storageService.getImageUri(name) {
            Glide.with(imageView)
                .load(it)
                .placeholder(R.drawable.ic_profile)
                .centerCrop()
                .into(imageView)
        }
    }

    /*
        Profile picture, icon button to change the profile picture
        name and email
    */

}