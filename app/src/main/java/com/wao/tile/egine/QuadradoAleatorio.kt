package com.wao.tile.egine

import android.content.Context
import android.graphics.Bitmap
import androidx.compose.ui.geometry.Offset
import com.wao.tile.view.MahjongTile
import kotlin.random.Random

class QuadradoAleatorio {


    fun quadrado(context: Context,
                 w: Int,
                 disponiveis: MutableList<Int>,
                 tileImages: MutableList<Bitmap>
    ): MutableList<MahjongTile> {



        var initialize = 0
        var grau = 0
        val tiles = mutableListOf<MahjongTile>()

        val pY = mutableListOf<Float>()
        val pX = mutableListOf<Float>()
        val pXY0 = mutableListOf<Offset>()
        val pXY1 = mutableListOf<Offset>()
        val pXY2 = mutableListOf<Offset>()
        val ran = mutableListOf<Int>()
        for (i in 20 until 79) {
            if(i%3==0){
                ran.add(i)
            }
        }
        ran.shuffle()
      //  ran[0] = 78
        val tamNovo = ((w * 0.9f) / 6.5f).toInt()
        val espaco = w * 0.03f
        val h = tamNovo+0
        //val w = tamNovo+0

        for (i in 0 until 6) {
            pY.add(tamNovo.toFloat() * (i + 3))


                pX.add(espaco + (tamNovo.toFloat() * i))

        }

        for (i in 0 until pY.size) {
            for (j in 0 until pX.size) {
                val o = Offset(pX[j], pY[i])
                val o1 = Offset(
                    pX[j] + (tamNovo.toFloat() * (0.5f)),
                    pY[i] + (tamNovo.toFloat() * (0.5f))
                )
                val o2 = Offset(
                    pX[j] + (tamNovo.toFloat() * (0.75f)),
                    pY[i] + (tamNovo.toFloat() * (0.75f))
                )

                pXY0.add(o)
                pXY1.add(o1)
                pXY2.add(o2)

            }
        }
        pXY0.shuffle()
        pXY1.shuffle()
        pXY2.shuffle()
        var camada0 = 0
        var camada1 = 0
        var camada2 = 0
        val resultado =   gerarValoresAleatorios(ran[0])

        val index0 = resultado.first
        val index1 = resultado.first+resultado.second

        for (i in 0 until ran[0]+1) {
            disponiveis.add(i)
        }
        val maximo = disponiveis.size


        for (i in 0 until ran[0]) {
            if (i % 3 == 0) {
                initialize = -1
                grau++
                if (grau > ((maximo / 3) / 2)) {
                    grau = 1
                }
            }
            initialize++
            val index = Random.nextInt(disponiveis.size - 1)
            var camadaP = 2
            var x = 0f
            var y = 0f
            when (disponiveis[index]) {
                in 0..index0 -> {
                    camadaP = 0

                    y = pXY0[camada0].y

                    x = pXY0[camada0].x
                    if (camada0 < pXY0.size-1) {

                        camada0++
                    }
                }

                in index0+1..index1 -> {
                    camadaP = 1
                    y = pXY1[camada1].y

                    x = pXY1[camada1].x

                    if (camada1 < pXY1.size-1) {
                        camada1++
                    }

                }

                in index1+1..ran[0] -> {
                    camadaP = 2
                    y = pXY2[camada2].y

                    x = pXY2[camada2].x

                    if (camada2 < pXY2.size-1) {

                        camada2++
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

 //           var t0 =tiles.filter { it.camada==0 }

//            var t01 =tiles.filter { it.camada==0 }
//
//            for (i in 0 until t0.size-1){
//
//
//                for (j in 1 until t01.size){
//                    if(t0[i].x==t01[j].x && t0[i].y==t01[j].y){
//                        tiles.get(tiles.indexOf(t0[i])).x = tiles.get(tiles.indexOf(t0[i])).x-(tamNovo.toFloat() * (0.8f))
//                        tiles.get(tiles.indexOf(t0[i])).y = tiles.get(tiles.indexOf(t0[i])).y+(tamNovo.toFloat() * (0.8f))
//                    }
//                }
//            }
//
//
//            var t1 =tiles.filter { it.camada==1 }
//
//            var t11 =tiles.filter { it.camada==1 }
//
//            for (i in 0 until t1.size-1){
//
//
//                for (j in 1 until t11.size){
//                    if(t1[i].x==t11[j].x && t1[i].y==t11[j].y){
//                        tiles.get(tiles.indexOf(t1[i])).x =
//                        if(tiles.get(tiles.indexOf(t1[i])).x-(tamNovo.toFloat() * (0.8f)) >=espaco)
//                            tiles.get(tiles.indexOf(t1[i])).x-(tamNovo.toFloat() * (0.8f))
//                        else espaco+ (tamNovo.toFloat() * (0.4f).toFloat())
//                        tiles.get(tiles.indexOf(t1[i])).y = tiles.get(tiles.indexOf(t1[i])).y+(tamNovo.toFloat() * (0.8f))
//                    }
//                }
//            }


//            var t2 =tiles.filter { it.camada==2 }
//
//            var t21 =tiles.filter { it.camada==2 }
//
//            for (i in 0 until t2.size-1){
//
//
//                for (j in 1 until t21.size){
//                    if(t2[i].x==t21[j].x && t2[i].y==t21[j].y){
//                        tiles.get(tiles.indexOf(t2[i])).x =
//                            if(tiles.get(tiles.indexOf(t2[i])).x-(tamNovo.toFloat() * (0.8f)) >=espaco)
//                                tiles.get(tiles.indexOf(t2[i])).x-(tamNovo.toFloat() * (0.8f))
//                        else espaco+ (tamNovo.toFloat() * (0.5f).toFloat())
//                        tiles.get(tiles.indexOf(t2[i])).y = tiles.get(tiles.indexOf(t2[i])).y+(tamNovo.toFloat() * (0.8f))
//                    }
//                }
//            }


        }



        return tiles

    }

    private fun gerarValoresAleatorios(valor: Int): Triple<Int, Int, Int>{


        val random = Random.Default
        var valor1: Int
        var valor2: Int
        var valor3: Int

        do {
            valor1 = random.nextInt(1, 36) // Gera um valor aleatório entre 1 e 35
            valor2 = random.nextInt(1, 36) // Gera outro valor aleatório entre 1 e 35
            valor3 = valor - valor1 - valor2 // Calcula o terceiro valor

        } while (valor3 <= 0 || valor3 >= 36) // Garante que o terceiro valor esteja dentro do intervalo

        return Triple(valor1, valor2, valor3)
    }

}