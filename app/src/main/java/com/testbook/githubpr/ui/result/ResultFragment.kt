package com.testbook.githubpr.ui.result

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.testbook.githubpr.R
import com.testbook.githubpr.models.pr.PullRequest
import com.testbook.githubpr.ui.input.InteractionListener
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.result_fragment.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject


class ResultFragment : DaggerFragment(), InteractionListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: ResultViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.result_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ResultViewModel::class.java)


        getPullRequests(arguments?.getString("username")!!, arguments?.getString("repo_name")!!)
    }

    private fun getPullRequests(username: String, repo_name: String) {
        loading_animation.visibility = View.VISIBLE
        GlobalScope.launch {
            val result = viewModel.getPullRequests(username, repo_name)
            if (result != null && result.isSuccessful) {
                val repos = result.body()
                if (repos != null && repos.isNotEmpty()) {
                    setData(repos)
                } else {
                    showError()
                }
            } else {
                showError()
            }
        }
    }

    private fun setData(repos: List<PullRequest>) {
        activity?.runOnUiThread {
            loading_animation.visibility = View.GONE
            recycler_view.layoutManager = LinearLayoutManager(context)
            recycler_view.adapter = ResultAdapter(repos, this)
            recycler_view.visibility = View.VISIBLE
        }
    }

    private fun showError() {
        activity?.runOnUiThread {
            loading_animation.setAnimation(R.raw.error_emoji)
            val toast = Toast.makeText(context, "No pull requests found", Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.TOP, 0, 0)
            toast.show()

        }
    }

    override fun openResult(repo: String) {
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(repo)
        startActivity(i)
    }

}
