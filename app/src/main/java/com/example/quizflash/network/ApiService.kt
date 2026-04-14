package com.example.quizflash.network

import com.example.quizflash.model.Question
import retrofit2.http.GET

interface ApiService {

    @GET("questions")
    suspend fun getQuestions(): List<Question>
}