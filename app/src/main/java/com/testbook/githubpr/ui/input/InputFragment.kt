package com.testbook.githubpr.ui.input

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.testbook.githubpr.R
import com.testbook.githubpr.models.Repo
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.input_fragment.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
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
            username.clearFocus()
            if (usernameString.isEmpty()) {
                Toast.makeText(context, "Username cannot be empty", Toast.LENGTH_SHORT).show()
            } else {
                getUser(usernameString)
            }
        }
    }

    private fun getUser(username: String) {
        GlobalScope.launch {
            val response = viewModel.getUser(username)
            if (response != null && response.isSuccessful) {
                val user = response.body()
                if (user != null) {
                    getRepositories(user.login)
                } else {
                    Toast.makeText(context, "User not found", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun getRepositories(username: String) {
        GlobalScope.launch {
            val response = viewModel.getRepos(username)
            if (response != null && response.isSuccessful) {
                val repos = response.body()
                if (repos != null) {
                    setData(repos)
                }
            } else {
                Toast.makeText(context, "No repos found", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setData(repos: List<Repo>) {
        activity?.runOnUiThread {
            recycler_view.layoutManager = LinearLayoutManager(context)
            recycler_view.adapter = RepoAdapter(repos)
        }
    }

}
