package com.wao.tile.egine

import android.content.Context
import android.graphics.Bitmap
import com.wao.tile.view.MahjongTile
import kotlin.random.Random

class Quadrado0C {

    fun quadradoC(context: Context, w:Int, disponiveis: MutableList<Int>, tileImages: MutableList<Bitmap>) :MutableList<MahjongTile>{
        var initialize = 0
        var grau = 0
      var tiles = mutableListOf<MahjongTile>()

      val tamNovo = ((w * 0.9f) / 7).toInt()
        val espaco = w * 0.05f
        val h = tamNovo
        val w = tamNovo
        val tamm = +(2*tamNovo.toFloat())

        for (i in 0 until 40) {
            disponiveis.add(i)
        }
        var maximo = disponiveis.size

        for (i in 0 until 39) {
            if (i % 3 == 0) {
                initialize = -1
                grau++
                if (grau > ((maximo / 3) / 2).toInt()) {
                    grau = 1
                }
            }
            initialize++
            val index = Random.nextInt(disponiveis.size - 1)
            var camadaP = 1
            var x = 0f
            var y = 0f
            when (disponiveis[index]) {
                in 0..26 -> {
                    camadaP = 1
                    when (disponiveis[index]) {
                        in 0..2 -> {
                            y = tamNovo.toFloat() * 2

                        }

                        in 3..5 -> {
                            y = tamNovo.toFloat() * 3
                        }

                        in 6..8 -> {
                            y = tamNovo.toFloat() * 4
                        }

                        in 9..11 -> {
                            y = tamNovo.toFloat() * 2
                        }

                        in 12..14 -> {
                            y = tamNovo.toFloat() * 3
                        }

                        in 15..17 -> {
                            y = tamNovo.toFloat() * 4
                        }

                        in 18..20 -> {
                            y = tamNovo.toFloat() * 5
                        }
                        in 21..23 -> {
                            y = tamNovo.toFloat() * 6
                        }
                        in 24..26 -> {
                            y = tamNovo.toFloat() * 7
                        }

                    }
                    y+=tamm
                    when (disponiveis[index]) {
                        in listOf(0, 3, 6) -> x = espaco
                        in listOf(1, 4, 7) -> x =
                            espaco + tamNovo.toFloat() * 1
                        in listOf(2, 5, 8) -> x =
                            espaco + tamNovo.toFloat() * 2

                        in listOf(9, 12, 15) -> x =
                            espaco + tamNovo.toFloat() * 4
                        in listOf(10, 13, 16) -> x =
                            espaco + tamNovo.toFloat() * 5
                        in listOf(11, 14, 17) -> x =
                            espaco + tamNovo.toFloat() * 6


                        in listOf(18, 21, 24) -> x =
                            espaco + tamNovo.toFloat() * 2
                        in listOf(19, 22, 25) -> x =
                            espaco + tamNovo.toFloat() * 3
                        in listOf(20, 23, 26) -> x =
                            espaco + tamNovo.toFloat() * 4

                    }
                }

                in 27..38 -> {
                    camadaP = 2

                    when (disponiveis[index]) {
                        in 27..30 -> {
                            y = (tamNovo.toFloat() * 2) + (tamNovo.toFloat() / 2)
                        }

                        in 31..34 -> {
                            y = (tamNovo.toFloat() * 3) + (tamNovo.toFloat() / 2)
                        }

                        in 35..36 -> {
                            y = (tamNovo.toFloat() * 5) + (tamNovo.toFloat() / 2)
                        }

                        in 37..38 -> {
                            y = (tamNovo.toFloat() * 6) + (tamNovo.toFloat() / 2)
                        }


                    }
                    y+=tamm
                    when (disponiveis[index]) {
                        in listOf(27,31) -> x =
                            espaco +  (tamNovo.toFloat() / 2)


                        in listOf(28,32) -> x =
                            espaco + (tamNovo.toFloat() * 1) + (tamNovo.toFloat() / 2)

                        in listOf(29,33) -> x =
                            espaco + (tamNovo.toFloat() * 4) + (tamNovo.toFloat() / 2)

                        in listOf(30,34) -> x =
                            espaco + (tamNovo.toFloat() * 5) + (tamNovo.toFloat() / 2)

                        in listOf(35,37) -> x =
                            espaco + (tamNovo.toFloat() * 2) + (tamNovo.toFloat() / 2)

                        in listOf(36,38) -> x =
                            espaco + (tamNovo.toFloat() * 3) + (tamNovo.toFloat() / 2)

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