package com.testbook.githubpr.network

import com.testbook.githubpr.models.Repo
import com.testbook.githubpr.models.User
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubService {

    @GET("users/{username}")
    fun getUser(@Path("username") username: String): Deferred<Response<User>>

    @GET("users/{username}/repos")
    fun getRepos(@Path("username") username: String): Deferred<Response<List<Repo>>>

}