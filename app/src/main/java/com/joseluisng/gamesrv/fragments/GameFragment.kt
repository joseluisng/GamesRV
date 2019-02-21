package com.joseluisng.gamesrv.fragments


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import com.joseluisng.gamesrv.AddGame

import com.joseluisng.gamesrv.R
import com.joseluisng.gamesrv.adapters.AdapterGames
import com.joseluisng.gamesrv.endpoint.ApiService
import com.joseluisng.gamesrv.listeners.RecyclerGameListener
import com.joseluisng.gamesrv.models.GameModel
import com.joseluisng.gamesrv.toast
import kotlinx.android.synthetic.main.fragment_game.*
import kotlinx.android.synthetic.main.fragment_game.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GameFragment : Fragment() {

    var listGame = ArrayList<GameModel>()

    private lateinit var recycler: RecyclerView
    private lateinit var adapter: AdapterGames

    private lateinit var mHandler: Handler
    private lateinit var mRunnable:Runnable
    //private val layoutManager = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val miVista = inflater.inflate(R.layout.fragment_game, container, false)

        recycler = miVista.recyclerView as RecyclerView

        getGame()

        miVista.fab.setOnClickListener {
            val intent = Intent(context, AddGame::class.java)
            startActivity(intent)
        }

        mHandler = Handler()
        // Refrescar la lista deslizando hacia abajo por si se realizaron cambios
        miVista.swipe_refresh_layout.setOnRefreshListener {
            mRunnable = Runnable {

                getGame()

                swipe_refresh_layout.isRefreshing = false
            }

            mHandler.postDelayed(mRunnable, 2000)
        }

        return miVista
    }

    override fun onResume() {
        super.onResume()

        getGame()

    }

    fun getGame(){

        val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl("http://192.168.0.103:3000")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        val service = retrofit.create<ApiService>(ApiService::class.java)

        service.getAllGames().enqueue(object : Callback<List<GameModel>> {

            override fun onResponse(call: Call<List<GameModel>>, response: Response<List<GameModel>>) {

                if (response.isSuccessful){
                    listGame = response.body() as ArrayList<GameModel>
                    setRecyclerView()
                    //listGame.addAll(response.body()!!)
                    //Log.e("Games: ", listGame.toString())
                   //adapter.notifyDataSetChanged()

                }else{
                    activity?.toast("Algo fallo, revise su conexión a internet")
                }
            }

            override fun onFailure(call: Call<List<GameModel>>, t: Throwable) {
                t.printStackTrace()
                Log.e("Games: ", t.toString())
            }

        })

    }

    fun setRecyclerView(){

        recycler.setHasFixedSize(true)
        recycler.itemAnimator = DefaultItemAnimator() as RecyclerView.ItemAnimator?
        recycler.layoutManager = LinearLayoutManager(context)
        adapter = (AdapterGames(listGame, object : RecyclerGameListener{
            override fun onEdit(game: GameModel, position: Int) {
                //activity?.toast("Edit  ${game.title}")
                val intent = Intent(context, AddGame::class.java)
                intent.putExtra("id", game.id)
                intent.putExtra("title", game.title)
                intent.putExtra("description", game.description )
                intent.putExtra("urlImage", game.image)
                startActivity(intent)


            }

            override fun onDelete(game: GameModel, position: Int) {
                val retrofit: Retrofit = Retrofit.Builder()
                        .baseUrl("http://192.168.0.103:3000")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                val service = retrofit.create<ApiService>(ApiService::class.java)
                val gameId = game.id
                service.deleteGame(gameId!!).enqueue(object : Callback<GameModel> {
                    override fun onFailure(call: Call<GameModel>, t: Throwable) {
                        t.printStackTrace()
                        activity?.toast("Algo paso, no se pudo eliminar")
                    }

                    override fun onResponse(call: Call<GameModel>, response: Response<GameModel>) {
                        val g = response.body()
                        Log.e("Game: ", Gson().toJson(g))

                        listGame.remove(game)
                        adapter.notifyItemRemoved(position)
                        activity?.toast("Delete  ${game.title}")
                        //getGameRecyclerView()
                    }

                })
            }

        }))
        recycler.adapter = adapter

    }


    //Función para llenar la lista al estilo kotlin
   /* private fun getFlights(): ArrayList<GameModel>{
        return object: ArrayList<GameModel>(){
            init {
                add(GameModel(1, "COD", "Black Ops 3", "https://upload.wikimedia.org/wikipedia/en/thumb/b/b1/Black_Ops_3.jpg/220px-Black_Ops_3.jpg", null))
                add(GameModel(2, "Apex Legends", "Game online", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSmZJlUB87ISQOPCNg8hLbDzHmHbmrfDr-LzJ7YZVs1dBYmL5a7", null))
            }
        }

    }*/


    // Función para llenar una lista al estilo java
    /*
    private fun getFlights(): ArrayList<Flight>{
        val list = ArrayList<Flight>()
        list.add(Flight(1, "Seatle", R.drawable.seatle))
        list.add(Flight(2, "London", R.drawable.london))

        return list
    }
    */


}
