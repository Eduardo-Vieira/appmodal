package com.br.appmodal.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.br.appmodal.model.Readme

@Dao
interface ReadmeDao {

    @Query("SELECT * from tb_repo_readme where id = :id")
    fun getReadmeLocalOne(id:Long): LiveData<Readme?>

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(readme: Readme?)
}