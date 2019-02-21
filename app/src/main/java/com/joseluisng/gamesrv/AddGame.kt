package com.joseluisng.gamesrv

import android.app.Activity
import android.os.Bundle
import android.util.Log
import com.google.gson.Gson
import com.joseluisng.gamesrv.models.GameModel
import kotlinx.android.synthetic.main.activity_add_game.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddGame : Activity() {

    var id = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_game)


        //Recibiendo los datos para editar desde el GameFragment
        try {
            val bundle: Bundle = intent.extras!!
            id = bundle.getInt("id", 0)
            if (id != 0){
                etTituloGame.setText(bundle.getString("title"))
                etDescriptionGame.setText(bundle.getString("description"))
                urlImage.setText(bundle.getString("urlImage"))
            }

        }catch (e: Exception){

        }


        btnAgregarGame.setOnClickListener {

            val tituloGame = etTituloGame.text.toString()
            val descriptionGame = etDescriptionGame.text.toString()
            val urlImageGame = urlImage.text.toString()

            //Comparando que los campos de texto no vengan vacios
            if(!tituloGame.isEmpty() && !descriptionGame.isEmpty() && !urlImageGame.isEmpty()){

                if(id == 0){
                    AgregarGame(tituloGame, descriptionGame, urlImageGame)
                }else{
                    actualizarGame(id, tituloGame, descriptionGame, urlImageGame)
                }


            }else{
                toast("Todos los campos deben de ser llenados")
            }

        }



    }

    fun AgregarGame(tituloGame: String, descGame: String, imgGame: String ){

        /*val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl("http://192.168.0.103:3000")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        val service = retrofit.create<ApiService>(ApiService::class.java)*/

        var game : GameModel = GameModel(null, tituloGame, descGame, imgGame, null)
        service.createGame(game).enqueue(object : Callback<GameModel> {
            override fun onResponse(call: Call<GameModel>, response: Response<GameModel>) {
                game = response.body()!!
                Log.e("Game: ", Gson().toJson(game))
                //goToActivity<MainActivity>()
                toast("Se agrego correctamente")
                finish()
            }

            override fun onFailure(call: Call<GameModel>, t: Throwable) {
                t.printStackTrace()
                Log.e("Fallo Game: ", t.toString())
                toast("Lo sentimos algo salio mal, revise su conexión a internet")
            }


        })

    }

    fun actualizarGame(id: Int, title: String, description: String, urlImage: String ){

        var game: GameModel = GameModel(null, title, description, urlImage, null)
        service.editGame(id, game).enqueue(object : Callback<GameModel> {
            override fun onResponse(call: Call<GameModel>, response: Response<GameModel>) {
                game = response.body()!!
                Log.e("Game: ", Gson().toJson(game))
                //goToActivity<MainActivity>()
                toast("Se Actualizo correctamente")
                finish()
            }

            override fun onFailure(call: Call<GameModel>, t: Throwable) {
                t.printStackTrace()
                Log.e("Fallo Game: ", t.toString())
                toast("Lo sentimos algo salio mal, revise su conexión a internet")
            }


        })

    }



}
