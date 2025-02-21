package com.example.majong

import android.app.Activity
import android.os.Bundle
import android.view.WindowManager

import com.example.majong.view.GameView
import com.example.majong.view.MahjongTile


class MainActivity : Activity() {
    private lateinit var gameView: GameView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        ) //coloca em fullscreen
        this.window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )


        gameView = GameView(this)
        setContentView(gameView)

    }



    override fun onResume() {
        super.onResume()

    }


}