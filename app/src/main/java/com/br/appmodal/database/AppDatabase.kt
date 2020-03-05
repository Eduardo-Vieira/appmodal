package com.br.appmodal.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.br.appmodal.database.dao.PagesDao
import com.br.appmodal.database.dao.ReadmeDao
import com.br.appmodal.database.dao.UserRepoDao
import com.br.appmodal.model.Pages
import com.br.appmodal.model.Readme
import com.br.appmodal.model.UserRepo

@Database(entities = arrayOf(UserRepo::class, Readme::class, Pages::class), version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun userRepoDao(): UserRepoDao
    abstract fun readmeDao(): ReadmeDao
    abstract fun pages(): PagesDao
}