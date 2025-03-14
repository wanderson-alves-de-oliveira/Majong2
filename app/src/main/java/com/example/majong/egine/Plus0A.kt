package com.example.majong.egine

import android.content.Context
import android.graphics.Bitmap
import com.example.majong.view.MahjongTile
import kotlin.random.Random

class Plus0A {



    fun plus(context: Context, w:Int, disponiveis: MutableList<Int>, tileImages: MutableList<Bitmap>) :MutableList<MahjongTile>{
        var initialize = 0
        var grau = 0
        var tiles = mutableListOf<MahjongTile>()

        val tamNovo = ((w * 0.9f) / 6).toInt()
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
            var camadaP = 0
            var x = 0f
            var y = 0f
            when (disponiveis[index]) {
                in 0..19 -> {
                    camadaP = 1
                    when (disponiveis[index]) {
                        in 0..3 -> {
                            y = tamNovo.toFloat()
                        }

                        in 4..7 -> {
                            y = tamNovo.toFloat() * 2
                        }

                        in 8..11 -> {
                            y = tamNovo.toFloat() * 3
                        }

                        in 12..15 -> {
                            y = tamNovo.toFloat() * 4
                        }


                        in 16..17 -> {
                            y = tamNovo.toFloat() * 2
                        }
                        in 18..19 -> {
                            y = tamNovo.toFloat() * 3
                        }

                    }
                    when (disponiveis[index]) {
                        in listOf(0, 4, 8, 12) -> x = espaco  + tamNovo.toFloat() *1
                        in listOf(1, 5, 9, 13) -> x =
                            espaco + tamNovo.toFloat() * 2

                        in listOf(2, 6, 10, 14) -> x =
                            espaco + tamNovo.toFloat() * 3

                        in listOf(3, 7, 11, 15) -> x =
                            espaco + tamNovo.toFloat() * 4
                        in listOf(16,18) -> x =
                            espaco
                        in listOf(17,19) -> x =
                            espaco + tamNovo.toFloat() * 5

                    }
                }

                in 20..32 -> {
                    camadaP = 2

                    when (disponiveis[index]) {
                        in 20..22 -> {
                            y = (tamNovo.toFloat() * 1)+ (tamNovo.toFloat() / 2)
                        }

                        in 23..25 -> {
                            y = (tamNovo.toFloat() * 2) + (tamNovo.toFloat() / 2)
                        }

                        in 26..28 -> {
                            y = (tamNovo.toFloat() * 3) + (tamNovo.toFloat() / 2)
                        }
                        in 29..30 -> {
                            y = (tamNovo.toFloat() * 2) + (tamNovo.toFloat() / 2)
                        }
                        in 31..32 -> {
                            y = (tamNovo.toFloat() * 4) + (tamNovo.toFloat() / 2)
                        }

                    }
                    when (disponiveis[index]) {
                        in listOf(20, 23, 26,31) -> x =
                            espaco + (tamNovo.toFloat() * 1) + (tamNovo.toFloat() / 2)

                        in listOf(21, 24, 27) -> x =
                            espaco + (tamNovo.toFloat() * 2) + (tamNovo.toFloat() / 2)

                        in listOf(22, 25, 28,32) -> x =
                            espaco + (tamNovo.toFloat() * 3) + (tamNovo.toFloat() / 2)


                        in listOf(29) -> x =
                            espaco + (tamNovo.toFloat() / 2)


                        in listOf(30) -> x =
                            espaco + (tamNovo.toFloat() * 4) + (tamNovo.toFloat() / 2)

                    }
                }



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