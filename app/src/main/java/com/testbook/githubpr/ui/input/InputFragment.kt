package com.testbook.githubpr.ui.input

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.testbook.githubpr.R
import dagger.android.support.DaggerFragment

class InputFragment : DaggerFragment() {

    companion object {
        fun newInstance() = InputFragment()
    }

    private lateinit var viewModel: InputViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.input_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(InputViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
