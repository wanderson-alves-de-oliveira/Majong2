package com.example.majong.view

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.TypedValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.example.majong.R

class Venceu(var context: Context,
             var w: Int,
             var h: Int,var tipo:Int) {

    var fase:Int =0
    var pontos:Int =0


    private val paint = Paint()
    private val paint2 = Paint()
    var y: Float = 0f
    var isSelected = false

    var inicio = true
    var xp = w*11f


    var espaco = 0f
    var tileImage = BitmapFactory.decodeResource(context.resources, R.drawable.mahjongtile)

    var btm = BotaoM(
        this.context,
        tileImage,
        ((this.w * 0.55)).toFloat(),
        (this.h * 0.6).toFloat(),
        (this.w * 0.3).toInt(),
        (this.h * 0.1).toInt(),
        0,
        fase.toString()
    )

    var btmCoin = BotaoM(
        this.context,
        tileImage,
        ((this.w * 0.2) ).toFloat(),
        (this.h * 0.6).toFloat(),
        (this.w * 0.3).toInt(),
        (this.h * 0.1).toInt(),
        1,
        "REVIVER"
    )


    var alpha = 0f
    var tam = 0f
    var liberado = false

    fun draw(canvas: Canvas) {

        if(tam<5){
            tam+=1f
        }else{
            liberado=true
        }

        paint.color = Color(0xFF000000).toArgb()
        paint.alpha = 150
        val b3: Bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        val canvasB = Canvas(b3)

        desenharRetanguloArredondadoCentralizado(canvasB, (this.w * 0.15f)*tam, (this.h * 0.1f)*tam, 70f,1f, paint)
      if(tipo==0) {
          paint.color = Color(0xFFFFA500).toArgb()
          btm.stt="Nível $fase"
          btm.w =  (this.w * 0.6).toInt()
          btm.camada = 0

      }else{
          paint.color = Color(0xFFD71204).toArgb()
          btm.stt="REVIVER"
          btm.camada =2
      }

        paint.alpha = 150
        desenharRetanguloArredondadoCentralizado(canvasB, (this.w * 0.14f)*tam, (this.h * 0.095f)*tam, 70f,1f, paint)


        if(tipo==0) {
            paint.color = Color(0xFF5442C5).toArgb()

        }else{
            paint.color = Color(0xFFFFA500).toArgb()

        }
        paint.alpha = 150
        desenharRetanguloArredondadoCentralizado(canvasB, (this.w * 0.13f)*tam, (this.h * 0.065f)*tam, 40f,0.8f, paint)



        if(tipo==0) {
            paint.color = Color(0xFF7B68EE).toArgb()

        }else{
            paint.color = Color(0xFFBE7D03).toArgb()

        }
        paint.alpha = 255
        desenharRetanguloArredondadoCentralizado(canvasB, (this.w * 0.12f)*tam, (this.h * 0.06f)*tam, 40f,0.8f, paint)


        paint.color = Color(0xFFFFFFFF).toArgb()
        desenharTextoCentralizado(canvasB, (this.w * 0.14f)*tam, (this.h * 0.095f)*tam, paint)

        canvas.drawBitmap(b3, 0f, 0f, paint)
        if(tipo==0) {
            btm.x = ((this.w * 0.2)).toFloat()
            btm.xFix = ((this.w * 0.2)).toFloat()

            btm.draw(canvas)
        }else{
            if(pontos>=100) {
                btm.h = (this.h * 0.08).toInt()
                btmCoin.h = (this.h * 0.08).toInt()
                btm.draw(canvas)
                btmCoin.draw(canvas)
            }else{
                btm.h = (this.h * 0.08).toInt()
                btm.x = (this.w * 0.35f).toFloat()
                btm.xFix = (this.w * 0.35f).toFloat()

                btm.draw(canvas)
            }

        }
    }

    fun desenharRetanguloArredondadoCentralizado(canvas: Canvas, largura: Float, altura: Float, raio: Float,alt:Float, paint: Paint) {
        // Calcula o centro da tela
        val centroX = canvas.width / 2f
        val centroY = canvas.height / 2f

        // Calcula os limites do retângulo arredondado
        val esquerda = centroX - largura / 2f
        val topo = (centroY - altura / 2f)*alt
        val direita = centroX + largura / 2f
        val inferior = (centroY + altura / 2f)*alt

        // Cria um objeto RectF com os limites calculados
        val rect = RectF(esquerda, topo, direita, inferior)

        canvas.drawRoundRect(rect, raio, raio, paint)
    }

    fun desenharTextoCentralizado(canvas: Canvas, largura: Float, altura: Float, paint: Paint) {
        // Calcula o centro da tela
        val centroX = canvas.width / 2f
        val centroY = canvas.height / 2f

        // Calcula os limites do retângulo arredondado
        val esquerda = centroX - largura / 2f
        val topo = centroY - altura / 2f
        val direita = centroX + largura / 2f
        val inferior = centroY + altura / 2f

        // Cria um objeto RectF com os limites calculados
        val rect = RectF(esquerda, topo, direita, inferior)
        paint.textSize = spToPx(altura*0.03f)
        if(tipo==0) {
            canvas.drawText("Nível ${fase - 1}", centroX - (largura / 6), centroY * 0.7f, paint)
            canvas.drawText("Completo", centroX - (largura / 4), centroY * 0.8f, paint)
            paint.color = Color(0xFF15072F).toArgb()
            if (liberado)
                canvas.drawText("Coins: $pontos", centroX / 2, centroY, paint)
        }else{


            canvas.drawText("SEM ESPAÇO", centroX - (largura / 2.5f), centroY * 0.7f, paint)
            canvas.drawText("Falhou", centroX - (largura / 4), centroY * 0.8f, paint)
            paint.color = Color(0xFF15072F).toArgb()
            if (liberado)
                canvas.drawText("Coins: $pontos", centroX / 2, centroY, paint)


        }


    }

    fun spToPx(sp: Float): Float {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP,
            sp,
            context.resources.displayMetrics
        )
    }
}