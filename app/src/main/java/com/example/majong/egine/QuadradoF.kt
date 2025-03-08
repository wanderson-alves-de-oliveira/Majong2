package com.example.majong.egine

import android.graphics.Bitmap
import com.example.majong.view.MahjongTile
import kotlin.random.Random

class QuadradoF {

    fun quadrado(w:Int, disponiveis: MutableList<Int>, tileImages: MutableList<Bitmap>) :MutableList<MahjongTile>{
        var initialize = 0
        var grau = 0
      var tiles = mutableListOf<MahjongTile>()

      val tamNovo = ((w * 0.9f) / 7).toInt()
        val espaco = w * 0.05f
        val h = tamNovo
        val w = tamNovo
        for (i in 0 until 76) {
            disponiveis.add(i)
        }
        var maximo = disponiveis.size

        for (i in 0 until 75) {
            if (i % 3 == 0) {
                initialize = -1
                grau++
                if (grau > ((maximo / 3) / 2).toInt()) {
                    grau = 1
                }
            }
            initialize++
            val index = Random.nextInt(disponiveis.size - 1)
            var camadaP = 2
            var x = 0f
            var y = 0f
            when (disponiveis[index]) {
                in 0..35 -> {
                    camadaP = 0
                    when (disponiveis[index]) {
                        in listOf(0, 1, 2,24,25,26) -> {
                            y = tamNovo.toFloat() * 1
                        }
                        in listOf(3, 4, 5,27,28,29) -> {
                            y = tamNovo.toFloat() * 2
                        }
                        in listOf(6, 7, 8) -> {
                            y = tamNovo.toFloat() * 3
                        }
                        in listOf(9, 10, 11,30,31,32) -> {
                            y = tamNovo.toFloat() * 4
                        }
                        in listOf(12, 13, 14,33,34,35) -> {
                            y = tamNovo.toFloat() * 5
                        }
                        in listOf(15, 16, 17) -> {
                            y = tamNovo.toFloat() * 6
                        }
                        in listOf(18, 19, 20) -> {
                            y = tamNovo.toFloat() * 7
                        }
                        in listOf(21, 22, 23) -> {
                            y = tamNovo.toFloat() * 8
                        }
                    }
                    when (disponiveis[index]) {
                        in listOf(0, 3, 6, 9, 12, 15, 18,21) -> x =
                            espaco + tamNovo.toFloat() * 0
                        in listOf(1, 4, 7, 10, 13, 16, 19,22) -> x =
                            espaco + tamNovo.toFloat() * 1
                        in listOf(2, 5, 8,11,14,17,20,23) -> x =
                            espaco + tamNovo.toFloat() * 2

                        in listOf(24, 27, 30,33) -> x =
                            espaco + tamNovo.toFloat() * 3

                        in listOf(25, 28, 31,34) -> x =
                            espaco + tamNovo.toFloat() * 4

                        in listOf(26, 29, 32,35) -> x =
                            espaco + tamNovo.toFloat() * 5

                    }
                }

                in 36..61 -> {
                    camadaP = 1

                    when (disponiveis[index]) {
                        in listOf(36, 37, 38,39,40) -> {
                            y = tamNovo.toFloat() * 1.5f
                        }

                        in listOf(41, 42, 43,44,45) -> {
                            y = tamNovo.toFloat() * 2.5f
                        }

                        in listOf(46, 47) -> {
                            y = tamNovo.toFloat() * 3.5f
                        }
                        in listOf(48, 49, 50,51,52) -> {
                            y = tamNovo.toFloat() * 4.5f
                        }
                        in listOf(53, 54, 55,56,57) -> {
                            y = tamNovo.toFloat() * 5.5f
                        }

                        in listOf(58, 59) -> {
                            y = tamNovo.toFloat() * 6.5f
                        }
                        in listOf(60, 61) -> {
                            y = tamNovo.toFloat() * 7.5f
                        }
                    }
                    when (disponiveis[index]) {
                        in listOf(36, 37, 38,39,40)-> x =
                            espaco + tamNovo.toFloat() * ((disponiveis[index] -36)+0.5f)
                        in listOf(41, 42, 43,44,45)-> x =
                            espaco + tamNovo.toFloat() * ((disponiveis[index] -41)+0.5f)
                        in listOf(46,47)-> x =
                            espaco + tamNovo.toFloat() * ((disponiveis[index] -46)+0.5f)
                        in listOf(48, 49, 50,51,52) -> x =
                            espaco + tamNovo.toFloat() * ((disponiveis[index] -48)+0.5f)
                        in listOf(53, 54, 55,56,57)  -> x =
                            espaco + tamNovo.toFloat() * ((disponiveis[index] -53)+0.5f)

                        in listOf(58, 59) -> x =
                            espaco + tamNovo.toFloat() * ((disponiveis[index] -58)+0.5f)

                        in listOf(60, 61) -> x =
                            espaco + tamNovo.toFloat() * ((disponiveis[index] -60)+0.5f)
                    }
                }

                in 62..77 -> {
                    camadaP = 2
                    when (disponiveis[index]) {
                        in listOf(62, 63,64,65,66,67) -> {
                            y = tamNovo.toFloat() *   ((disponiveis[index] -62)+2)
                        }

                        in listOf(68, 69,70) -> {
                            y = tamNovo.toFloat() *  2
                        }
                        in listOf(71, 72,73) -> {
                            y = tamNovo.toFloat() *  5
                        }
                        in listOf(74) -> {
                            y = tamNovo.toFloat() *  8
                        }
                    }
                    when (disponiveis[index]) {
                        in  listOf(62, 63,64,65,66,67,74)  -> x =
                            espaco + tamNovo.toFloat() * 1f

                        in  listOf(68, 69,70)  -> x =
                            espaco + tamNovo.toFloat() *  ((disponiveis[index] -68)+2)

                        in  listOf(71, 72,73) -> x =
                            espaco + tamNovo.toFloat() *  ((disponiveis[index] -71)+2)

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