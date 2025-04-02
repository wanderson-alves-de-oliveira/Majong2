package com.wao.tile.egine

import android.content.Context
import android.graphics.Bitmap
import com.wao.tile.view.MahjongTile
import kotlin.random.Random

class Cabeca0A {



    fun cabeca(context: Context, w:Int, disponiveis: MutableList<Int>, tileImages: MutableList<Bitmap>) :MutableList<MahjongTile>{
        var initialize = 0
        var grau = 0
        var tiles = mutableListOf<MahjongTile>()

        val tamNovo = ((w * 0.9f) / 6).toInt()
        val espaco = w * 0.05f
        val h = tamNovo
        val w = tamNovo
        val tamm = +(2*tamNovo.toFloat())

        for (i in 0 until 43) {
            disponiveis.add(i)
        }
        var maximo = disponiveis.size

        for (i in 0 until 42) {
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
                in 0..25 -> {
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

                        in 16..19 -> {
                            y = tamNovo.toFloat() * 5
                        }
                        in 20..21 -> {
                            y = tamNovo.toFloat() * 2
                        }
                        in 22..23 -> {
                            y = tamNovo.toFloat() * 3
                        }
                        in 24..25 -> {
                            y = tamNovo.toFloat() * 6
                        }
                    }
                    y+=tamm
                    when (disponiveis[index]) {
                        in listOf(0, 4, 8, 12, 16) -> x = espaco  + tamNovo.toFloat() *1
                        in listOf(1, 5, 9, 13, 17,24) -> x =
                            espaco + tamNovo.toFloat() * 2

                        in listOf(2, 6, 10, 14, 18,25) -> x =
                            espaco + tamNovo.toFloat() * 3

                        in listOf(3, 7, 11, 15, 19) -> x =
                            espaco + tamNovo.toFloat() * 4
                        in listOf(20,22) -> x =
                            espaco
                        in listOf(21,23) -> x =
                            espaco + tamNovo.toFloat() * 5

                    }
                }

                in 26..41 -> {
                    camadaP = 2

                    when (disponiveis[index]) {
                        in 26..28 -> {
                            y = (tamNovo.toFloat() * 1)+ (tamNovo.toFloat() / 2)
                        }

                        in 29..31 -> {
                            y = (tamNovo.toFloat() * 2) + (tamNovo.toFloat() / 2)
                        }

                        in 32..34 -> {
                            y = (tamNovo.toFloat() * 3) + (tamNovo.toFloat() / 2)
                        }

                        in 35..37 -> {
                            y = (tamNovo.toFloat() * 4) + (tamNovo.toFloat() / 2)
                        }

                        in 38..39 -> {
                            y = (tamNovo.toFloat() * 2) + (tamNovo.toFloat() / 2)
                        }
                        in 40..40 -> {
                            y = (tamNovo.toFloat() * 5) + (tamNovo.toFloat() / 2)
                        }
                        in 41..41 -> {
                            y =(tamNovo.toFloat() / 2)
                        }
                    }
                    y+=tamm
                    when (disponiveis[index]) {
                        in listOf(26, 29, 32, 35) -> x =
                            espaco + (tamNovo.toFloat() * 1) + (tamNovo.toFloat() / 2)

                        in listOf(27, 30, 33, 36,40,41) -> x =
                            espaco + (tamNovo.toFloat() * 2) + (tamNovo.toFloat() / 2)

                        in listOf(28, 31, 34, 37) -> x =
                            espaco + (tamNovo.toFloat() * 3) + (tamNovo.toFloat() / 2)

                        in listOf(38) -> x =
                            espaco + (tamNovo.toFloat() / 2)

                        in listOf(39) -> x =
                            espaco + (tamNovo.toFloat() * 4) + (tamNovo.toFloat() / 2)

                        in listOf(40) -> x =
                            espaco + (tamNovo.toFloat() * 4) + (tamNovo.toFloat() / 2)


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
                    context ,
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