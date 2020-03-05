package com.br.appmodal.model

import com.google.gson.annotations.SerializedName

data class Commits (
    val sha:String,
    @SerializedName("node_id")
    val nodeId:String,
    val url:String
)