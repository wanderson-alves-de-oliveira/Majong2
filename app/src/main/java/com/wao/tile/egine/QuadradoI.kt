package com.wao.tile.egine

import android.content.Context
import android.graphics.Bitmap
import com.wao.tile.view.MahjongTile
import kotlin.random.Random

class QuadradoI {

    fun quadradoI(context: Context, w:Int, disponiveis: MutableList<Int>, tileImages: MutableList<Bitmap>) :MutableList<MahjongTile>{
        var initialize = 0
        var grau = 0
      val tiles = mutableListOf<MahjongTile>()

      val tamNovo = ((w * 0.9f) / 7).toInt()
        val espaco = w * 0.05f
        val h = tamNovo+0
        val w1 = tamNovo+0
        for (i in 0 until 40) {
            disponiveis.add(i)
        }
        val maximo = disponiveis.size
        val tamm = +(2*tamNovo.toFloat())

        for (i in 0 until 39) {
            if (i % 3 == 0) {
                initialize = -1
                grau++
                if (grau > ((maximo / 3) / 2)) {
                    grau = 1
                }
            }
            initialize++
            val index = Random.nextInt(disponiveis.size - 1)
            var camadaP: Int
            var x = 0f
            var y = 0f
            when (disponiveis[index]) {
                in 0..20 -> {
                    camadaP = 0
                    when (disponiveis[index]) {
                        in listOf(0, 1, 2) -> {
                            y = tamNovo.toFloat() * 1
                        }
                        in listOf(3, 4, 5) -> {
                            y = tamNovo.toFloat() * 2
                        }
                        in listOf(6, 7, 8) -> {
                            y = tamNovo.toFloat() * 3
                        }
                        in listOf(9, 10, 11) -> {
                            y = tamNovo.toFloat() * 4
                        }
                        in listOf(12, 13, 14) -> {
                            y = tamNovo.toFloat() * 5
                        }
                        in listOf(15, 16, 17) -> {
                            y = tamNovo.toFloat() * 6
                        }
                        in listOf(18, 19, 20) -> {
                            y = tamNovo.toFloat() * 7
                        }
                    }
                    y+=tamm
                    when (disponiveis[index]) {
                        in listOf(0, 3, 6, 9, 12, 15, 18) -> x =
                            espaco + tamNovo.toFloat() * 2
                        in listOf(1, 4, 7, 10, 13, 16, 19) -> x =
                            espaco + tamNovo.toFloat() * 3
                        in listOf(2, 5, 8,11,14,17,20) -> x =
                            espaco + tamNovo.toFloat() * 4


                    }
                }

                in 21..32 -> {
                    camadaP = 1

                    when (disponiveis[index]) {
                        in listOf(21, 22) -> {
                            y = tamNovo.toFloat() * 1.5f
                        }
                        in listOf(23, 24) -> {
                            y = tamNovo.toFloat() * 2.5f
                        }
                        in listOf(25, 26) -> {
                            y = tamNovo.toFloat() * 3.5f
                        }
                        in listOf(27, 28) -> {
                            y = tamNovo.toFloat() * 4.5f
                        }
                        in listOf(29, 30) -> {
                            y = tamNovo.toFloat() * 5.5f
                        }
                        in listOf(31, 32) -> {
                            y = tamNovo.toFloat() * 6.5f
                        }


                    }
                    y+=tamm
                    when (disponiveis[index]) {
                        in listOf(21,23,25,27,29,31) -> x =
                            espaco + tamNovo.toFloat() * 2.5f

                        in listOf(22,24,26,28,30,32) -> x =
                            espaco + tamNovo.toFloat() * 3.5f

                    }
                }

                in 33..38 -> {
                    camadaP = 2
                    when (disponiveis[index]) {
                        in listOf(33, 34,35,36,37,38) -> {
                            y = tamNovo.toFloat() *   ((disponiveis[index] -33)+1)
                        }

                    }
                    y+=tamm
                    when (disponiveis[index]) {
                        in  listOf(33, 34,35,36,37,38) -> x =
                            espaco + tamNovo.toFloat() * 3f


                    }
                }

                else -> {
                    camadaP = 2
                    y = espaco + (tamNovo.toFloat() * 4) + tamNovo.toFloat()
                    x = espaco + (tamNovo.toFloat() * 4) + tamNovo.toFloat()
                    y+=tamm
                }
            }


            disponiveis.remove(disponiveis[index])

            tiles.add(
                MahjongTile(
                    tileImages[grau - 1],
                    context,
                    x,
                    y,
                    w1,
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