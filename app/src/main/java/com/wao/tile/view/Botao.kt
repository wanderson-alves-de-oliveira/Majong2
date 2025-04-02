package com.wao.tile.view

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb

class Botao(val image: Bitmap, var x: Float, var y: Float,var w:Int,var h:Int, var camada:Int, val stt:String) {
    private val paint = Paint()
    var isSelected = false

    fun draw(canvas: Canvas,opaco:Boolean = false) {
     //   canvas.drawBitmap(image, x, y, paint)
        paint.textSize = 80f
       val b: Bitmap = Bitmap.createBitmap(120,120, Bitmap.Config.ARGB_8888)
        val img = Bitmap.createScaledBitmap(image, (w*0.9f).toInt(), (h*0.9f).toInt(), false)

        val canvas2 = Canvas(b)
        //canvas2.drawRGB(250, 250, 0)
        val colorCamada = when(camada){
            0 -> Color.Green
            1 -> Color.Yellow
            2 -> Color.Blue
            else -> Color.Magenta
        }



        paint.color = Color(0xFF836FFF).toArgb()
        paint.alpha = 150
        canvas2.drawRoundRect(RectF(1f, 1f, b.width.toFloat(), b.height.toFloat()), 20f, 20f, paint)

//
//        paint.color = Color.Red.toArgb()
//        canvas2.drawRoundRect(0f, 0f, b.width.toFloat(), b.height.toFloat(),40f,40f, paint);
//        paint.color = colorCamada.toArgb()
//        canvas2.drawRoundRect(5f, 5f, (b.width-5).toFloat(), (b.height-5).toFloat(),40f,40f, paint);
//        paint.color = Color.Black.toArgb()
//        canvas2.drawText(stt,(10).toFloat(),(b.height/1.5f).toFloat(),paint)

        canvas.drawBitmap(b,x,y,paint)
    }

    fun containsTouch(touchX: Float, touchY: Float): Boolean {
        return touchX >= x && touchX <= x + 120f && touchY >= y && touchY <= y + 120f
    }

    fun containsCamada(touchX: Float, touchY: Float): Boolean {
      val t1 =  touchX > x && touchX < x + 120f && touchY > y && touchY < y + 120f
        val t2 =  touchX + 120f> x && touchX+ 120f < x + 120f && touchY > y && touchY < y + 120f
        val t3 =  touchX > x && touchX < x + 120f && touchY + 120f> y && touchY + 120f< y + 120f
        val t4 =  touchX + 120f> x && touchX+ 120f < x + 120f && touchY+ 120f > y && touchY + 120f< y + 120f
        if(t1 || t2 || t3 || t4){
            return true
        }

        return false
    }
}
