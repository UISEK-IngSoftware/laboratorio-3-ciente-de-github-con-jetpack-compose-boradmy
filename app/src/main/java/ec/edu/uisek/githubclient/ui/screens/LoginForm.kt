package ec.edu.uisek.githubclient.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ec.edu.uisek.githubclient.services.AuthService
import ec.edu.uisek.githubclient.ui.theme.GithubClientTheme

@Composable
fun LoginForm(
    onLoginSuccess: () -> Unit = {}
) {
    var context = LocalContext.current
    var authService = remember { AuthService(context) }


    var username by remember { mutableStateOf("") }
    var token by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Ingreso a cliente de Github",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // Campo usuario
        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Nombre de usuario") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            leadingIcon = {
                Icon(Icons.Filled.AccountBox, contentDescription = "Usuario")
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Campo token/contraseña (solo candado a la izquierda)
        OutlinedTextField(
            value = token,
            onValueChange = { token = it },
            label = { Text("Contraseña") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            visualTransformation = if (passwordVisible) VisualTransformation.None
            else PasswordVisualTransformation(),
            leadingIcon = {
                Icon(Icons.Filled.Lock, contentDescription = "Contraseña")
            }
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Botón ingresar
        Button(
            onClick = {
                authService.saveAuth(username, token)
                onLoginSuccess()
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = username.isNotBlank() && token.isNotBlank()
        ) {
            Icon(
                imageVector = Icons.Filled.CheckCircle,
                contentDescription = null,
                modifier = Modifier.padding(end = 8.dp)
            )
            Text(text = "Ingresar")
        }
    }
}
//run
@Preview(showBackground = true)
@Composable
fun LoginFormPreview() {
    GithubClientTheme {
        LoginForm()
    }
}
