package ec.edu.uisek.githubclient.services

import android.content.Context
import ec.edu.uisek.githubclient.services.AuthService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ec.edu.uisek.githubclient.BuildConfig

object RetrofitClient {

    private const val BASE_URL = "https://api.github.com/"
    private lateinit var authService: AuthService

    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    fun init(context: Context){
        authService = AuthService(context)
    }
    private val httpClient = OkHttpClient.Builder()
        .addInterceptor(logging)
        .addInterceptor { chain ->
            val token = authService.getToken() ?:""

            println("Token es vacío: ${token.isEmpty()}")

        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer $token")
            .header(name = "Cache-Control", value = "no-cache, no-store, must-revalidate")
            .header(name = "Pragma", value = "no-cache")
            .header(name = "Expires", value = "0")
            .build()
            chain.proceed(request)
        }
        .build()

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}