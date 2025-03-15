package com.example.majong.egine

import android.content.Context
import android.graphics.Bitmap
import com.example.majong.view.MahjongTile
import kotlin.random.Random

class Quadrado01 {



    fun quadrado(context: Context, w:Int, disponiveis: MutableList<Int>, tileImages: MutableList<Bitmap>) :MutableList<MahjongTile>{
        var initialize = 0
        var grau = 0
        var tiles = mutableListOf<MahjongTile>()

        val tamNovo = ((w * 0.9f) / 6).toInt()
        val espaco = w * 0.05f
        val h = tamNovo
        val w = tamNovo
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
            var camadaP = 0
            var x = 0f
            var y = 0f
            when (disponiveis[index]) {
                in 0..24 -> {
                    camadaP = 1
                    when (disponiveis[index]) {
                        in 0..4 -> {
                            y = tamNovo.toFloat()
                        }

                        in 5..9 -> {
                            y = tamNovo.toFloat() * 2
                        }

                        in 10..14 -> {
                            y = tamNovo.toFloat() * 3
                        }

                        in 15..19 -> {
                            y = tamNovo.toFloat() * 4
                        }

                        in 20..24 -> {
                            y = tamNovo.toFloat() * 5
                        }
//                        in 25..29 -> {
//                            y = tamNovo.toFloat() * 6
//                        }

                    }
                    when (disponiveis[index]) {
                        in listOf(0, 5, 10, 15, 20) -> x =
                            espaco  + tamNovo.toFloat() *0.5f
                        in listOf(1, 6, 11, 16, 21) -> x =
                            espaco + tamNovo.toFloat() * 1.5f

                        in listOf(2, 7, 12, 17, 22) -> x =
                            espaco + tamNovo.toFloat() * 2.5f

                        in listOf(3, 8, 13, 18, 23) -> x =
                            espaco + tamNovo.toFloat() * 3.5f
                        in listOf(4, 9, 14, 19, 24) -> x =
                            espaco + tamNovo.toFloat() * 4.5f

                    }
                }

                in 25..41 -> {
                    camadaP = 2

                    when (disponiveis[index]) {
                        in 25..28 -> {
                            y = (tamNovo.toFloat() * 1)+ (tamNovo.toFloat() / 2)
                        }

                        in 29..32 -> {
                            y = (tamNovo.toFloat() * 4)+ (tamNovo.toFloat() / 2)
                        }

                        in 33..35 -> {
                            y = (tamNovo.toFloat() * 2)+ (tamNovo.toFloat() / 2)
                        }

                        in 36..38 -> {
                            y = (tamNovo.toFloat() * 3)+ (tamNovo.toFloat() / 2)
                        }

                    }
                    when (disponiveis[index]) {
                        in listOf(25,26, 27, 28) -> x =
                            espaco + (tamNovo.toFloat() *  ((disponiveis[index] -25f)+0.5f)) + (tamNovo.toFloat() / 2)

                        in listOf(29, 30, 31, 32) -> x =
                            espaco + (tamNovo.toFloat() * ((disponiveis[index] -29)+0.5f)) + (tamNovo.toFloat() / 2)

                        in listOf(33,36) -> x =
                            espaco + (tamNovo.toFloat() * 0.5f) + (tamNovo.toFloat() / 2)


                        in listOf(34,37) -> x =
                            espaco + (tamNovo.toFloat() * 2.1f) + (tamNovo.toFloat() / 2)

                        in listOf(35,38) -> x =
                            espaco + (tamNovo.toFloat() * 3.5f) + (tamNovo.toFloat() / 2)

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