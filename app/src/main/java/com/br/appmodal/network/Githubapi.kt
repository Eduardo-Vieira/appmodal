package com.br.appmodal.network

import com.br.appmodal.model.*
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Githubapi {
    @GET("/user/repos")
    suspend fun userRepo(@Query("page") page:Int=1):Response<List<UserRepo>>

    @GET("/repos/{owner}/{repo}/readme")
    suspend fun getReadme(@Path("owner") owner:String, @Path("repo") repo:String): Response<Readme>

    @GET("/repos/{owner}/{repo}/commits")
    suspend fun getCommitsCount(@Path("owner") owner:String, @Path("repo") repo:String): Response<List<Commits>?>

    @GET("/repos/{owner}/{repo}/releases")
    suspend fun getReleasesCount(@Path("owner") owner:String, @Path("repo") repo:String): Response<List<Releases>?>

    @GET("/repos/{owner}/{repo}/collaborators")
    suspend fun getCollaboratorsCount(@Path("owner") owner:String, @Path("repo") repo:String): Response<List<Collaborators>?>

    @GET("/repos/{owner}/{repo}/branches")
    suspend fun getBranchesCount(@Path("owner") owner:String, @Path("repo") repo:String): Response<List<Branches>?>

}