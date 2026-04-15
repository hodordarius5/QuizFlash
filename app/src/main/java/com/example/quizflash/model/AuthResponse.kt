package com.example.quizflash.model

data class AuthResponse(
    val success: Boolean,
    val message: String,
    val userId: Int? = null,
    val username: String? = null
)