package com.example.chefschoice.network

import com.example.chefschoice.BuildConfig
import com.example.chefschoice.database.FavoriteEntity
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private const val BASE_URL = "https://api.spoonacular.com/"
private const val API_KEY = BuildConfig.SPOONACULAR_API_KEY

private val networkLoggingInterceptor =
    HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .client(OkHttpClient.Builder().addInterceptor(networkLoggingInterceptor).build())
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface ChefTestService {
    @GET("recipes/findByIngredients?number=15&ranking=1")
    suspend fun getPhotos(
        @Query("ingredients") ingredient: String,
        @Query("apiKey") apiKey: String = API_KEY
    ): List<FavoriteEntity>

    @GET("food/trivia/random")
    suspend fun getTrivia(
        @Query("apiKey") apiKey: String = API_KEY
    ): Trivia

    @GET("random")
    suspend fun getRandom(
        @Query("apiKey") apiKey: String = API_KEY
    ): RandomRecipe

    @GET("recipes/{id}/card")
    suspend fun getRecipe(
        @Path("id") recipeId: String,
        @Query("apiKey") apiKey: String = API_KEY
    ): RecipeCard

    @GET("recipes/complexSearch")
    suspend fun getPopular(
        @Query("apiKey") apiKey: String = API_KEY,
        @Query("cuisine") cuisine: String = "american",
        @Query("sort") sort: String = "popularity",
        @Query("number") number: Int = 5
    ): ComplexSearch

    @GET("recipes/complexSearch")
    suspend fun getDessert(
        @Query("apiKey") apiKey: String = API_KEY,
        @Query("type") type: String = "dessert",
        @Query("sort") sort: String = "random",
        @Query("number") number: Int = 5
    ): ComplexSearch

    @GET("recipes/complexSearch")
    suspend fun getQuick(
        @Query("apiKey") apiKey: String = API_KEY,
        @Query("maxReadyTime") maxReadyTime: Int = 30,
        @Query("sort") sort: String = "random",
        @Query("number") number: Int = 5
    ): ComplexSearch

    @GET("food/products/upc/{upc}/")
    suspend fun getBarcode(
        @Path("upc") upc: String,
        @Query("apiKey") apiKey: String = API_KEY
    ): Upc
}

object ChefApi {
    val retrofitService: ChefTestService by lazy {
        retrofit.create(ChefTestService::class.java)
    }
}

