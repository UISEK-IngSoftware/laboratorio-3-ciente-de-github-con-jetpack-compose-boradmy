package ec.edu.uisek.githubclient.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import ec.edu.uisek.githubclient.models.Repository
import ec.edu.uisek.githubclient.ui.theme.GithubClientTheme
import ec.edu.uisek.githubclient.viewmodels.RepoFormViewModel
import ec.edu.uisek.githubclient.viewmodels.RepoListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RepoForm(
    repositoryToEdit: Repository? = null,
    onBackClick: () -> Unit = {},
    repoListViewModel: RepoListViewModel = viewModel(),
    viewModel: RepoFormViewModel = viewModel(),
    onSaveSuccess: () -> Unit = {}
) {
    val isLoading by viewModel.isLoading.collectAsState()
    val isSuccess by viewModel.isSuccess.collectAsState()
    val errorMsg by viewModel.errorMsg.collectAsState()

    val isEditMode = repositoryToEdit != null

    var name by remember { mutableStateOf(repositoryToEdit?.name ?: "") }
    var description by remember { mutableStateOf(repositoryToEdit?.description ?: "") }

    LaunchedEffect(isSuccess) {
        if (isSuccess) {
            // refrescar lista al guardar/actualizar
            repoListViewModel.fetchRepos()
            onSaveSuccess()
            onBackClick()
            viewModel.resetSuccess()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(if (isEditMode) "Editar Repositorio" else "Nuevo Repositorio")
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Regresar"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Nombre del repositorio") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Descripción del repositorio (opcional)") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (isEditMode) {
                        viewModel.updateRepo(
                            oldName = repositoryToEdit.name,
                            name = name,
                            description = description
                        )
                    } else {
                        viewModel.createRepo(
                            name = name,
                            description = description
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = name.isNotBlank() && !isLoading
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.width(24.dp),
                        strokeWidth = 2.dp
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(if (isEditMode) "Actualizando..." else "Guardando...")
                } else {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Guardar"
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = if (isEditMode) "Actualizar" else "Guardar",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }

            if (!errorMsg.isNullOrBlank()) {
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = errorMsg ?: "",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RepoFormPreview() {
    GithubClientTheme {
        RepoForm(
            repositoryToEdit = null,
            onBackClick = {},
            onSaveSuccess = {}
        )
    }
}
