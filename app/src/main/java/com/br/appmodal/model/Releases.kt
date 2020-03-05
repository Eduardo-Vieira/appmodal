package com.br.appmodal.model

import com.google.gson.annotations.SerializedName

data class Releases(
    val id:Long,
    val url:String,
    @SerializedName("tag_name")
    val tagName:String
)