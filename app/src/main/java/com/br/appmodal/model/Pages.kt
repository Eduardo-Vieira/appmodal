package com.br.appmodal.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tb_pages")
data class Pages(
    var router:String,
    var next:Int? = 0,
    var last:Int? = 0,
    @PrimaryKey(autoGenerate = true)
    var id:Long? = 0L
)
