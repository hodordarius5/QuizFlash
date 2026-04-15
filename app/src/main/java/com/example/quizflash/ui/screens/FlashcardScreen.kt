package com.example.quizflash.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.quizflash.model.Question
import com.example.quizflash.network.RetrofitInstance

@Composable
fun FlashcardScreen() {
    var cardIndex by remember { mutableIntStateOf(0) }
    var showAnswer by remember { mutableStateOf(false) }
    var finished by remember { mutableStateOf(false) }

    var questions by remember { mutableStateOf<List<Question>>(emptyList()) }
    var loading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        try {
            questions = RetrofitInstance.api.getQuestions()
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("FlashcardScreen", "Error loading questions", e)
        }
        loading = false
    }

    if (loading) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()
        }
        return
    }

    if (questions.isEmpty()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Nu s-au încărcat flashcard-urile")
        }
        return
    }

    if (cardIndex !in questions.indices) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Index card invalid")
        }
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (!finished) {
            val currentCard = questions[cardIndex]
            val correctAnswer = currentCard.answers[currentCard.correctIndex]

            Text(
                text = "Flashcard ${cardIndex + 1} / ${questions.size}",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(24.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .sizeIn(minHeight = 220.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp)
                        .sizeIn(minHeight = 220.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = if (!showAnswer) currentCard.text else correctAnswer,
                        style = MaterialTheme.typography.headlineSmall
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    if (!showAnswer) {
                        showAnswer = true
                    } else {
                        if (cardIndex < questions.size - 1) {
                            cardIndex++
                            showAnswer = false
                        } else {
                            finished = true
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (!showAnswer) "Vezi răspunsul" else "Înainte")
            }
        } else {
            Text(
                text = "Flashcarduri terminate!",
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