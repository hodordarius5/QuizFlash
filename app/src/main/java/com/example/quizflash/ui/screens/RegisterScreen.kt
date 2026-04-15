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
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import com.example.quizflash.model.AuthRequest
import com.example.quizflash.network.RetrofitInstance
import kotlinx.coroutines.launch
import androidx.compose.runtime.rememberCoroutineScope

@Composable
fun RegisterScreen(
    onRegisterSuccess: () -> Unit,
    onBackToLoginClick: () -> Unit
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    var errorMessage by remember { mutableStateOf("") }
    var loading by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text("Înregistrează-te", style = MaterialTheme.typography.headlineLarge)

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = username,
            onValueChange = {
                username = it
                errorMessage = ""
            },
            label = { Text("Username") },
            modifier = Modifier.fillMaxWidth()
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
            visualTransformation = PasswordVisualTransformation()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = confirmPassword,
            onValueChange = {
                confirmPassword = it
                errorMessage = ""
            },
            label = { Text("Confirmă parola") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation()
        )

        if (confirmPassword.isNotEmpty() && password != confirmPassword) {
            Spacer(modifier = Modifier.height(8.dp))
            Text("Parolele nu coincid")
        }

        if (errorMessage.isNotEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(errorMessage)
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                if (password != confirmPassword) return@Button

                scope.launch {
                    loading = true
                    errorMessage = ""

                    try {
                        val response = RetrofitInstance.api.register(
                            AuthRequest(username, password)
                        )

                        if (response.isSuccessful) {
                            val body = response.body()

                            if (body != null && body.success) {
                                onRegisterSuccess()
                            } else {
                                errorMessage = body?.message ?: "Înregistrare eșuată."
                            }
                        } else {
                            val errorBody = response.errorBody()?.string()

                            errorMessage = if (!errorBody.isNullOrBlank()) {
                                if (errorBody.contains("Username already exists")) {
                                    "Username already exists."
                                } else {
                                    "Register failed."
                                }
                            } else {
                                "Register failed."
                            }
                        }
                    } catch (e: Exception) {
                        errorMessage = "Nu s-a putut conecta la server."
                        e.printStackTrace()
                    }

                    loading = false
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = username.isNotBlank() &&
                    password.isNotBlank() &&
                    confirmPassword.isNotBlank() &&
                    !loading
        ) {
            if (loading) {
                CircularProgressIndicator()
            } else {
                Text("Crează cont")
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedButton(
            onClick = onBackToLoginClick,
            modifier = Modifier.fillMaxWidth(),
            enabled = !loading
        ) {
            Text("Înapoi la Login")
        }
    }
}