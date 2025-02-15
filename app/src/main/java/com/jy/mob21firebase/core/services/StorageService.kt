package com.jy.mob21firebase.core.services

import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage

class StorageService(
    private val authService: AuthService
) {
    private val storageRef = FirebaseStorage.getInstance().getReference("image/*")

    fun createImageName(): String? {
        val uid = authService.getUid() ?: return null
        return uid.take(5) + System.nanoTime()
    }
    fun uploadImage(uri: Uri, name: String?, callback: (String?) -> Unit) {
        val imageName = name ?: createImageName()
        if (imageName != null) {
            storageRef.child(imageName).putFile(uri)
                .addOnSuccessListener {
                    callback(imageName)
                }
                .addOnFailureListener{
                    callback(null)
                }
        }
    }

    fun getImageUri(imageName: String, callback: (Uri) -> Unit) {
        storageRef.child(imageName).downloadUrl.addOnSuccessListener {
            callback(it)
        }
    }
}