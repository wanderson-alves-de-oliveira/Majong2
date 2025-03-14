package com.example.majong.egine

import android.content.Context
import android.graphics.Bitmap
import com.example.majong.view.MahjongTile
import kotlin.random.Random

class Quadrado0I {

    fun quadradoI(context: Context, w:Int, disponiveis: MutableList<Int>, tileImages: MutableList<Bitmap>) :MutableList<MahjongTile>{
        var initialize = 0
        var grau = 0
      var tiles = mutableListOf<MahjongTile>()

      val tamNovo = ((w * 0.9f) / 7).toInt()
        val espaco = w * 0.05f
        val h = tamNovo
        val w = tamNovo
        for (i in 0 until 34) {
            disponiveis.add(i)
        }
        var maximo = disponiveis.size

        for (i in 0 until 33) {
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
                in 0..20 -> {
                    camadaP = 1
                    when (disponiveis[index]) {
                        in listOf(0, 1, 2) -> {
                            y = tamNovo.toFloat() * 1
                        }
                        in listOf(3, 4, 5) -> {
                            y = tamNovo.toFloat() * 2
                        }
                        in listOf(6, 7, 8) -> {
                            y = tamNovo.toFloat() * 3
                        }
                        in listOf(9, 10, 11) -> {
                            y = tamNovo.toFloat() * 4
                        }
                        in listOf(12, 13, 14) -> {
                            y = tamNovo.toFloat() * 5
                        }
                        in listOf(15, 16, 17) -> {
                            y = tamNovo.toFloat() * 6
                        }
                        in listOf(18, 19, 20) -> {
                            y = tamNovo.toFloat() * 7
                        }
                    }
                    when (disponiveis[index]) {
                        in listOf(0, 3, 6, 9, 12, 15, 18) -> x =
                            espaco + tamNovo.toFloat() * 2
                        in listOf(1, 4, 7, 10, 13, 16, 19) -> x =
                            espaco + tamNovo.toFloat() * 3
                        in listOf(2, 5, 8,11,14,17,20) -> x =
                            espaco + tamNovo.toFloat() * 4


                    }
                }

                in 21..32 -> {
                    camadaP = 2

                    when (disponiveis[index]) {
                        in listOf(21, 22) -> {
                            y = tamNovo.toFloat() * 1.5f
                        }
                        in listOf(23, 24) -> {
                            y = tamNovo.toFloat() * 2.5f
                        }
                        in listOf(25, 26) -> {
                            y = tamNovo.toFloat() * 3.5f
                        }
                        in listOf(27, 28) -> {
                            y = tamNovo.toFloat() * 4.5f
                        }
                        in listOf(29, 30) -> {
                            y = tamNovo.toFloat() * 5.5f
                        }
                        in listOf(31, 32) -> {
                            y = tamNovo.toFloat() * 6.5f
                        }


                    }
                    when (disponiveis[index]) {
                        in listOf(21,23,25,27,29,31) -> x =
                            espaco + tamNovo.toFloat() * 2.5f

                        in listOf(22,24,26,28,30,32) -> x =
                            espaco + tamNovo.toFloat() * 3.5f

                    }
                }




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