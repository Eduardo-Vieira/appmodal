package com.br.appmodal.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.br.appmodal.utils.decodeBase64
import com.google.gson.annotations.SerializedName

@Entity(tableName = "tb_repo_readme")
data class Readme(
    @PrimaryKey
    @SerializedName("id")           var id:Long? = 0L,
    @SerializedName("sha")          var sha:String? = null,
    @SerializedName("type")         var type:String? = null,
    @SerializedName("content")      var content:String? = null,
    @SerializedName("encoding")     var encoding:String? = null
){
    fun contentString(): String? {
        return this.content?.decodeBase64()
    }


}
