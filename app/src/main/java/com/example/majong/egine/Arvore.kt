package com.example.majong.egine

import android.graphics.Bitmap
import com.example.majong.view.MahjongTile
import kotlin.random.Random

class Arvore {

    fun arvore(w:Int,disponiveis: MutableList<Int>,tileImages: MutableList<Bitmap>) :MutableList<MahjongTile>{
        var initialize = 0
        var grau = 0
      var tiles = mutableListOf<MahjongTile>()

      val tamNovo = ((w * 0.9f) / 6).toInt()
        val espaco = w * 0.05f
        val h = tamNovo
        val w = tamNovo
        for (i in 0 until 49) {
            disponiveis.add(i)
        }
        var maximo = disponiveis.size

        for (i in 0 until 48) {
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
                in 0..23 -> {
                    camadaP = 0
                    when (disponiveis[index]) {
                        in 0..3 -> {
                            y = tamNovo.toFloat()* 2
                        }

                        in 4..7 -> {
                            y = tamNovo.toFloat() * 3
                        }

                        in 8..11 -> {
                            y = tamNovo.toFloat() * 4
                        }

                        in 12..15 -> {
                            y = tamNovo.toFloat() * 5
                        }
                        in 16..17 -> {
                            y = tamNovo.toFloat() * 6
                        }
                        in 18..19 -> {
                            y = tamNovo.toFloat() * 1
                        }
                        in 20..21 -> {
                            y = tamNovo.toFloat() * 4
                        }
                        in 22..23 -> {
                            y = tamNovo.toFloat() * 5
                        }
                    }
                    when (disponiveis[index]) {
                        in listOf(0, 4, 8, 12) -> x =
                            espaco + tamNovo.toFloat() * 1
                        in listOf(1, 5, 9, 13) -> x =
                            espaco + tamNovo.toFloat() * 2

                        in listOf(2, 6, 10, 14) -> x =
                            espaco + tamNovo.toFloat() * 3

                        in listOf(3, 7, 11, 15) -> x =
                            espaco + tamNovo.toFloat() * 4
                        in listOf(16,18) -> x =
                            espaco + tamNovo.toFloat() * 2
                        in listOf(17,19) -> x =
                            espaco + tamNovo.toFloat() * 3

                        in listOf(20,22) -> x =
                            espaco
                        in listOf(21,23) -> x =
                            espaco + tamNovo.toFloat() * 5


                    }
                }

                in 24..36 -> {
                    camadaP = 1

                    when (disponiveis[index]) {
                        in 24..26 -> {
                            y = (tamNovo.toFloat() * 2) + (tamNovo.toFloat() / 2)
                        }

                        in 27..29 -> {
                            y = (tamNovo.toFloat() * 3) + (tamNovo.toFloat() / 2)
                        }

                        in 30..32 -> {
                            y = (tamNovo.toFloat() * 4) + (tamNovo.toFloat() / 2)
                        }
                        in 33..34 -> {
                            y = (tamNovo.toFloat() * 4) + (tamNovo.toFloat() / 2)
                        }
                        in 35..35 -> {
                            y = (tamNovo.toFloat() * 1) + (tamNovo.toFloat() / 2)
                        }
                        in 36..36 -> {
                            y = (tamNovo.toFloat() * 5) + (tamNovo.toFloat() / 2)
                        }

                    }
                    when (disponiveis[index]) {
                        in listOf(24, 27, 30) -> x =
                            espaco + (tamNovo.toFloat() * 1) + (tamNovo.toFloat() / 2)

                        in listOf(25, 28, 31) -> x =
                            espaco + (tamNovo.toFloat() * 2) + (tamNovo.toFloat() / 2)

                        in listOf(26, 29, 32) -> x =
                            espaco + (tamNovo.toFloat() * 3) + (tamNovo.toFloat() / 2)

                        in listOf(33) -> x =
                            espaco + (tamNovo.toFloat() / 2)


                        in listOf(34) -> x =
                            espaco + (tamNovo.toFloat() * 4) + (tamNovo.toFloat() / 2)

                        in listOf(35,36) -> x =
                            espaco + (tamNovo.toFloat() * 2) + (tamNovo.toFloat() / 2)

                    }
                }

                in 37..47 -> {
                    camadaP = 2
                    when (disponiveis[index]) {
                        in 37..38 -> {
                            y = tamNovo.toFloat() + tamNovo.toFloat()
                        }

                        in 39..40 -> {
                            y = (tamNovo.toFloat() * 2) + tamNovo.toFloat()
                        }

                        in 41..42 -> {
                            y = (tamNovo.toFloat() * 3) + tamNovo.toFloat()
                        }

                        in 43..44 -> {
                            y = (tamNovo.toFloat() * 4) + tamNovo.toFloat()
                        }

                        in 45..46 -> {
                            y = (tamNovo.toFloat() * 3.6f) + tamNovo.toFloat()
                        }

                        in 47..47 -> {
                            y =  tamNovo.toFloat()*0.7f
                        }
                    }
                    when (disponiveis[index]) {
                        in listOf(37, 39, 41, 43) -> x =
                            espaco + (tamNovo.toFloat() * 1) + tamNovo.toFloat()

                        in listOf(38, 40, 42, 44) -> x =
                            espaco + (tamNovo.toFloat() * 2) + tamNovo.toFloat()

                        in listOf(45) -> x =
                            espaco+ tamNovo.toFloat()

                        in listOf(46) -> x =
                            espaco + (tamNovo.toFloat() * 3) + tamNovo.toFloat()
                        in listOf(47) -> x =
                            espaco + (tamNovo.toFloat() * 1.6f) + tamNovo.toFloat()
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