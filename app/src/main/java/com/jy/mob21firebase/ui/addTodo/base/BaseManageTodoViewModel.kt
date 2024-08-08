package com.jy.mob21firebase.ui.addTodo.base

import android.icu.text.CaseMap.Title
import androidx.lifecycle.ViewModel
import com.jy.mob21firebase.ui.base.BaseViewModel
import kotlinx.coroutines.flow.MutableSharedFlow

abstract class BaseManageTodoViewModel: BaseViewModel() {
    abstract fun submitTask(title: String, desc: String)
}