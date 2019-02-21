package com.joseluisng.gamesrv.listeners

import com.joseluisng.gamesrv.models.GameModel

interface RecyclerGameListener {
    fun onEdit(game: GameModel, position: Int)
    fun onDelete(game: GameModel, position: Int)
}