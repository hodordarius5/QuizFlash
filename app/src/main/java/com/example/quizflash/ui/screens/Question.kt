package com.example.quizflash.ui.screens

data class Question(
    val text: String,
    val answers: List<String>,
    val correctIndex: Int
)
