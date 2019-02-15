package com.testbook.githubpr.ui.input

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.testbook.githubpr.models.Repo
import com.testbook.githubpr.models.User
import com.testbook.githubpr.network.GithubService
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

class InputViewModel
@Inject constructor(private val githubService: GithubService) : ViewModel() {

    var userData = MutableLiveData<Response<User>>()

    var repoData = MutableLiveData<Response<List<Repo>>>()

    fun getUser(username: String) {
        GlobalScope.launch {
            val request = githubService.getUser(username)
            val result = request.await()
            userData.postValue(result)
        }
    }

    fun getRepos(username: String) {
        GlobalScope.launch {
            val request = githubService.getRepos(username)
            val result = request.await()
            repoData.postValue(result)
        }
    }
}
