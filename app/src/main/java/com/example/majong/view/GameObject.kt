package com.example.majong.view

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint

class GameObject(var x: Float, var y: Float, var speed: Float,num:Int, bitmap: Bitmap?) {
    private val paint = Paint().apply { setARGB(255, 255, 0, 0) }

    fun update(deltaTime: Double) {
        // Exemplo de movimento simples
        x += (speed * deltaTime).toFloat()
    }

    fun onDraw(canvas: Canvas) {
        canvas.drawCircle(x, y, 20f, paint)
    }
}