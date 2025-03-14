package com.example.majong.egine

import android.content.Context
import android.graphics.Bitmap
import androidx.compose.ui.geometry.Offset
import com.example.majong.view.MahjongTile
import kotlin.random.Random

class QuadradoAleatorio {


    fun quadrado(context: Context,
                 w: Int,
                 disponiveis: MutableList<Int>,
                 tileImages: MutableList<Bitmap>
    ): MutableList<MahjongTile> {
        var initialize = 0
        var grau = 0
        var tiles = mutableListOf<MahjongTile>()

        var pY = mutableListOf<Float>()
        var pX = mutableListOf<Float>()
        var pXY0 = mutableListOf<Offset>()
        var pXY1 = mutableListOf<Offset>()
        var pXY2 = mutableListOf<Offset>()
        var ran = mutableListOf<Int>()
        for (i in 20 until 79) {
            if(i%3==0){
                ran.add(i)
            }
        }
        ran.shuffle()
        val tamNovo = ((w * 0.9f) / 7).toInt()
        val espaco = w * 0.05f
        val h = tamNovo
        val w = tamNovo

        for (i in 0 until 7) {
            pY.add(tamNovo.toFloat() * (i + 1))
            if (i < 6) {

                pX.add(espaco + tamNovo.toFloat() * i)
            }
        }

        for (i in 0 until pY.size) {
            for (j in 0 until pX.size) {
                var o = Offset(pX[j], pY[i])
                var o1 = Offset(
                    pX[j] + (tamNovo.toFloat() * (0.5f)),
                    pY[i] + (tamNovo.toFloat() * (0.5f))
                )
                var o2 = Offset(
                    pX[j] + (tamNovo.toFloat() * (0.7f)),
                    pY[i] + (tamNovo.toFloat() * (0.7f))
                )

                pXY0.add(o)
                pXY1.add(o1)
                pXY2.add(o2)

            }
        }
        pXY0.shuffle()
        pXY1.shuffle()
        pXY2.shuffle()
        var camada0 = 0
        var camada1 = 0
        var camada2 = 0
        var index0 = (0..ran[0]/2).random()
        var index1 = (ran[0]/2..ran[0]).random()

        for (i in 0 until ran[0]+1) {
            disponiveis.add(i)
        }
        var maximo = disponiveis.size


        for (i in 0 until ran[0]) {
            if (i % 3 == 0) {
                initialize = -1
                grau++
                if (grau > ((maximo / 3) / 2).toInt()) {
                    grau = 1
                }
            }
            initialize++
            val index = Random.nextInt(disponiveis.size - 1)
            var camadaP = 2
            var x = 0f
            var y = 0f
            when (disponiveis[index]) {
                in 0..index0 -> {
                    camadaP = 0

                    y = pXY0.get(camada0).y

                    x = pXY0.get(camada0).x
                    if (camada0 < pXY0.size-1) {

                        camada0++
                    }
                }

                in index0+1..index1 -> {
                    camadaP = 1
                    y = pXY1.get(camada1).y

                    x = pXY1.get(camada1).x

                    if (camada1 < pXY1.size-1) {
                        camada1++
                    }

                }

                in index1+1..ran[0] -> {
                    camadaP = 2
                    y = pXY2.get(camada2).y

                    x = pXY2.get(camada2).x

                    if (camada2 < pXY2.size-1) {

                        camada2++
                    }
                }
//
//                else -> {
//                    camadaP = 2
//                    y = espaco + (tamNovo.toFloat() * 4) + tamNovo.toFloat()
//                    x = espaco + (tamNovo.toFloat() * 4) + tamNovo.toFloat()
//                }
            }


            disponiveis.remove(disponiveis[index])

            tiles.add(
                MahjongTile(
                    tileImages[grau - 1],
                    context,
                    x,
                    y,
                    w,
                    h,
                    camadaP,
                    grau,
                    true
                )
            ) // Criando pares

        }

        return tiles

    }


}