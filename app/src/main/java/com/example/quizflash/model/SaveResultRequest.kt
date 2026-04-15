package com.example.quizflash.model

data class SaveResultRequest(
    val userId: Int,
    val score: Int,
    val totalQuestions: Int
)