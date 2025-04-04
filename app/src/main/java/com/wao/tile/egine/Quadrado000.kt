package com.wao.tile.egine

import android.content.Context
import android.graphics.Bitmap
import com.wao.tile.view.MahjongTile
import kotlin.random.Random

class Quadrado000 {



    fun quadrado(context: Context, w:Int, disponiveis: MutableList<Int>, tileImages: MutableList<Bitmap>) :MutableList<MahjongTile>{
        var initialize = 0
        var grau = 0
        val tiles = mutableListOf<MahjongTile>()

        val tamNovo = ((w * 0.9f) / 5).toInt()
        val espaco = (((w * 0.9f) / 6)).toInt()
        val h = tamNovo+0
        val ww = tamNovo+0
        for (i in 0 until 10) {
            disponiveis.add(i)
        }
        val maximo = disponiveis.size

        for (i in 0 until 9) {
            if (i % 3 == 0) {
                initialize = -1
                grau++
                if (grau > ((maximo / 3) / 2)) {
                    grau = 1
                }
            }
            initialize++
            val index = Random.nextInt(disponiveis.size - 1)
            var camadaP: Int
            var x: Float
            var y: Float
            when (disponiveis[index]) {
                in 0..4 -> {
                    camadaP = 1

                            y = tamNovo.toFloat()*4.6f

                          x = (espaco/2)  + tamNovo.toFloat() * disponiveis[index]

                }

                else -> {
                    camadaP = 2

                     y = (tamNovo.toFloat() * 4)+ (tamNovo.toFloat() / 3)

                     x =   (espaco/2)   + (tamNovo.toFloat() * (disponiveis[index] - 5)) + (tamNovo.toFloat() / 2)


                }

            }


            disponiveis.remove(disponiveis[index])

            tiles.add(
                MahjongTile(
                    tileImages[grau - 1],
                    context,
                    x,
                    y,
                    ww,
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