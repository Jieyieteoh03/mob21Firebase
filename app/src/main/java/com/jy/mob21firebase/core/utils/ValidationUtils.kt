package com.jy.mob21firebase.core.utils

import com.jy.mob21firebase.data.model.ValidationField

object ValidationUtils {
    fun validate(vararg fields: ValidationField): String? {
        fields.forEach { field ->
           if (!Regex(field.regExp).matches(field.value)) {
               return field.errMsg
           }
        }
        return null
    }
}