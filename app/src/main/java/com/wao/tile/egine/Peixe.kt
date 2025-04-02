package com.wao.tile.egine

import android.content.Context
import android.graphics.Bitmap
import com.wao.tile.view.MahjongTile
import kotlin.random.Random

class Peixe {

    fun quadrado(context: Context, w:Int, disponiveis: MutableList<Int>, tileImages: MutableList<Bitmap>) :MutableList<MahjongTile>{
        var initialize = 0
        var grau = 0
      var tiles = mutableListOf<MahjongTile>()

      val tamNovo = ((w * 0.9f) / 7).toInt()
        val espaco = w * 0.05f
        val h = tamNovo
        val w = tamNovo
        for (i in 0 until 67) {
            disponiveis.add(i)
        }
        var maximo = disponiveis.size
        val tamm = +(2*tamNovo.toFloat())

        for (i in 0 until 66) {
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
                    camadaP = 0
                    when (disponiveis[index]) {
                        in 0..2 -> {
                            y = tamNovo.toFloat()
                        }

                        in 3..7 -> {
                            y = tamNovo.toFloat() * 2
                        }

                        in 8..14 -> {
                            y = tamNovo.toFloat() * 3
                        }

                        in 15..17 -> {
                            y = tamNovo.toFloat() * 4
                        }

                        in 18..20 -> {
                            y = tamNovo.toFloat() * 5
                        }

                        in 21..21 -> {
                            y = tamNovo.toFloat() * 6
                        }
                        in 22..24 -> {
                            y = tamNovo.toFloat() * 7
                        }
                    }
                    y+=tamm
                    when (disponiveis[index]) {
                        in listOf( 8) -> x =
                            espaco + tamNovo.toFloat() * 0f
                        in listOf(3, 9) -> x =

                            espaco + tamNovo.toFloat() * 1.0f
                        in listOf(0, 4,10, 15, 18,22)  -> x =
                            espaco + tamNovo.toFloat() * 2.0f

                        in listOf(1, 21,5,11, 16,19, 23) -> x =
                            espaco + tamNovo.toFloat() * 3.0f
                        in listOf(2, 6,12, 17,20,24) -> x =
                            espaco + tamNovo.toFloat() * 4.0f
                        in listOf( 7,13) -> x =
                            espaco + tamNovo.toFloat() * 5.0f
                        in listOf( 14) -> x =
                            espaco + tamNovo.toFloat() * 6.0f

                    }
                }

                in 25..49 -> {
                    camadaP = 1
                    when (disponiveis[index]) {
                        in 25..27 -> {
                            y = tamNovo.toFloat() *1.5f
                        }

                        in 28..32 -> {
                            y = tamNovo.toFloat() * 2.5f
                        }

                        in 33..39 -> {
                            y = tamNovo.toFloat() * 3.5f
                        }

                        in 40..42 -> {
                            y = tamNovo.toFloat() * 4.5f
                        }

                        in 43..45 -> {
                            y = tamNovo.toFloat() * 5.5f
                        }

                        in 46..46 -> {
                            y = tamNovo.toFloat() * 6.5f
                        }
                        in 47..49 -> {
                            y = tamNovo.toFloat() * 7.5f
                        }
                    }
                    y+=tamm
                    when (disponiveis[index]) {
                        in listOf( 33) -> x =
                            espaco + tamNovo.toFloat() * 0.5f
                        in listOf(28, 34) -> x =

                            espaco + tamNovo.toFloat() * 1.5f
                        in listOf(25, 29,35, 40, 43,47)  -> x =
                            espaco + tamNovo.toFloat() * 2.5f

                        in listOf(26, 46,30,36, 41,44, 48) -> x =
                            espaco + tamNovo.toFloat() * 3.5f
                        in listOf(27, 31,37, 42,45,49) -> x =
                            espaco + tamNovo.toFloat() * 4.5f
                        in listOf( 32,38) -> x =
                            espaco + tamNovo.toFloat() * 5.5f
                        in listOf( 39) -> x =
                            espaco + tamNovo.toFloat() * 6.5f

                    }

                }

                in 50..65 -> {
                    camadaP = 2
                    when (disponiveis[index]) {
                        in listOf( 50,51)-> {
                            y = tamNovo.toFloat() *2f
                        }
                        in 52..57 -> {
                            y = tamNovo.toFloat() *3f
                        }
                        in listOf( 58,59)-> {
                            y = tamNovo.toFloat() *4f
                        }
                        in listOf( 60,61)-> {
                            y = tamNovo.toFloat() *5f
                        }
                        in listOf( 62,63)-> {
                            y = tamNovo.toFloat() *7f
                        }
                        in listOf( 64)-> {
                            y = tamNovo.toFloat() *6f
                        }
                        in listOf( 65)-> {
                            y = tamNovo.toFloat() *1f
                        }
                    }
                    y+=tamm
                    when (disponiveis[index]) {
                        in listOf( 50,58,60,62) -> x =
                            espaco + tamNovo.toFloat() * 3f
                        in listOf(51,59,61,63) -> x =
                            espaco + tamNovo.toFloat() * 4f
                        in listOf(52,53,54,55,56,57) -> x =
                            espaco + tamNovo.toFloat() *  ((disponiveis[index] -52)+1f)
                        in listOf(64) -> x =
                            espaco + tamNovo.toFloat() * 3.8f
                        in listOf(65) -> x =
                            espaco + tamNovo.toFloat() * 3.8f
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