package com.example.majong

import android.app.Activity
import android.os.Bundle

import com.example.majong.view.GameView
import com.example.majong.view.MahjongTile


class MainActivity : Activity() {
    private lateinit var gameView: GameView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        gameView = GameView(this)
        setContentView(gameView)

    }


    override fun onResume() {
        super.onResume()

    }


}