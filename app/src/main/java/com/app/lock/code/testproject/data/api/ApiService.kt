package com.app.lock.code.testproject.data.api

import com.google.gson.JsonArray
import retrofit2.http.GET

interface APIService {

    @GET("posts")
    suspend fun getPosts(): JsonArray
}