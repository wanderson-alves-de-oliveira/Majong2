package com.example.majong.egine

import android.content.Context
import android.graphics.Bitmap
import com.example.majong.view.MahjongTile
import kotlin.random.Random

class QuadradoO {

    fun quadrado(context: Context, w:Int, disponiveis: MutableList<Int>, tileImages: MutableList<Bitmap>) :MutableList<MahjongTile>{
        var initialize = 0
        var grau = 0
      var tiles = mutableListOf<MahjongTile>()

      val tamNovo = ((w * 0.9f) / 7).toInt()
        val espaco = w * 0.05f
        val h = tamNovo
        val w = tamNovo
        for (i in 0 until 82) {
            disponiveis.add(i)
        }
        var maximo = disponiveis.size

        for (i in 0 until 81) {
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
                in 0..32 -> {
                    camadaP = 0
                    when (disponiveis[index]) {
                        in 0..5 -> {
                            y = tamNovo.toFloat()
                        }

                        in 6..11 -> {
                            y = tamNovo.toFloat() * 2
                        }

                        in 12..15 -> {
                            y = tamNovo.toFloat() * 3
                        }

                        in 16..19 -> {
                            y = tamNovo.toFloat() * 4
                        }

                        in 20..26 -> {
                            y = tamNovo.toFloat() * 5
                        }

                        in 27..32 -> {
                            y = tamNovo.toFloat() * 6
                        }
                    }
                    when (disponiveis[index]) {
                        in listOf(0, 6, 12, 18, 24, 30) -> x = espaco
                        in listOf(1, 7, 13, 19, 25, 31) -> x =
                            espaco + tamNovo.toFloat() * 1

                        in listOf(2, 8,  20, 26, 32) -> x =
                            espaco + tamNovo.toFloat() * 2

                        in listOf(3, 9,  21, 27) -> x =
                            espaco + tamNovo.toFloat() * 3

                        in listOf(4, 10, 16,14, 22, 28) -> x =
                            espaco + tamNovo.toFloat() * 4

                        in listOf(5, 11, 17,15, 23, 29) -> x =
                            espaco + tamNovo.toFloat() * 5
                    }
                }

                in 33..64-> {
                    camadaP = 1
                    when (disponiveis[index]) {
                        in 33..38 -> {
                            y = tamNovo.toFloat()*1.5f
                        }

                        in 39..44 -> {
                            y = tamNovo.toFloat() * 2.5f
                        }

                        in 45..48 -> {
                            y = tamNovo.toFloat() * 3.5f
                        }

                        in 49..52 -> {
                            y = tamNovo.toFloat() * 4.5f
                        }

                        in 53..58 -> {
                            y = tamNovo.toFloat() * 5.5f
                        }

                        in 59..64 -> {
                            y = tamNovo.toFloat() * 6.5f
                        }
                    }
                    when (disponiveis[index]) {
                        in listOf(33, 34, 35, 36, 37, 38) -> x =
                            espaco + tamNovo.toFloat() * ((disponiveis[index] -33)+0.5f)
                        in listOf(39, 40, 41, 42, 43, 44) -> x =
                            espaco + tamNovo.toFloat() * ((disponiveis[index] -39)+0.5f)

                        in listOf(45, 46) -> x =
                            espaco + tamNovo.toFloat() * ((disponiveis[index] -45)+0.5f)
                        in listOf(47, 48) -> x =
                            espaco + tamNovo.toFloat() * ((disponiveis[index] -43)+0.5f)

                        in listOf(49, 50) -> x =
                            espaco + tamNovo.toFloat() * ((disponiveis[index] -49)+0.5f)
                        in listOf(51, 52) -> x =
                            espaco + tamNovo.toFloat() * ((disponiveis[index] -47)+0.5f)

                        in listOf(53, 54, 55,56,57,58) -> x =
                            espaco + tamNovo.toFloat() * ((disponiveis[index] -53)+0.5f)

                        in listOf(59, 60, 61,62,63,64) -> x =
                            espaco + tamNovo.toFloat() * ((disponiveis[index] -59)+0.5f)
                    }
                }

                in 65..80 -> {
                    camadaP = 2
                    when (disponiveis[index]) {
                        in listOf(65, 66,67,68,69) -> {
                            y = tamNovo.toFloat() *   ((disponiveis[index] -65)+2)
                        }

                        in listOf(70, 71,72,73,74) -> {
                            y = tamNovo.toFloat() *   ((disponiveis[index] -70)+2)
                        }
                        in listOf(75, 76,77) -> {
                            y = tamNovo.toFloat() *  2
                        }
                        in listOf(78, 79,80) -> {
                            y = tamNovo.toFloat() *  6
                        }
                    }
                    when (disponiveis[index]) {
                        in  listOf(65, 66,67,68,69)  -> x =
                            espaco + tamNovo.toFloat() * 1f

                        in listOf(70, 71,72,73,74)  -> x =
                            espaco + tamNovo.toFloat() * 5f

                        in  listOf(75, 76,77) -> x =
                            espaco + tamNovo.toFloat() *  ((disponiveis[index] -75)+2)


                        in  listOf(78, 79,80) -> x =
                            espaco + tamNovo.toFloat() *  ((disponiveis[index] -78)+2)

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