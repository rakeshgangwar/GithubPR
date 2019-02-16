package com.testbook.githubpr.network

import com.testbook.githubpr.models.Repo
import com.testbook.githubpr.models.User
import com.testbook.githubpr.models.pr.PullRequest
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubService {

    @GET("users/{username}")
    fun getUser(@Path("username") username: String): Deferred<Response<User>>

    @GET("users/{username}/repos")
    fun getRepos(@Path("username") username: String): Deferred<Response<List<Repo>>>

    @GET("repos/{username}/{repo_name}/pulls")
    fun getPullRequests(@Path("username") username: String, @Path("repo_name") repo_name: String):
            Deferred<Response<List<PullRequest>>>

}