package com.example.quizflash.network

import com.example.quizflash.model.AuthRequest
import com.example.quizflash.model.AuthResponse
import com.example.quizflash.model.Question
import com.example.quizflash.model.BasicResponse
import com.example.quizflash.model.QuizResult
import com.example.quizflash.model.SaveResultRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @GET("questions")
    suspend fun getQuestions(): List<Question>

    @POST("login")
    suspend fun login(@Body request: AuthRequest): Response<AuthResponse>

    @POST("register")
    suspend fun register(@Body request: AuthRequest): Response<AuthResponse>

    @POST("results")
    suspend fun saveResult(@Body request: SaveResultRequest): retrofit2.Response<BasicResponse>

    @GET("results/{userId}")
    suspend fun getResults(@Path("userId") userId: Int): List<QuizResult>
}