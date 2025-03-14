package com.example.majong.egine

import android.content.Context
import android.graphics.Bitmap
import com.example.majong.view.MahjongTile
import kotlin.random.Random

class QuadradoT {

    fun quadradoT(context: Context, w:Int, disponiveis: MutableList<Int>, tileImages: MutableList<Bitmap>) :MutableList<MahjongTile>{
        var initialize = 0
        var grau = 0
      var tiles = mutableListOf<MahjongTile>()

      val tamNovo = ((w * 0.9f) / 7).toInt()
        val espaco = w * 0.05f
        val h = tamNovo
        val w = tamNovo
        for (i in 0 until 64) {
            disponiveis.add(i)
        }
        var maximo = disponiveis.size

        for (i in 0 until 63) {
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
                in 0..32 -> {
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
                            y = tamNovo.toFloat() * 5
                        }
                        in 21..23 -> {
                            y = tamNovo.toFloat() * 6
                        }
                        in 24..26 -> {
                            y = tamNovo.toFloat() * 7
                        }

                        in listOf(27, 28, 29) -> {
                            y = tamNovo.toFloat() * ((disponiveis[index] - 27) + 2)
                        }
                        in listOf(30, 31, 32) -> {
                            y = tamNovo.toFloat() * 8
                        }
                    }
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
                        in listOf(19, 22, 25,27,28,29) -> x =
                            espaco + tamNovo.toFloat() * 3
                        in listOf(20, 23, 26) -> x =
                            espaco + tamNovo.toFloat() * 4
                        in listOf(30, 31, 32) -> x =
                            espaco + tamNovo.toFloat() *  ((disponiveis[index] - 30) + 2)
                    }
                }

                in 33..52 -> {
                    camadaP = 1

                    when (disponiveis[index]) {
                        in 33..38 -> {
                            y = (tamNovo.toFloat() * 2) + (tamNovo.toFloat() / 2)
                        }

                        in 39..44 -> {
                            y = (tamNovo.toFloat() * 3) + (tamNovo.toFloat() / 2)
                        }

                        in 45..48 -> {
                            y = (tamNovo.toFloat() *  ((disponiveis[index] - 45) +4)) + (tamNovo.toFloat() / 2)
                        }

                        in 49..52 -> {
                            y = (tamNovo.toFloat() *  ((disponiveis[index] - 49) +4)) + (tamNovo.toFloat() / 2)
                        }



                    }
                    when (disponiveis[index]) {
                        in listOf(33,34,35,36,37,38) -> x =
                            espaco + (tamNovo.toFloat() *
                                    ((disponiveis[index] - 33) )) + (tamNovo.toFloat() / 2)


                        in listOf(39,40,41,42,43,44) -> x =
                            espaco + (tamNovo.toFloat() *
                                    ((disponiveis[index] - 39) )) + (tamNovo.toFloat() / 2)

                        in listOf(45,46,47,48) -> x =
                            espaco + (tamNovo.toFloat() *
                                    2) + (tamNovo.toFloat() / 2)

                        in listOf(49,50,51,52) -> x =
                            espaco + (tamNovo.toFloat() *
                                    3) + (tamNovo.toFloat() / 2)

                    }
                }

                in 53..77 -> {
                    camadaP = 2
                    when (disponiveis[index]) {
                        in 53..57 -> {
                            y = (tamNovo.toFloat() * 2.5f)+ (tamNovo.toFloat() / 2)
                        }

                        in 58..62 -> {
                            y = (tamNovo.toFloat() * ((disponiveis[index] - 58) +3.5f))+ (tamNovo.toFloat() / 2)
                        }

                    }
                    when (disponiveis[index]) {
                        in listOf(53,54,55,56,57) -> x =
                            espaco + (tamNovo.toFloat() *
                                    ((disponiveis[index] - 53) +0.5f)) + (tamNovo.toFloat() / 2)

                        in listOf(58,59,60,61,62) -> x =
                            espaco + (tamNovo.toFloat() *2.5f) + (tamNovo.toFloat() / 2)

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