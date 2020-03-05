package com.br.appmodal.repository.local

import com.br.appmodal.database.dao.PagesDao
import com.br.appmodal.database.dao.ReadmeDao
import com.br.appmodal.database.dao.UserRepoDao
import com.br.appmodal.model.Pages
import com.br.appmodal.model.Readme
import com.br.appmodal.model.UserRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RepoLocal(
    private val userRepoDao: UserRepoDao,
    private val readmeDao: ReadmeDao,
    private val pagesDao: PagesDao
) {

    fun getUserRepoLocal() = userRepoDao.getUserRepoLocal()

    fun getUserRepoSearchLocal(name:String) = userRepoDao.getUserRepoSearchLocal(name)

    fun getUserRepoLocalOne(id:Long) = userRepoDao.getUserRepoLocalOne(id)

    fun getReadmeLocalOne(id:Long) = readmeDao.getReadmeLocalOne(id)

    suspend fun getPageLocalOne(router:String): Pages? {
        return withContext(Dispatchers.IO) {
            pagesDao.getPageLocalOne(router)
        }
    }

    suspend fun saveReadme(data:Readme?){
        withContext(Dispatchers.IO){
            readmeDao.insert(data)
        }
    }
    suspend fun saveUserRepo(data: List<UserRepo>?) {
        withContext(Dispatchers.IO) {
            userRepoDao.insertAll(data)
        }
    }

    suspend fun saveUserRepo(data: UserRepo) {
        withContext(Dispatchers.IO) {
            userRepoDao.update(data)
        }
    }

    suspend fun savePage(data:Pages){
        withContext(Dispatchers.IO) {
            pagesDao.insert(data)
        }
    }
}