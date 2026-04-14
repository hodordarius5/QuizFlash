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
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.quizflash.model.Question
import com.example.quizflash.data.SampleData

@Composable
fun QuizScreen() {
    var selectedAnswer by remember { mutableStateOf<Int?>(null) }
    var score by remember { mutableIntStateOf(0) }
    var questionIndex by remember { mutableIntStateOf(0) }
    var quizFinished by remember { mutableStateOf(false) }

    val questions = SampleData.questions

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        if (!quizFinished) {
            val currentQuestion = questions[questionIndex]

            Text(
                text = "Întrebarea ${questionIndex + 1} / ${questions.size}",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = currentQuestion.text,
                style = MaterialTheme.typography.headlineSmall
            )

            Spacer(modifier = Modifier.height(24.dp))

            currentQuestion.answers.forEachIndexed { index, answer ->
                val isSelected = selectedAnswer == index

                if (isSelected) {
                    Button(
                        onClick = { selectedAnswer = index },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        Text(answer)
                    }
                } else {
                    OutlinedButton(
                        onClick = { selectedAnswer = index },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        Text(answer)
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    if (selectedAnswer == currentQuestion.correctIndex) {
                        score++
                    }

                    if (questionIndex < questions.size - 1) {
                        questionIndex++
                        selectedAnswer = null
                    } else {
                        quizFinished = true
                    }
                },
                enabled = selectedAnswer != null,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Înainte")
            }
        } else {
            Text(
                text = "Quiz terminat!",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Scor: $score / ${questions.size}",
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    questionIndex = 0
                    score = 0
                    selectedAnswer = null
                    quizFinished = false
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Restart Quiz")
            }
        }
    }
}