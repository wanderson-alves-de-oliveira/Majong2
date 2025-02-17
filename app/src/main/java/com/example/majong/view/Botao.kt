package com.example.majong.view

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.sp
import com.example.majong.R

class Botao(val image: Bitmap, var x: Float, var y: Float, var camada:Int, val stt:String) {
    private val paint = Paint()
    var isSelected = false

    fun draw(canvas: Canvas) {
     //   canvas.drawBitmap(image, x, y, paint)
        paint.textSize = 80f
       val b: Bitmap = Bitmap.createBitmap(120,120, Bitmap.Config.ARGB_8888)
        val canvas2 = Canvas(b)
        //canvas2.drawRGB(250, 250, 0)
        val colorCamada = when(camada){
            0 -> Color.Green
            1 -> Color.Yellow
            2 -> Color.Blue
            else -> Color.Magenta
        }
        paint.color = Color.Red.toArgb()
        canvas2.drawRoundRect(0f, 0f, b.width.toFloat(), b.height.toFloat(),40f,40f, paint);
        paint.color = colorCamada.toArgb()
        canvas2.drawRoundRect(5f, 5f, (b.width-5).toFloat(), (b.height-5).toFloat(),40f,40f, paint);
        paint.color = Color.Black.toArgb()
        canvas2.drawText(stt,(10).toFloat(),(b.height/1.5f).toFloat(),paint)

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
