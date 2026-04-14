package com.example.quizflash.model

data class Question(
    val text: String,
    val answers: List<String>,
    val correctIndex: Int
)