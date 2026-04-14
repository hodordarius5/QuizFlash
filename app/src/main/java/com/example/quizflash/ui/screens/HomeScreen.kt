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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(
    onQuizClick: () -> Unit,
    onFlashcardsClick: () -> Unit,
    onResultsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "QuizFlash",
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Învață cu quiz-uri și flashcard-uri",
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = onQuizClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Quiz")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onFlashcardsClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Flashcards")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onResultsClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Results")
        }
    }
}