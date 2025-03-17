package com.example.majong.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapShader
import android.graphics.Canvas
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Shader
import android.util.TypedValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.sp
import com.example.majong.R

class BotaoM(val contexte: Context,val image: Bitmap, var x: Float, var y: Float, var w:Int, var h:Int, var camada:Int, var stt:String) {
    private val paint = Paint()

var animar = false
    var liberar = 0
    var t = 0f
    var inicio = true
    var yp = h*11f
val xFix = x
    fun draw(canvas: Canvas ) {
         paint.textSize = 80f
       val b: Bitmap = Bitmap.createBitmap(w,h, Bitmap.Config.ARGB_8888)

        val canvas2 = Canvas(b)

        if(animar){
            canvas2.scale(t,t)
            if(liberar==0) {
                t -= 0.1f
                x+=(w*0.01f)
            }else   if(liberar==1) {
                t += 0.1f
              //  x-=(w*0.02f)
            }
            if(t<=0.9f && liberar==0){
                 liberar = 1
            }else if(t>=1f && liberar==1){
                animar = false
                liberar = 2
            }


        }else{

            if(inicio){

                if(yp>y){
                    var dif = (yp-y)/2
                    yp-=dif
                    if(dif<=0.5f){
                        yp = y
                    }

                }else{
                    inicio = false
                }

            }else {
                b.width = w
                b.height = h
                x = xFix
                t = 1f
                if (liberar >= 2) {
                    liberar++
                }
            }



        }

        paint.alpha = 200

        paint.color = Color(0xFF4169E1).toArgb()

         canvas2.drawRoundRect(RectF(b.width.toFloat()*0.05f, b.height.toFloat()*0.05f, b.width.toFloat()*0.95f, b.height.toFloat()*0.95f), 70f, 70f, paint)

        paint.color = Color(0xFF836FFF).toArgb()

        canvas2.drawRoundRect(RectF(b.width.toFloat()*0.05f, b.height.toFloat()*0.1f, b.width.toFloat()*0.95f, b.height.toFloat()*0.95f), 70f, 70f, paint)

        paint.textSize =  spToPx(30f)

         paint.color = Color(0xFFffffff).toArgb()

        canvas2.drawText(
            "Nível $stt",
            100f,
            (h * 0.6).toFloat(),
            paint
        )




        canvas.drawBitmap(b,x,yp,paint)
    }

    fun containsTouch(touchX: Float, touchY: Float): Boolean {
        return ((touchX >= x && touchX <= x + w && touchY >= y && touchY <= y + h) && !inicio)
    }



     fun spToPx(sp: Float): Float {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP,
            sp,
            contexte.resources.displayMetrics
        )
    }



}
