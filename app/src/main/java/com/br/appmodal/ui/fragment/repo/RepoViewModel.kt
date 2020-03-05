package com.br.appmodal.ui.fragment.repo

import androidx.lifecycle.ViewModel
import com.br.appmodal.repository.RepoRepository

class RepoViewModel(
    private val repository: RepoRepository
):ViewModel() {

    fun userRepo() = repository.userRepo()

    fun getUserRepo() = repository.getUserRepo()

    fun getUserRepoSearch(name:String) = repository.getUserRepoSearch(name)

    fun getUserRepoRefresh() = repository.getUserRepo(true)
}