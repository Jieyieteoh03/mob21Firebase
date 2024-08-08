package com.jy.mob21firebase.data.model

import java.net.URL

data class User(
    val firstName: String,
    val lastName: String,
    val email:String,
    val profilePic: String? = null
) {

    companion object {
        fun fromMap(map: Map<*,*>): User {
            return User(
                firstName = map["firstName"].toString(),
                lastName = map["lastName"].toString(),
                email = map["email"].toString(),
                profilePic = map["profilePic"].toString()
            )
        }
    }
}

