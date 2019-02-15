package com.testbook.githubpr.ui.input

import androidx.lifecycle.ViewModel
import com.testbook.githubpr.models.Repo
import com.testbook.githubpr.models.User
import com.testbook.githubpr.network.GithubService
import retrofit2.Response
import javax.inject.Inject

class InputViewModel
@Inject constructor(private val githubService: GithubService) : ViewModel() {

    suspend fun getUser(username: String): Response<User>? {
        val request = githubService.getUser(username)
        return request.await()
    }

    suspend fun getRepos(username: String): Response<List<Repo>>? {
        val request = githubService.getRepos(username)
        return request.await()
    }
}
