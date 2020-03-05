package com.br.appmodal.ui.fragment.repo.detail

import androidx.lifecycle.ViewModel
import com.br.appmodal.repository.RepoRepository

class DetailViewModel(
    private val repository: RepoRepository
): ViewModel() {

    fun getUserRepoDetail(id:Long) = repository.getUserRepoDetail(id)

    fun getReadme(id: Long) = repository.getReadme(id)

}