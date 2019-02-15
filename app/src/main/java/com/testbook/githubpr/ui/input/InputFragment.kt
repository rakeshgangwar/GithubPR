package com.testbook.githubpr.ui.input

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.testbook.githubpr.R
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.input_fragment.*
import javax.inject.Inject

class InputFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: InputViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.input_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(InputViewModel::class.java)

        username_submit.setOnClickListener {
            val usernameString = username.text.toString()
            if (usernameString.isEmpty()) {
                Toast.makeText(context, "Username cannot be empty", Toast.LENGTH_SHORT).show()
            } else {
                getUser(usernameString)
            }
        }
    }

    private fun getUser(username: String) {
        viewModel.getUser(username)
        viewModel.userData.observe(viewLifecycleOwner, Observer {
            if (it.isSuccessful) {
                val user = it.body()
                if (user != null) {
                    getRepositories(user.login)
                }
                viewModel.userData.removeObservers(viewLifecycleOwner)
            } else {
                Toast.makeText(context, "User not found", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun getRepositories(username: String) {
        viewModel.getRepos(username)
        viewModel.repoData.observe(viewLifecycleOwner, Observer {
            if (it.isSuccessful) {
                val repos = it.body()
                if (repos != null) {
                    Log.d("", "")
                }
                viewModel.repoData.removeObservers(viewLifecycleOwner)
            } else {
                Toast.makeText(context, "No repos found", Toast.LENGTH_SHORT).show()
            }
        })
    }

}
