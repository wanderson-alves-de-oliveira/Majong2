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

    init {
        holder.addCallback(this)
    }


    override fun surfaceCreated(holder: SurfaceHolder) {
        try {
            tiles = gameLoop.tiles
            selectedTiles = gameLoop.selectedTiles
        }catch (_:Exception){}

        gameLoop = GameLoop(holder, context)
        if (this.tiles.isNotEmpty()) {
            gameLoop.tiles = this.tiles
            gameLoop.selectedTiles = this.selectedTiles
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