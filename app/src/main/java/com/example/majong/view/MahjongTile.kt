package com.example.majong.view

import android.graphics.Bitmap
import android.graphics.BitmapShader
import android.graphics.Canvas
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.Shader
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb

class MahjongTile(var image: Bitmap, var x: Float, var y: Float, var camada:Int, var id: Int) {
    private val paint = Paint()
    private val paint2 = Paint()

    var isSelected = false

    fun draw(canvas: Canvas) {
     //   canvas.drawBitmap(image, x, y, paint)
        paint.textSize = 80f

        paint2.color = Color.Red.toArgb()

        paint.color = Color.Red.toArgb()
       val b: Bitmap = Bitmap.createBitmap(120,120, Bitmap.Config.ARGB_8888)
        val img = Bitmap.createScaledBitmap(image, 120, 120, false)
        val canvas2 = Canvas(b)
        //canvas2.drawRGB(250, 250, 0)
        val colorCamada = when(camada){
            0 -> Color.Green
            1 -> Color.Yellow
            2 -> Color.Blue
            else -> Color.Magenta
        }
   //   canvas2.drawRoundRect(0f, 0f, b.width.toFloat(), b.height.toFloat(),40f,40f, paint2);
//        paint.color = colorCamada.toArgb()
//        canvas2.drawRoundRect(5f, 5f, (b.width-5).toFloat(), (b.height-5).toFloat(),40f,40f, paint);
//        paint.color = Color.Black.toArgb()

        //canvas2.drawText(id.toString(),(10).toFloat(),(b.height/1.5f).toFloat(),paint)

        if (img != null) {


                val matrix = ColorMatrix()

                if(camada==0) {
                    matrix.set(
                        floatArrayOf(
                            0.6f, 0f, 0f, 0f, 0f, // Red
                            0f, 0.6f, 0f, 0f, 0f, // Green
                            0f, 0f, 0.6f, 0f, 0f, // Blue
                            0f, 0f, 0f, 1f, 0f  // Alpha
                        )
                    )
                }else if(camada==1){
                    matrix.set(
                        floatArrayOf(
                            0.8f, 0f, 0f, 0f, 0f, // Red
                            0f, 0.8f, 0f, 0f, 0f, // Green
                            0f, 0f, 0.8f, 0f, 0f, // Blue
                            0f, 0f, 0f, 1f, 0f  // Alpha
                        )
                    )
                }else{
                    matrix.set(
                        floatArrayOf(
                            1.0f, 0f, 0f, 0f, 0f, // Red
                            0f, 1.0f, 0f, 0f, 0f, // Green
                            0f, 0f, 1.0f, 0f, 0f, // Blue
                            0f, 0f, 0f, 1f, 0f  // Alpha
                        )
                    )
                }


                paint.colorFilter = ColorMatrixColorFilter(matrix)

            // Obtenha as dimensões da View
            val width =img.width
            val height = img.height

            // Crie um BitmapShader com a imagem
            val shader = BitmapShader(img!!, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
            paint.shader = shader

            // Defina o raio das bordas arredondadas
            val cornerRadius = 20f
            paint.color = Color.Red.toArgb()
         //   canvas2.drawRoundRect(0f, 0f, b.width.toFloat(), b.height.toFloat(),40f,40f, paint);
            // Desenhe um retângulo com bordas arredondadas
            canvas2.drawRoundRect(RectF(0f, 0f, width.toFloat(), height.toFloat()), cornerRadius, cornerRadius, paint)
        }


        canvas.drawBitmap(b,x,y,paint)
      //  canvas.drawBitmap(img,x,y,paint)


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
