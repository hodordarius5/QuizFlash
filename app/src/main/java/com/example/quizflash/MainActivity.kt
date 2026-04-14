package com.example.quizflash

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.quizflash.navigation.AppNavGraph
import com.example.quizflash.ui.theme.QuizFlashTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            QuizFlashTheme {
                AppNavGraph()
            }
        }
    }
}