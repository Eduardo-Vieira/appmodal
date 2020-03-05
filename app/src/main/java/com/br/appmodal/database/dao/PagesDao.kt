package com.br.appmodal.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.br.appmodal.model.Pages

@Dao
interface PagesDao {
    @Query("SELECT * from tb_pages where router = :router")
    fun getPageLocalOne(router:String): Pages?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(pages: Pages?)
}