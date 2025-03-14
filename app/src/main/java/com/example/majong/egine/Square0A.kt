package com.example.majong.egine

import android.content.Context
import android.graphics.Bitmap
import com.example.majong.view.MahjongTile
import kotlin.random.Random

class Square0A {

    fun quadrado(context: Context, w:Int, disponiveis: MutableList<Int>, tileImages: MutableList<Bitmap>) :MutableList<MahjongTile>{
        var initialize = 0
        var grau = 0
      var tiles = mutableListOf<MahjongTile>()

      val tamNovo = ((w * 0.9f) / 6).toInt()
        val espaco = w * 0.05f
        val h = tamNovo
        val w = tamNovo
        for (i in 0 until 55) {
            disponiveis.add(i)
        }
        var maximo = disponiveis.size

        for (i in 0 until 54) {
            if (i % 3 == 0) {
                initialize = -1
                grau++
                if (grau > ((maximo / 3) / 2).toInt()) {
                    grau = 1
                }
            }
            initialize++
            val index = Random.nextInt(disponiveis.size - 1)
            var camadaP = 0
            var x = 0f
            var y = 0f
            when (disponiveis[index]) {
                in 0..31 -> {
                    camadaP = 1
                    when (disponiveis[index]) {
                        in listOf(0, 1, 2, 3)-> {
                            y = tamNovo.toFloat()
                        }

                        in listOf(4,5, 6, 7, 8, 9) -> {
                            y = tamNovo.toFloat() * 2
                        }

                        in listOf(10,11, 12, 13, 14,15) -> {
                            y = tamNovo.toFloat() * 3
                        }

                        in listOf(16,17,18, 19, 20,21)  -> {
                            y = tamNovo.toFloat() * 4
                        }

                        in listOf(22, 23, 24, 25, 26,27)  -> {
                            y = tamNovo.toFloat() * 5
                        }

                        in listOf(28, 29, 30, 31) -> {
                            y = tamNovo.toFloat() * 6
                        }
                    }
                    when (disponiveis[index]) {
                        in listOf(0, 1, 2, 3) -> x =
                            espaco + tamNovo.toFloat() * ((disponiveis[index] )+1)
                       in listOf(4,5, 6, 7, 8, 9)  -> x =
                            espaco + tamNovo.toFloat() *  ((disponiveis[index] -4))


                        in listOf(10,11, 12, 13, 14,15) -> {
                            x =
                                espaco + tamNovo.toFloat() *  ((disponiveis[index] -10))
                        }

                        in listOf(16,17,18, 19, 20,21)  -> {
                            x =
                                espaco + tamNovo.toFloat() *  ((disponiveis[index] -16))
                        }

                        in listOf(22, 23, 24, 25, 26,27)  -> {
                            x =
                                espaco + tamNovo.toFloat() *  ((disponiveis[index] -22))
                        }

                        in listOf(28, 29, 30, 31) -> {
                            x =
                                espaco + tamNovo.toFloat() *  ((disponiveis[index] -28)+1)
                        }
                    }
                }

                in 32..53 -> {
                    camadaP = 2

                    when (disponiveis[index]) {
                        in listOf(32, 33, 34) -> {
                            y = tamNovo.toFloat() * 1.5f
                        }
                        in listOf(35,36, 37, 38,39) -> {
                            y = tamNovo.toFloat() * 2.5f
                        }
                        in listOf(40,41, 42, 43,44) -> {
                            y = tamNovo.toFloat() * 3.5f
                        }
                        in listOf(45,46, 47, 48,49) -> {
                            y = tamNovo.toFloat() * 4.5f
                        }
                        in listOf(50,51, 52) -> {
                            y = tamNovo.toFloat() * 5.5f
                        }
                        in listOf(53) -> {
                            y = tamNovo.toFloat() * 6.5f
                        }
                    }
                    when (disponiveis[index]) {
                        in listOf(32,33, 34) -> x =
                            espaco + (tamNovo.toFloat() *
                                    ((disponiveis[index] - 32)+0.5f)) + tamNovo.toFloat()
                        in listOf(35,36, 37, 38,39) -> x =
                            espaco + (tamNovo.toFloat() *
                                    ((disponiveis[index] - 35)-0.5f)) + tamNovo.toFloat()
                        in listOf(40,41, 42, 43,44) -> x =
                            espaco + (tamNovo.toFloat() *
                                    ((disponiveis[index] - 40)-0.5f)) + tamNovo.toFloat()
                        in listOf(45,46, 47, 48,49) -> x =
                            espaco + (tamNovo.toFloat() *
                                    ((disponiveis[index] - 45)-0.5f)) + tamNovo.toFloat()
                        in listOf(50,51, 52) -> {
                            x =
                                espaco + (tamNovo.toFloat() *
                                        ((disponiveis[index] - 50)+0.5f)) + tamNovo.toFloat()
                        }

                        in listOf(53) -> {
                            x =
                                espaco + (tamNovo.toFloat() * 1.5f) + tamNovo.toFloat()
                        }
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