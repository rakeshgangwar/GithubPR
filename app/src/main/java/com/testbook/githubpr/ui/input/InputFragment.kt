package com.testbook.githubpr.ui.input

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.testbook.githubpr.R
import com.testbook.githubpr.models.Repo
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.input_fragment.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject


class InputFragment : DaggerFragment(), InteractionListener {

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
            val inputManager = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(
                activity?.currentFocus?.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
            username.clearFocus()
            if (usernameString.isEmpty()) {
                Toast.makeText(context, "Username cannot be empty", Toast.LENGTH_SHORT).show()
            } else {
                getUser(usernameString)
                loading_animation.visibility = View.VISIBLE
            }
        }
    }

    override fun onResume() {
        super.onResume()
        username.setText("")
    }

    private fun getUser(username: String) {
        GlobalScope.launch {
            val response = viewModel.getUser(username)
            if (response != null && response.isSuccessful) {
                val user = response.body()
                if (user != null) {
                    getRepositories(user.login)
                }
            } else {
                activity?.runOnUiThread {
                    loading_animation.visibility = View.GONE
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
                if (repos != null && repos.isNotEmpty()) {
                    setData(repos)
                } else {
                    activity?.runOnUiThread {
                        loading_animation.visibility = View.GONE
                        Toast.makeText(context, "No repos found", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                activity?.runOnUiThread {
                    loading_animation.visibility = View.GONE
                    Toast.makeText(context, "No repos found", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setData(repos: List<Repo>) {
        activity?.runOnUiThread {
            error_message.visibility = View.GONE
            loading_animation.visibility = View.GONE
            recycler_view.layoutManager = LinearLayoutManager(context)
            recycler_view.adapter = RepoAdapter(repos, this)
            recycler_view.visibility = View.VISIBLE
        }
    }

    override fun openResult(repo: String) {
        val args = Bundle()
        args.putString("username", username.text.toString())
        args.putString("repo_name", repo)
        NavHostFragment.findNavController(this).navigate(R.id.resultFragment, args)
    }
}
