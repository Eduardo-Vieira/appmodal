package com.br.appmodal.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.br.appmodal.model.UserRepo

@Dao
interface UserRepoDao {
    @Query("SELECT * from tb_user_repo order by page_num")
    fun getUserRepoLocal(): LiveData<List<UserRepo>?>

    @Query("SELECT * from tb_user_repo where name like :name")
    fun getUserRepoSearchLocal(name:String): LiveData<List<UserRepo>?>

    @Query("SELECT * from tb_user_repo where id = :id")
    fun getUserRepoLocalOne(id:Long): LiveData<UserRepo?>

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(userLocal: List<UserRepo>?)

    @Update (onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(userLocal: UserRepo)

    @Query("DELETE FROM tb_user_repo")
    suspend fun deleteAll()
}