package com.example.quizflash.model

data class QuizResult(
    val id: Int,
    val userId: Int,
    val score: Int,
    val totalQuestions: Int,
    val createdAt: String
)