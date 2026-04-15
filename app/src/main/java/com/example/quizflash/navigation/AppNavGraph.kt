package com.example.quizflash.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.quizflash.ui.screens.FlashcardScreen
import com.example.quizflash.ui.screens.HomeScreen
import com.example.quizflash.ui.screens.QuizScreen
import com.example.quizflash.ui.screens.ResultsScreen
import com.example.quizflash.ui.screens.LoginScreen
import com.example.quizflash.ui.screens.RegisterScreen

@Composable
fun AppNavGraph(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "login",
        modifier = modifier
    ) {
        composable("login") {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate("home") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onGoToRegisterClick = {
                    navController.navigate("register")
                }
            )
        }

        composable("register") {
            RegisterScreen(
                onRegisterSuccess = {
                    navController.navigate("home") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onBackToLoginClick = {
                    navController.popBackStack()
                }
            )
        }

        composable("home") {
            HomeScreen(
                onQuizClick = { navController.navigate("quiz") },
                onFlashcardsClick = { navController.navigate("flashcards") },
                onResultsClick = { navController.navigate("results") }
            )
        }

        composable("quiz") {
            QuizScreen()
        }

        composable("flashcards") {
            FlashcardScreen()
        }

        composable("results") {
            ResultsScreen()
        }
    }
}