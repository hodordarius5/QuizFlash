package com.example.quizflash.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun FlashcardScreen() {
    var cardIndex by remember { mutableIntStateOf(0) }
    var showAnswer by remember { mutableStateOf(false) }
    var finished by remember { mutableStateOf(false) }

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
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        if (!finished) {
            val currentCard = questions[cardIndex]
            val correctAnswer = currentCard.answers[currentCard.correctIndex]

            Text(
                text = "Flashcard ${cardIndex + 1} / ${questions.size}",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = currentCard.text,
                style = MaterialTheme.typography.headlineSmall
            )

            Spacer(modifier = Modifier.height(24.dp))

            if (showAnswer) {
                Text(
                    text = "Răspuns: $correctAnswer",
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        if (cardIndex < questions.size - 1) {
                            cardIndex++
                            showAnswer = false
                        } else {
                            finished = true
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Următorul card")
                }
            } else {
                Button(
                    onClick = { showAnswer = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Vezi răspuns")
                }
            }
        } else {
            Text(
                text = "Flashcard-uri terminate!",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Ai revizuit ${questions.size} carduri.",
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    cardIndex = 0
                    showAnswer = false
                    finished = false
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Restart Flashcards")
            }
        }
    }
}