package com.jy.mob21firebase.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar
import com.jy.mob21firebase.R
import kotlinx.coroutines.launch


abstract class BaseFragment<T: ViewBinding> : Fragment() {
    protected abstract val viewModel: BaseViewModel
    protected var binding: T? = null
    protected abstract fun getLayoutResource(): Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(getLayoutResource(), container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onBindView(view)
        onBindData(view)
    }

    protected open fun onBindView(view: View) {
        binding = DataBindingUtil.bind(view)
    }

    protected open fun onBindData(view: View) {
        lifecycleScope.launch {
            viewModel.error.collect{
                showSnackbar(view, it, true)
            }
        }

        lifecycleScope.launch {
            viewModel.finish.collect{
                showSnackbar(view, it)
            }
        }
    }

    protected fun showSnackbar(view: View, msg: String, isError: Boolean = false) {
        val snackbar = Snackbar.make(view, msg, Snackbar.LENGTH_LONG)
        val color = if (isError) {
            R.color.red
        } else {
            R.color.green
        }
        snackbar.setBackgroundTint(
            ContextCompat.getColor(requireContext(), color)
        )
        snackbar.show()
    }
}