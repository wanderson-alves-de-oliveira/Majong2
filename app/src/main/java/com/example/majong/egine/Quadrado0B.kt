package com.example.majong.egine

import android.content.Context
import android.graphics.Bitmap
import com.example.majong.view.MahjongTile
import kotlin.random.Random

class Quadrado0B {



    fun quadradoB(context: Context, w:Int, disponiveis: MutableList<Int>, tileImages: MutableList<Bitmap>) :MutableList<MahjongTile>{
        var initialize = 0
        var grau = 0
        var tiles = mutableListOf<MahjongTile>()

        val tamNovo = ((w * 0.9f) / 5).toInt()
        val espaco = (((w * 0.9f) / 6)).toInt()
        val h = tamNovo
        val w = tamNovo
        for (i in 0 until 25) {
            disponiveis.add(i)
        }
        var maximo = disponiveis.size

        for (i in 0 until 24) {
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
                in 0..15 -> {
                    camadaP = 1
                    when (disponiveis[index]) {
                        in 0..3 -> {
                            y = tamNovo.toFloat()*0.6f
                        }

                        in 4..7 -> {
                            y = (tamNovo.toFloat() * 2)*0.8f
                        }

                        in 8..11 -> {
                            y = tamNovo.toFloat() * 3
                        }

                        in 12..15 -> {
                            y = tamNovo.toFloat() * 4
                        }




                    }
                    when (disponiveis[index]) {
                        in listOf(0, 4, 8, 12) -> x = espaco  + tamNovo.toFloat() *0
                        in listOf(1, 5, 9, 13) -> x =
                            espaco + tamNovo.toFloat() * 1

                        in listOf(2, 6, 10, 14) -> x =
                            espaco + tamNovo.toFloat() * 2

                        in listOf(3, 7, 11, 15) -> x =
                            espaco + tamNovo.toFloat() * 3


                    }
                }

                in 16..23 -> {
                    camadaP = 2

                    when (disponiveis[index]) {
                        in 16..18 -> {
                            y = (tamNovo.toFloat() * 1)+ (tamNovo.toFloat() / 3)
                        }

                        in 19..21 -> {
                            y = (tamNovo.toFloat() * 3) + (tamNovo.toFloat() / 2)
                        }

                        in 22..23 -> {
                            y = (tamNovo.toFloat() * 2) + (tamNovo.toFloat() / 2)
                        }


                    }
                    when (disponiveis[index]) {
                        in listOf(16, 19) -> x =
                            espaco + (tamNovo.toFloat() * 0) + (tamNovo.toFloat() / 2)

                        in listOf(17, 20) -> x =
                            espaco + (tamNovo.toFloat() * 1) + (tamNovo.toFloat() / 2)

                        in listOf(18, 21) -> x =
                            espaco + (tamNovo.toFloat() * 2) + (tamNovo.toFloat() / 2)

                        in listOf(22) -> x =
                            espaco + (tamNovo.toFloat() * 0.6f) + (tamNovo.toFloat() / 2)
                        in listOf( 23) -> x =
                            espaco + (tamNovo.toFloat() * 1.6f) + (tamNovo.toFloat() / 2)

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