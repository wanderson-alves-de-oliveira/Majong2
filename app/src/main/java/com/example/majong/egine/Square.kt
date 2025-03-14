package com.example.majong.egine

import android.content.Context
import android.graphics.Bitmap
import com.example.majong.view.MahjongTile
import kotlin.random.Random

class Square {

    fun quadrado(context: Context, w:Int, disponiveis: MutableList<Int>, tileImages: MutableList<Bitmap>) :MutableList<MahjongTile>{
        var initialize = 0
        var grau = 0
      var tiles = mutableListOf<MahjongTile>()

      val tamNovo = ((w * 0.9f) / 6).toInt()
        val espaco = w * 0.05f
        val h = tamNovo
        val w = tamNovo
        for (i in 0 until 79) {
            disponiveis.add(i)
        }
        var maximo = disponiveis.size

        for (i in 0 until 78) {
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
                in 0..35 -> {
                    camadaP = 0
                    when (disponiveis[index]) {
                        in 0..5 -> {
                            y = tamNovo.toFloat()
                        }

                        in 6..11 -> {
                            y = tamNovo.toFloat() * 2
                        }

                        in 12..17 -> {
                            y = tamNovo.toFloat() * 3
                        }

                        in 18..23 -> {
                            y = tamNovo.toFloat() * 4
                        }

                        in 24..29 -> {
                            y = tamNovo.toFloat() * 5
                        }

                        in 30..35 -> {
                            y = tamNovo.toFloat() * 6
                        }
                    }
                    when (disponiveis[index]) {
                        in listOf(0, 6, 12, 18, 24, 30) -> x = espaco
                        in listOf(1, 7, 13, 19, 25, 31) -> x =
                            espaco + tamNovo.toFloat() * 1

                        in listOf(2, 8, 14, 20, 26, 32) -> x =
                            espaco + tamNovo.toFloat() * 2

                        in listOf(3, 9, 15, 21, 27, 33) -> x =
                            espaco + tamNovo.toFloat() * 3

                        in listOf(4, 10, 16, 22, 28, 34) -> x =
                            espaco + tamNovo.toFloat() * 4

                        in listOf(5, 11, 17, 23, 29, 35) -> x =
                            espaco + tamNovo.toFloat() * 5
                    }
                }

                in 36..60 -> {
                    camadaP = 1

                    when (disponiveis[index]) {
                        in 36..40 -> {
                            y = tamNovo.toFloat() + (tamNovo.toFloat() / 2)
                        }

                        in 41..45 -> {
                            y = (tamNovo.toFloat() * 2) + (tamNovo.toFloat() / 2)
                        }

                        in 46..50 -> {
                            y = (tamNovo.toFloat() * 3) + (tamNovo.toFloat() / 2)
                        }

                        in 51..55 -> {
                            y = (tamNovo.toFloat() * 4) + (tamNovo.toFloat() / 2)
                        }

                        in 56..60 -> {
                            y = (tamNovo.toFloat() * 5) + (tamNovo.toFloat() / 2)
                        }
                    }
                    when (disponiveis[index]) {
                        in listOf(36, 41, 46, 51, 56) -> x =
                            espaco + (tamNovo.toFloat() / 2)

                        in listOf(37, 42, 47, 52, 57) -> x =
                            espaco + (tamNovo.toFloat() * 1) + (tamNovo.toFloat() / 2)

                        in listOf(38, 43, 48, 53, 58) -> x =
                            espaco + (tamNovo.toFloat() * 2) + (tamNovo.toFloat() / 2)

                        in listOf(39, 44, 49, 54, 59) -> x =
                            espaco + (tamNovo.toFloat() * 3) + (tamNovo.toFloat() / 2)

                        in listOf(40, 45, 50, 55, 60) -> x =
                            espaco + (tamNovo.toFloat() * 4) + (tamNovo.toFloat() / 2)
                    }
                }

                in 61..76 -> {
                    camadaP = 2
                    when (disponiveis[index]) {
                        in 61..64 -> {
                            y = tamNovo.toFloat() + tamNovo.toFloat()
                        }

                        in 65..68 -> {
                            y = (tamNovo.toFloat() * 2) + tamNovo.toFloat()
                        }

                        in 69..72 -> {
                            y = (tamNovo.toFloat() * 3) + tamNovo.toFloat()
                        }

                        in 73..76 -> {
                            y = (tamNovo.toFloat() * 4) + tamNovo.toFloat()
                        }
                    }
                    when (disponiveis[index]) {
                        in listOf(61, 65, 69, 73) -> x = espaco + tamNovo.toFloat()
                        in listOf(62, 66, 70, 74) -> x =
                            espaco + (tamNovo.toFloat() * 1) + tamNovo.toFloat()

                        in listOf(63, 67, 71, 75) -> x =
                            espaco + (tamNovo.toFloat() * 2) + tamNovo.toFloat()

                        in listOf(64, 68, 72, 76) -> x =
                            espaco + (tamNovo.toFloat() * 3) + tamNovo.toFloat()
                    }
                }

                else -> {
                    camadaP = 2
                    y = espaco + (tamNovo.toFloat() * 4) + tamNovo.toFloat()
                    x = espaco + (tamNovo.toFloat() * 4) + tamNovo.toFloat()
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