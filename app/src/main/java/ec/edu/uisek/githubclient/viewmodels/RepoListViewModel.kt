package ec.edu.uisek.githubclient.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ec.edu.uisek.githubclient.models.Repository
import ec.edu.uisek.githubclient.services.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RepoListViewModel : ViewModel() {

    private val apiService = RetrofitClient.apiService

    private val _repos = MutableStateFlow<List<Repository>>(emptyList())
    val repos: StateFlow<List<Repository>> = _repos.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMsg = MutableStateFlow<String?>(null)
    val errorMsg: StateFlow<String?> = _errorMsg.asStateFlow()

    init {
        fetchRepos()
    }

    fun fetchRepos() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMsg.value = null
            try {
                _repos.value = apiService.getRepositories()
            } catch (e: Exception) {
                _errorMsg.value = "Error al cargar repositorios: ${e.localizedMessage}"
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun deleteRepo(name: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMsg.value = null
            try {
                val userResponse = apiService.getAuthenticatedUser()
                if (userResponse.isSuccessful) {
                    val owner = userResponse.body()?.login
                        ?: throw Exception("No se pudo obtener el usuario autenticado")

                    val response = apiService.deleteRepository(owner, name)
                    if (response.isSuccessful) {
                        fetchRepos()
                    } else {
                        _errorMsg.value = "Error al eliminar: ${response.code()} ${response.message()}"
                    }
                } else {
                    _errorMsg.value = "Error al obtener usuario: ${userResponse.code()} ${userResponse.message()}"
                }
            } catch (e: Exception) {
                _errorMsg.value = "Error al eliminar repositorio: ${e.localizedMessage}"
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }
}
