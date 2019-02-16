package com.testbook.githubpr.ui.result

import androidx.lifecycle.ViewModel
import com.testbook.githubpr.models.Repo
import com.testbook.githubpr.models.pr.PullRequest
import com.testbook.githubpr.network.GithubService
import retrofit2.Response
import javax.inject.Inject

class ResultViewModel
    @Inject constructor(private val githubService: GithubService): ViewModel() {

    suspend fun getPullRequests(username: String, repoName: String): Response<List<PullRequest>>? {
        val request = githubService.getPullRequests(username, repoName)
        return request.await()
    }

}
