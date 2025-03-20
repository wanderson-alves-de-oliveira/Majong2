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
       // ran[0] = 108
        val tamNovo = ((w * 0.9f) / 6.5f).toInt()
        val espaco = w * 0.03f
        val h = tamNovo
        val w = tamNovo

        for (i in 0 until 6) {
            pY.add(tamNovo.toFloat() * (i + 1))
            if (i < 6) {

                pX.add(espaco + (tamNovo.toFloat() * i))
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
                    pX[j] + (tamNovo.toFloat() * (0.75f)),
                    pY[i] + (tamNovo.toFloat() * (0.75f))
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
        var index0 = (0..(ran[0]/2)-1).random()
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

 //           var t0 =tiles.filter { it.camada==0 }

//            var t01 =tiles.filter { it.camada==0 }
//
//            for (i in 0 until t0.size-1){
//
//
//                for (j in 1 until t01.size){
//                    if(t0[i].x==t01[j].x && t0[i].y==t01[j].y){
//                        tiles.get(tiles.indexOf(t0[i])).x = tiles.get(tiles.indexOf(t0[i])).x-(tamNovo.toFloat() * (0.8f))
//                        tiles.get(tiles.indexOf(t0[i])).y = tiles.get(tiles.indexOf(t0[i])).y+(tamNovo.toFloat() * (0.8f))
//                    }
//                }
//            }
//
//
//            var t1 =tiles.filter { it.camada==1 }
//
//            var t11 =tiles.filter { it.camada==1 }
//
//            for (i in 0 until t1.size-1){
//
//
//                for (j in 1 until t11.size){
//                    if(t1[i].x==t11[j].x && t1[i].y==t11[j].y){
//                        tiles.get(tiles.indexOf(t1[i])).x =
//                        if(tiles.get(tiles.indexOf(t1[i])).x-(tamNovo.toFloat() * (0.8f)) >=espaco)
//                            tiles.get(tiles.indexOf(t1[i])).x-(tamNovo.toFloat() * (0.8f))
//                        else espaco+ (tamNovo.toFloat() * (0.4f).toFloat())
//                        tiles.get(tiles.indexOf(t1[i])).y = tiles.get(tiles.indexOf(t1[i])).y+(tamNovo.toFloat() * (0.8f))
//                    }
//                }
//            }


//            var t2 =tiles.filter { it.camada==2 }
//
//            var t21 =tiles.filter { it.camada==2 }
//
//            for (i in 0 until t2.size-1){
//
//
//                for (j in 1 until t21.size){
//                    if(t2[i].x==t21[j].x && t2[i].y==t21[j].y){
//                        tiles.get(tiles.indexOf(t2[i])).x =
//                            if(tiles.get(tiles.indexOf(t2[i])).x-(tamNovo.toFloat() * (0.8f)) >=espaco)
//                                tiles.get(tiles.indexOf(t2[i])).x-(tamNovo.toFloat() * (0.8f))
//                        else espaco+ (tamNovo.toFloat() * (0.5f).toFloat())
//                        tiles.get(tiles.indexOf(t2[i])).y = tiles.get(tiles.indexOf(t2[i])).y+(tamNovo.toFloat() * (0.8f))
//                    }
//                }
//            }


        }

        var tiles2 = tiles.filter { it.camada==2 }

        tiles2.reversed()
        tiles.removeAll(tiles2)
        tiles.addAll(tiles2)

        return tiles.reversed().toMutableList()

    }


}