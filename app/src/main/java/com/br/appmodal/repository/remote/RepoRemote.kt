package com.br.appmodal.repository.remote

import com.br.appmodal.network.Githubapi

class RepoRemote(
    private val githubapi: Githubapi
) {
    suspend fun getUserRepo(page:Int) = githubapi.userRepo(page)

    suspend fun getReadme(owner:String,repo:String) = githubapi.getReadme(owner,repo)

    suspend fun getCommitsCount(owner:String,repo:String) = githubapi.getCommitsCount(owner,repo)

    suspend fun getReleasesCount(owner:String,repo:String) = githubapi.getReleasesCount(owner,repo)

    suspend fun getCollaboratorsCount(owner:String,repo:String) = githubapi.getCollaboratorsCount(owner,repo)

    suspend fun getBranchesCount(owner:String,repo:String) = githubapi.getBranchesCount(owner,repo)
}