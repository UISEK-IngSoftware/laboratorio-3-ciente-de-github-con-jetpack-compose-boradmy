package ec.edu.uisek.githubclient.services

import ec.edu.uisek.githubclient.models.Repository
import retrofit2.http.GET

interface ApiService {

    @GET(value = "user/repos")
    suspend fun getRepositories(): List<Repository>
}
