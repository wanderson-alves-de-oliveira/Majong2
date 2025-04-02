package com.wao.tile.egine

import android.content.Context
import android.graphics.Bitmap
import com.wao.tile.view.MahjongTile
import kotlin.random.Random

class QuadradoCC {

    fun quadradoC(context: Context, w:Int, disponiveis: MutableList<Int>, tileImages: MutableList<Bitmap>) :MutableList<MahjongTile>{
        var initialize = 0
        var grau = 0
      var tiles = mutableListOf<MahjongTile>()

      val tamNovo = ((w * 0.9f) / 7).toInt()
        val espaco = w * 0.05f
        val h = tamNovo
        val w = tamNovo
        for (i in 0 until 61) {
            disponiveis.add(i)
        }
        var maximo = disponiveis.size
        val tamm = +(2*tamNovo.toFloat())

        for (i in 0 until 60) {
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
                in 0..35 -> {
                    camadaP = 0
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
                            y = tamNovo.toFloat() * 6
                        }
                        in 21..23 -> {
                            y = tamNovo.toFloat() * 7
                        }
                        in 24..26 -> {
                            y = tamNovo.toFloat() * 8
                        }

                        in 27..29 -> {
                            y = tamNovo.toFloat() * 6
                        }
                        in 30..32 -> {
                            y = tamNovo.toFloat() * 7
                        }
                        in 33..35 -> {
                            y = tamNovo.toFloat() * 8
                        }

                    }
                    y+=tamm
                    when (disponiveis[index]) {
                        in listOf(0, 3, 6,18, 21, 24) -> x = espaco
                        in listOf(1, 4, 7,19, 22, 25) -> x =
                            espaco + tamNovo.toFloat() * 1
                        in listOf(2, 5, 8,20, 23, 26) -> x =
                            espaco + tamNovo.toFloat() * 2

                        in listOf(9, 12, 15,27,30,33) -> x =
                            espaco + tamNovo.toFloat() * 4
                        in listOf(10, 13, 16,28,31,34) -> x =
                            espaco + tamNovo.toFloat() * 5
                        in listOf(11, 14, 17,29,32,35) -> x =
                            espaco + tamNovo.toFloat() * 6



                    }
                }

                in 36..51 -> {
                    camadaP = 1

                    when (disponiveis[index]) {
                        in listOf(36,37,40,41) -> {
                            y = (tamNovo.toFloat() * 2) + (tamNovo.toFloat() / 2)
                        }

                        in listOf(38,39,42,43) -> {
                            y = (tamNovo.toFloat() * 3) + (tamNovo.toFloat() / 2)
                        }

                        in listOf(44,45,46,47) -> {
                            y = (tamNovo.toFloat() * 6) + (tamNovo.toFloat() / 2)
                        }
                        in listOf(48,49,50,51) -> {
                            y = (tamNovo.toFloat() *7) + (tamNovo.toFloat() / 2)
                        }

                    }
                    y+=tamm
                    when (disponiveis[index]) {
                        in listOf(36,38,44,48) -> x =
                            espaco +  (tamNovo.toFloat() / 2)


                        in listOf(37,39,45,49) -> x =
                            espaco + (tamNovo.toFloat() * 1) + (tamNovo.toFloat() / 2)

                        in listOf(40,42,46,50) -> x =
                            espaco + (tamNovo.toFloat() * 4) + (tamNovo.toFloat() / 2)

                        in listOf(41,43,47,51) -> x =
                            espaco + (tamNovo.toFloat() * 5) + (tamNovo.toFloat() / 2)


                    }
                }
//
                in 52..59 -> {
                    camadaP = 2
                    when (disponiveis[index]) {
                        in 52..53 -> {
                            y = (tamNovo.toFloat() * 2) + tamNovo.toFloat()
                        }

                        in 54..55 -> {
                            y = (tamNovo.toFloat() * 6) + tamNovo.toFloat()
                        }

                        in 56..57 -> {
                            y = (tamNovo.toFloat() * 3.5f) + tamNovo.toFloat()
                        }
                        in 58..59 -> {
                            y = (tamNovo.toFloat() *4.5f) + tamNovo.toFloat()
                        }
                    }
                    y+=tamm
                    when (disponiveis[index]) {
                        in listOf(52,54) -> x = espaco + tamNovo.toFloat()
                        in listOf(53,55) -> x =
                            espaco + (tamNovo.toFloat() * 4) + tamNovo.toFloat()

                        in listOf(56,58) -> x =
                            espaco + (tamNovo.toFloat() * 1.5f) + tamNovo.toFloat()
                        in listOf(57,59) -> x =
                            espaco + (tamNovo.toFloat() * 2.5f) + tamNovo.toFloat()
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