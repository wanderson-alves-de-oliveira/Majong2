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


class MahjongTile(var image: Bitmap, var x: Float, var yp: Float,var w:Int,var h:Int, var camada:Int, var id: Int,girando:Boolean=false) {



    private val paint = Paint()
    private val paint2 = Paint()
    var y: Float = if(id>=1000)yp else-100f
    var isSelected = false
    var intro = true
    var bloqueado = false
    var ref  = -1


    var ty = false
    var espaco  = 0f
    var girando = girando
    var giroc= 0f
    private val imutw = w
    private val imuth = h
    private val VAL_LIMITG = 25
    private val VAL_LIMITG_INTRO = 3

    var timeG = if(intro) VAL_LIMITG_INTRO else VAL_LIMITG
    fun draw(canvas: Canvas) {
     //   canvas.drawBitmap(image, x, y, paint)
        paint.textSize = 80f

        paint2.color = Color.LightGray.toArgb()

        paint.color = Color.Red.toArgb()

        if(w<=0 || h <=0){
            w=100
            h=100
        }
       val b: Bitmap = Bitmap.createBitmap(w,h, Bitmap.Config.ARGB_8888)
        if (!isSelected) {
            espaco = 0f
        } else {
            espaco = w*0.06f

        }

        val img = Bitmap.createScaledBitmap(image, ((w*0.9f)-espaco).toInt(), ((h*0.9f)-espaco).toInt(), false)
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
//
        if (isSelected) {
            paint2.color = Color.Gray.toArgb()
        }
        canvas2.drawRoundRect(0f, 0f, b.width.toFloat()-espaco, b.height.toFloat()-espaco,20f,20f, paint2);


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
            var width =img.width
            var height = img.height

            // Crie um BitmapShader com a imagem
            val shader = BitmapShader(img!!, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
            paint.shader = shader

            // Defina o raio das bordas arredondadas
            val cornerRadius = 20f
            paint.color = Color.LightGray.toArgb()
         //   canvas2.drawRoundRect(0f, 0f, b.width.toFloat(), b.height.toFloat(),40f,40f, paint);
            // Desenhe um retângulo com bordas arredondadas



            canvas2.drawRoundRect(RectF(1f, 1f, (width).toFloat(), (height).toFloat()), cornerRadius, cornerRadius, paint)



        }
if(intro) {
    if (girando) {
        giroc -= 5f
        timeG--
        if (timeG <= 0 && giroc <= 0) {
            girando = false
            giroc = 0f
            timeG = VAL_LIMITG
            intro = false
        }
        canvas.save()
        canvas.translate(x - espaco, (y + espaco) - giroc)
        //  canvas.rotate(giroc,centerX,centerY)
        canvas.drawBitmap(b, x + espaco, y + espaco, paint)
        canvas.restore()
    } else {
        canvas.drawBitmap(b, x + espaco, y + espaco, paint)
    }
}else{
    if(girando){
        giroc-=200f
        timeG--
        if(timeG<=0 && giroc<=0){
            girando = false
            giroc=0f
            timeG =VAL_LIMITG
        }
        canvas.save()
        canvas.translate((x- (w*0.5f)),(y+espaco)+giroc)
        //  canvas.rotate(giroc,centerX,centerY)
        canvas.drawBitmap(b,x+espaco,y+espaco,paint)
        canvas.restore()
    }else{
        canvas.drawBitmap(b,x+espaco,y+espaco,paint)
    }
}




    }

    fun containsTouch(touchX: Float, touchY: Float): Boolean {
        return touchX >= x && touchX <= x + w && touchY >= y && touchY <= y + h
    }

    fun containsCamada(touchX: Float, touchY: Float): Boolean {
      val t1 =  touchX > x && touchX < x + w && touchY > y && touchY < y + h
        val t2 =  touchX + w> x && touchX+ w < x + w && touchY > y && touchY < y + h
        val t3 =  touchX > x && touchX < x + w && touchY + h> y && touchY + h< y + h
        val t4 =  touchX + w> x && touchX+ w < x + w && touchY+ h > y && touchY + h< y + h
        if(t1 || t2 || t3 || t4){
            return true
        }

        return false
    }
}
