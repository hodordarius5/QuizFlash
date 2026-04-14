package com.example.quizflash.model

data class Question(
    val id: Int,
    val text: String,
    val answers: List<String>,
    val correctIndex: Int
)