package com.example.quizflash.data

import com.example.quizflash.model.Question

object SampleData {

    val questions = listOf(
        Question(
            text = "Capitala Franței?",
            answers = listOf("Berlin", "Madrid", "Paris", "Roma"),
            correctIndex = 2
        ),
        Question(
            text = "2 + 2 = ?",
            answers = listOf("3", "4", "5", "6"),
            correctIndex = 1
        ),
        Question(
            text = "Capitala Italiei?",
            answers = listOf("Roma", "Paris", "Lisabona", "Viena"),
            correctIndex = 0
        )
    )
}