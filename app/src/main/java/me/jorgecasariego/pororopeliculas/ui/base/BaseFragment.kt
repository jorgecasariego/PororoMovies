package me.jorgecasariego.pororopeliculas.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

abstract class BaseFragment<T : ViewDataBinding> : Fragment() {
    private lateinit var viewBinding: T

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return createView(inflater, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onUICreated(viewBinding)
    }

    private fun createView(inflater: LayoutInflater, container: ViewGroup?): View {
        with(
                DataBindingUtil.inflate<T>(
                        inflater, layout(), container, false
                )
        ) {
            viewBinding = this
            return this.root
        }
    }

    fun onBackPressed() {
        findNavController().navigateUp()
    }

    fun navigateToGraph(actionId: Int) {
        findNavController().navigate(actionId)
    }

    abstract fun layout(): Int
    abstract fun onUICreated(viewBinding: T)
}