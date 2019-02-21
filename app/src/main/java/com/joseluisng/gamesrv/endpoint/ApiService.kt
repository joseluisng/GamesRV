package com.joseluisng.gamesrv.endpoint

import com.joseluisng.gamesrv.models.GameModel
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("/api/games")
    fun getAllGames(): Call<List<GameModel>>

    @GET("/api/games/{id}")
    fun getOneGame(@Path("id") id: Int): Call<GameModel>

    @POST("/api/games")
    fun createGame(@Body game: GameModel?): Call<GameModel>

    @PUT("/api/games/{id}")
    fun editGame(@Path("id") id: Int, @Body game: GameModel?): Call<GameModel>

    @DELETE("/api/games/{id}")
    fun deleteGame(@Path("id") id: Int): Call<GameModel>


}