package com.example.quizflash.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.quizflash.model.AuthRequest
import com.example.quizflash.network.RetrofitInstance
import kotlinx.coroutines.launch
import androidx.compose.runtime.rememberCoroutineScope
import com.example.quizflash.session.SessionManager

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onGoToRegisterClick: () -> Unit
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    var loading by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Login",
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = username,
            onValueChange = {
                username = it
                errorMessage = ""
            },
            label = { Text("Username") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                errorMessage = ""
            },
            label = { Text("Parola") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            visualTransformation = PasswordVisualTransformation()
        )

        if (errorMessage.isNotEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = errorMessage,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                scope.launch {
                    loading = true
                    errorMessage = ""

                    try {
                        val response = RetrofitInstance.api.login(
                            AuthRequest(username = username, password = password)
                        )

                        if (response.isSuccessful) {
                            val body = response.body()

                            if (body != null && body.success) {
                                SessionManager.userId = body.userId
                                SessionManager.username = body.username
                                onLoginSuccess()
                            } else {
                                errorMessage = body?.message ?: "Login eșuat."
                            }
                        } else {
                            val errorBody = response.errorBody()?.string()
                            errorMessage = if (!errorBody.isNullOrBlank()) {
                                if (errorBody.contains("Username sau parola invalide")) {
                                    "Invalid username or password."
                                } else {
                                    "Login eșuat."
                                }
                            } else {
                                "Login eșuat."
                            }
                        }
                    } catch (e: Exception) {
                        errorMessage = "Nu se poate conecta la server."
                        e.printStackTrace()
                    }

                    loading = false
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = username.isNotBlank() && password.isNotBlank() && !loading
        ) {
            if (loading) {
                CircularProgressIndicator()
            } else {
                Text("Login")
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedButton(
            onClick = onGoToRegisterClick,
            modifier = Modifier.fillMaxWidth(),
            enabled = !loading
        ) {
            Text("Înregistrează-te")
        }
    }
}