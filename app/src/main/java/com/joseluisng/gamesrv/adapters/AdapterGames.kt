package com.joseluisng.gamesrv.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.joseluisng.gamesrv.R
import com.joseluisng.gamesrv.inflate
import com.joseluisng.gamesrv.listeners.RecyclerGameListener
import com.joseluisng.gamesrv.loadByResource
import com.joseluisng.gamesrv.models.GameModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.molde_game.view.*
import java.util.ArrayList

class AdapterGames(private val games: List<GameModel>, private val listener: RecyclerGameListener):  RecyclerView.Adapter<AdapterGames.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =  ViewHolder(parent.inflate(R.layout.molde_game))

    override fun onBindViewHolder(holder: ViewHolder, position: Int)  = holder.bind(games[position], listener)

    override fun getItemCount() = games.size


    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(game: GameModel, listener: RecyclerGameListener) = with(itemView){


            tvTitleGame.text = game.title
            //Picasso.get().load(game.image).resize(600,300).into(imageViewGame)
            imageViewGame.loadByResource(game.image)
            tvDescriptionGame.text = game.description
            tvFechaGame.text = game.created_at.toString()
            // Clicks Events
            buttonEdit.setOnClickListener { listener.onEdit(game, adapterPosition) }
            //Estamos utilizando los listener que es las funciones del interface para no encapsular la logica dentro del adaptador
            // Ya que el adaptador lo que tiene que hacer es resmplazar solo los valores
            // y cuando hagas un click es decir que el listener va a llamar a ese metodo
            buttonDelete.setOnClickListener { listener.onDelete(game, adapterPosition)}


        }

    }

}