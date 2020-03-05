package com.br.appmodal.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.br.appmodal.model.Pages
import com.br.appmodal.model.Readme
import com.br.appmodal.model.UserRepo
import com.br.appmodal.repository.local.RepoLocal
import com.br.appmodal.repository.remote.RepoRemote
import kotlinx.coroutines.*

class RepoRepository(
    private val remote: RepoRemote,
    private val local: RepoLocal
) {
    private val repositoryJob = Job()
    private val coroutineScope = CoroutineScope(repositoryJob + Dispatchers.Main)

    private val  mediatorUserRepo = MediatorLiveData<Resource<List<UserRepo>?>>()

    private val  mediatorDetail = MediatorLiveData<Resource<UserRepo?>>()

    private val  mediatorReadme = MediatorLiveData<Resource<Readme?>>()

    private var pageNext:Int = 1

    private var pageLast:Int = 0

    fun userRepo() =  mediatorUserRepo

    fun getReadme(id:Long): LiveData<Resource<Readme?>>{
        // Fazer busca interna no banco
         mediatorReadme.addSource(readmeLocalOne(id)) { dataSuccess ->
             mediatorReadme.value = Resource(data = dataSuccess)
        }
        return  mediatorReadme
    }

    fun getUserRepoSearch(name:String){
        // Fazer busca interna no banco
        mediatorUserRepo.addSource(getUserRepoSearchLocal(name)) { dataSuccess ->
            mediatorUserRepo.value = Resource(data = dataSuccess)
        }
    }

    fun getUserRepo(isRefrash:Boolean = false){
        getNextPage()
        // Verificar ser é uma atualização
        val page = if(isRefrash) 1 else pageNext

        // Fazer busca interna no banco
        mediatorUserRepo.addSource(getUserRepoLocal()) { dataSuccess ->
            mediatorUserRepo.value = Resource(data = dataSuccess)
        }

        // Fazer atualização de dados que vem da api
        val failureLiveData = MutableLiveData<Resource<List<UserRepo>?>>()
        mediatorUserRepo.addSource(failureLiveData) { resourceFailure ->
            val resourceAtual =  mediatorUserRepo.value
            val resourceNovo: Resource<List<UserRepo>?> = if(resourceAtual != null){
                Resource(data = resourceAtual.data, erro = resourceFailure.erro)
            } else {
                resourceFailure
            }
            mediatorUserRepo.value = resourceNovo
        }

        // Get na API
        getUserRepoApi(
            page,
            failure = { erro ->
                failureLiveData.value = Resource(data = null, erro = erro)
            })
    }

    fun getUserRepoDetail(id:Long): LiveData<Resource<UserRepo?>>{
        // Fazer busca interna no banco
         mediatorDetail.addSource(getUserRepoLocalOne(id)) { dataSuccess ->
            summaryUserRepoApi(dataSuccess)
             mediatorDetail.value = Resource(data = dataSuccess)
        }

        return  mediatorDetail
    }

    private fun summaryUserRepoApi(userRepo: UserRepo?){
        coroutineScope.launch {
            userRepo?.let { data ->
                val itemId = data.id!!
                val owner = data.fullName!!.split('/')[0]
                val repo = data.fullName!!.split('/')[1]
                        getReadmeApi(itemId, owner, repo)
                        data.commitsCount = remote.getCommitsCount(owner, repo)
                            .body()?.size?.toLong()
                        data.releasesCount = remote.getReleasesCount(owner, repo)
                            .body()?.size?.toLong()
                        data.collaboratorsCount = remote.getCollaboratorsCount(owner, repo)
                            .body()?.size?.toLong()
                        data.branchesCount = remote.getBranchesCount(owner, repo)
                            .body()?.size?.toLong()
                // update row UserRepo
                local.saveUserRepo(data)
            }
        }
    }

    private fun getReadmeApi(
        id:Long,
        owner:String,
        repo:String){
        coroutineScope.launch {
            try {
                val result = remote.getReadme(owner, repo)
                if (result.isSuccessful) {
                    var readme = result.body()
                    readme?.id = id
                    //save local db
                    local.saveReadme(readme)
                }
            }catch (e: Throwable){
                Log.e("Erro Readme","UserRepo: ${e.message}")
            }
        }
    }

    private fun getUserRepoApi(
        page:Int,
        failure: (erro: String?) -> Unit
    ){
        coroutineScope.launch {
            try {
                val result = remote.getUserRepo(page)
                if (result.isSuccessful) {
                    getHeader(result.headers().get("Link"))
                    val userRepo = result.body()
                    //seta Pagina atual
                    userRepo?.let {
                        it.forEach { data ->
                            data.page = page.toLong()
                        }
                        //save local db
                        local.saveUserRepo(userRepo)
                    }
                } else {
                    failure(result.errorBody().toString())
                }
            }catch (e: Throwable){
                failure("UserRepo, ${e.message}")
            }
        }
    }

    private fun getNextPage(){
        coroutineScope.launch {
            val page = local.getPageLocalOne("userRepo")
            page?.next?.let { pageNext = it }
            page?.last?.let { pageLast = it }
        }
    }

    private suspend fun getHeader(link:String?) {
         link?.let {
            val itens = it.split(",")
            val page = Pages("userRepo")
            if(itens.size > 2) {
                page.next = itens[1].replace(Regex("\\D"), "").toInt()
                page.last = itens[2].replace(Regex("\\D"), "").toInt()
            }else{
                page.next = itens[0].replace(Regex("\\D"), "").toInt()
            }
            local.savePage(page)
        }
    }

    private fun getUserRepoLocalOne(id:Long) = local.getUserRepoLocalOne(id)

    private fun getUserRepoLocal() = local.getUserRepoLocal()

    private fun getUserRepoSearchLocal(name:String) = local.getUserRepoSearchLocal(name)

    private fun readmeLocalOne(id:Long) = local.getReadmeLocalOne(id)
}
