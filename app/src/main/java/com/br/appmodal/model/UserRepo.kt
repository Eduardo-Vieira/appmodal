package com.br.appmodal.model

import androidx.room.*
import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat
import java.util.*
import kotlin.time.days

@Entity(tableName = "tb_user_repo")
data class UserRepo(
    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id")
    var id:Long? = 0L,

    @SerializedName("node_id")
    @ColumnInfo(name = "node_id")
    var nodeId:String? = null,

    @SerializedName("name")
    @ColumnInfo(name = "name")
    var name:String? = null,

    @SerializedName("full_name")
    @ColumnInfo(name = "full_name")
    var fullName:String? = null,

    @SerializedName("private")
    @ColumnInfo(name = "private")
    var private:Boolean = false,

    @SerializedName("description")
    @ColumnInfo(name = "description")
    var description:String? = null,

    @SerializedName("stargazers_count")
    @ColumnInfo(name = "stargazers_count")
    var stargazersCount:Long? = 0,

    @SerializedName("forks_count")
    @ColumnInfo(name = "forks_count")
    var forksCount:Long? = 0,

    @SerializedName("watchers_count")
    @ColumnInfo(name = "watchers_count")
    var watchersCount:Long? = 0,

    @SerializedName("watchers")
    @ColumnInfo(name = "watchers")
    var watchers:Long? = 0,

    @SerializedName("created_at")
    @ColumnInfo(name = "created_at")
    var createdAt:String? = null,

    @SerializedName("updated_at")
    @ColumnInfo(name = "updated_at")
    var updatedAt:String? = null,

    @SerializedName("pushed_at")
    @ColumnInfo(name = "pushed_at")
    var pushedAt:String? = null,

    @ColumnInfo(name = "commits_count")
    var commitsCount:Long? = 0,

    @ColumnInfo(name = "releases_count")
    var releasesCount:Long? = 0,

    @ColumnInfo(name = "collaborators_count")
    var collaboratorsCount:Long? = 0,

    @ColumnInfo(name = "branches_count")
    var branchesCount:Long? = 0,
    @ColumnInfo(name = "page_num")
    var page:Long? = 0
){
    fun pushedAtToDay(): String {
        val formated = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        val pushedAtTime = formated.parse(pushedAt!!).time
        val currentTime = Date().time
        val diff =  currentTime - pushedAtTime
        return "${ diff / (24 * 60 * 60 * 1000)} Dia(s)"

    }
}