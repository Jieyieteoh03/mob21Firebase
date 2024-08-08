package com.jy.mob21firebase.data.model

data class Extra(
    val location: String = "location",
    val time: String = "time"
) {
    companion object {
        fun fromMap(map: Map<*,*>?): Extra? {
           return map?.let {
                Extra(
                    location = it["location"].toString(),
                    time = it["time"].toString()
                )
            }
        }
    }
}


