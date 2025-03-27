package com.example.majong.view

import android.content.Context
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import com.example.majong.egine.GameLoop

class GameView(context: Context) : SurfaceView(context), SurfaceHolder.Callback {
    lateinit var gameLoop: GameLoop
    private var tiles = mutableListOf<MahjongTile>()
    private var selectedTiles = mutableListOf<MahjongTile>()
    private var pontos = 0
    private var index = 0

    init {
        holder.addCallback(this)
    }


    override fun surfaceCreated(holder: SurfaceHolder) {
        try {
            tiles = gameLoop.tiles
            pontos = gameLoop.pontos
            index = gameLoop.index

            selectedTiles = gameLoop.selectedTiles
            selectedTiles.removeAll(gameLoop.removerDaLista)
            gameLoop.removerDaLista.clear()

        }catch (_:Exception){}

        gameLoop = GameLoop(holder, context)
        if (this.tiles.isNotEmpty()) {
            gameLoop.tiles = this.tiles
            gameLoop.selectedTiles = this.selectedTiles
            gameLoop.pontos = this.pontos
            gameLoop.index = this.index
        }
        gameLoop.startLoop()
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {}
    override fun surfaceDestroyed(holder: SurfaceHolder) {
        gameLoop.stopLoop()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {

        gameLoop.onTouchEvent(event)
        return true
    }
}