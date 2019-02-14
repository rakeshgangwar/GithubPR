package com.testbook.githubpr.network

import javax.inject.Inject

class GithubRepository @Inject constructor(
    private val githubService: GithubService
)