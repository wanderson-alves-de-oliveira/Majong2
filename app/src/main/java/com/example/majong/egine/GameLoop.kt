package com.example.majong.egine

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.media.MediaPlayer
import android.util.DisplayMetrics
import android.view.MotionEvent
import android.view.SurfaceHolder
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.drawText
import com.example.majong.R
import com.example.majong.view.Botao
import com.example.majong.view.MahjongTile
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.random.Random

class GameLoop(private val surfaceHolder: SurfaceHolder, private val context: Context) : Thread() {
    private var running = false

    private var lastTime = System.nanoTime()
    private val targetFps = 60
    private var timet = 5
    private var toque = 0

    private val optimalTime = 1_000_000_000 / targetFps
    private var touchX: Float = 0f
    private var touchY: Float = 0f
    private var isTouched = false
    private var dica = false
    private var songs: Int = 0
    private var efeitoSonoro: MediaPlayer = MediaPlayer.create(this.context, R.raw.dim)
    private var efeitoSonoro2: MediaPlayer = MediaPlayer.create(this.context, R.raw.finalyy)

    var tiles = mutableListOf<MahjongTile>()
    var selectedTiles = mutableListOf<MahjongTile>()
    var selectedTilesBase = mutableListOf<Botao>()
    private val paint = Paint()

    private val display: DisplayMetrics = context.resources.displayMetrics
    private val h = display.heightPixels
    private val w = display.widthPixels
    private val hp = 0
    private val wp = 0
    var tamPadrao = 0
    var tamNovo = 0
    var ajustarY = true
    var bloquerBT = false
    var bloquerBT2 = false

    var time1 = 0
    var time2 = 0
    var time3 = 0

    val divisor = 3
    var tileImage = BitmapFactory.decodeResource(context.resources, R.drawable.mahjongtile)
    var lampada = BitmapFactory.decodeResource(context.resources, R.drawable.lampada)
    var ima = BitmapFactory.decodeResource(context.resources, R.drawable.ima)
    var giro = BitmapFactory.decodeResource(context.resources, R.drawable.giro)

    // var  walld = Bitmap.createScaledBitmap(wall, (w).toInt(), (h).toInt(), false)
    var walld: MutableList<Bitmap> = mutableListOf()
    var tileImages = mutableListOf<Bitmap>()
    val botao1 = MahjongTile(
        lampada,
        (((w * 0.02f) + (w * 0.9f) / 6) * 1).toFloat(),
        h * 0.65f,
        ((w * 0.8f) / 6).toInt(),
        ((w * 0.9f) / 6).toInt(),
        2,
        5000
    )
    val botao2 = MahjongTile(
        ima,
        (((w * 0.02f) + (w * 0.9f) / 6) * 2).toFloat(),
        h * 0.65f,
        ((w * 0.8f) / 6).toInt(),
        ((w * 0.9f) / 6).toInt(),
        2,
        2000
    )
    val botao3 = MahjongTile(
        giro,
        (((w * 0.02f) + (w * 0.9f) / 6) * 3).toFloat(),
        h * 0.65f,
        ((w * 0.8f) / 6).toInt(),
        ((w * 0.9f) / 6).toInt(),
        2,
        3000
    )
    val IA_LIMIT = 50
    var timeValidarIA = IA_LIMIT

    fun startLoop() {
        running = true
        start()
    }

    fun stopLoop() {
        running = false
        join()
    }

    override fun run() {


        while (running) {
            val now = System.nanoTime()
            val deltaTime = (now - lastTime) / 1_000_000_000.0 // Convertendo para segundos
            lastTime = now
try {
    update(deltaTime)

}catch (e:Exception){

}




            render()
            handleInput()

            val sleepTime = optimalTime - (System.nanoTime() - now)
            if (sleepTime > 0) {
                Thread.sleep(sleepTime / 1_000_000)
            }
        }
    }

    private fun update(deltaTime: Double) {


        while (running) {


                    var canvas: Canvas? = null
                    try {
                        canvas = surfaceHolder.lockCanvas()



                        if (canvas != null) {
                            try {

                                    validarSelecao(selectedTiles.filter { it.camada !=-5}.toMutableList())

                                bloquerBT =
                                    selectedTiles.filter { it.camada < -2 && it.camada >= -4 }
                                        .isEmpty()
                                if (selectedTiles.filter { it.camada > -2 }.size < 7 || selectedTiles.filter { it.camada > -2 }.size >7 && dica) {

//
                                    if (!ajustarY) {
                                        timeValidarIA--
                                        if (timeValidarIA < 0) {
                                            timeValidarIA = IA_LIMIT
                                        }
                                    }
                                    // canvas.drawRGB(0, 128, 0) // Fundo verde
                                    canvas.drawBitmap(walld[0], 0f, 0f, paint)

                                        try {


                                            if (!bloquerBT) {
                                                botao1.camada = 1
                                                botao2.camada = 1
                                                botao3.camada = 1

                                            } else {
                                                botao1.camada = 2
                                                botao2.camada = 2
                                                botao3.camada = 2
                                                botao1.isSelected = false
                                                botao2.isSelected = false
                                                botao3.isSelected = false


                                            }

                                            if (time1 > 0) {
                                                botao1.camada = 1
                                                botao1.isSelected = true
                                                time1--
                                            }else{
                                                bloquerBT2 = true

                                            }
                                            if (time2 > 0 ) {
                                                botao2.camada = 1
                                                botao2.isSelected = true

                                                time2--
                                            }else{
                                                bloquerBT2 = true

                                            }
                                            if (time3 > 0) {
                                                botao3.camada = 1
                                                botao3.isSelected = true

                                                time3--
                                            }else{
                                                bloquerBT2 = true

                                            }
                                            botao1.draw(canvas)
                                            botao2.draw(canvas)
                                            botao3.draw(canvas)

                                        }catch (e:Exception){
                                            e.stackTrace
                                        }


//                            runBlocking {
//                                launch(Dispatchers.Default) {
                                    paint.color = Color.LightGray.toArgb()
                                    paint.alpha = 100
                                    canvas.drawRoundRect(
                                        RectF(
                                            (w * 0.08).toFloat(),
                                            (selectedTilesBase[0].y - (w * 0.03).toFloat()).toFloat(),
                                            (w * 0.93).toFloat(),
                                            (selectedTilesBase[0].y).toFloat() + (selectedTilesBase[0].h * 1.22f).toFloat()
                                        ), 30f, 30f, paint
                                    )
                                    paint.color = Color(0xFF2F4F4F).toArgb()

                                    canvas.drawRoundRect(
                                        RectF(
                                            (w * 0.09).toFloat(),
                                            (selectedTilesBase[0].y - (w * 0.02).toFloat()).toFloat(),
                                            (w * 0.92).toFloat(),
                                            (selectedTilesBase[0].y).toFloat() + (selectedTilesBase[0].h * 1.15f).toFloat()
                                        ), 30f, 30f, paint
                                    )

                                    selectedTilesBase.forEach { it.draw(canvas) }
//                                }
//                            }

//////////////////////////////////////////////

                                    runBlocking {




//////////////////////////////////////////////////////////////



                                            ///////////////////






                                        launch(Dispatchers.Default) {


                                            tiles.forEach {

                                                if (ajustarY) {
                                                    val vel = (it.yp - it.y) / divisor
                                                    if (it.y < it.yp) {

                                                        it.y += if (vel > w * 0.1f) vel else w * 0.1f

                                                        if (it.y > it.yp) {
                                                            it.y = it.yp
                                                            it.ty = true
                                                        }


                                                    }
                                                }
                                                if(!(it.w<=0 || it.h<=0) ) {
                                                    it.draw(canvas)
                                                }
                                                // }

                                            }

                                            if (tiles.filter { !selectedTiles.filter { it.camada !=-5}.contains(it) }
                                                    .filter { it.ty == false }.isEmpty()) {
                                                ajustarY = false

                                            }


                                            val novoFiltro2 =
                                                tiles.filter { !selectedTiles.filter { it.camada !=-5}.contains(it) }
                                                    .filter { it.camada == 2 }
                                            val novoFiltro1 =
                                                tiles.filter { !selectedTiles.filter { it.camada !=-5}.contains(it) }
                                                    .filter { it.camada == 1 }
                                            val novoFiltro0 =
                                                tiles.filter { !selectedTiles.filter { it.camada !=-5}.contains(it) }
                                                    .filter { it.camada == 0 }

                                            validarCamadas(
                                                novoFiltro1.toMutableList(),
                                                novoFiltro2.toMutableList(),
                                                2
                                            )
                                            validarCamadas(
                                                novoFiltro0.toMutableList(),
                                                novoFiltro1.toMutableList(),
                                                1
                                            )





                                            try {


                                                var i = 0
                                                val velocidade = w * 0.15f

                                                selectedTiles.filter { it.camada > -3 }.forEach {
                                                    var p = ((w * 0.9 / 7) * i).toFloat()
                                                    if (it.x > p) {
                                                        it.x -= velocidade
                                                        if (it.x < p) {
                                                            it.x = p
                                                        }
                                                    } else if (it.x < p) {
                                                        it.x += velocidade
                                                        if (it.x > p) {
                                                            it.x = p
                                                        }
                                                    }
                                                    i++


                                                    // } else {
                                                    p = ((w * 0.9 / 8) * i).toFloat()
                                                    val py = (h * 0.75).toFloat()
                                                    val mediay = (py - it.y) / divisor
                                                    val velocidadey = h * 0.1f
                                                    //  if ((mediay) > w * 0.01f) mediay else w * 0.1f

                                                    if (it.x > p) {
                                                        it.x -= velocidade
                                                        if (it.x < p) {
                                                            it.x = p
                                                        }

                                                    } else if (it.x < p) {
                                                        it.x += velocidade
                                                        if (it.x > p) {
                                                            it.x = p
                                                        }
                                                    }
                                                    if (it.y > py) {
                                                        it.y -= velocidadey
                                                        if (it.y < py) {
                                                            it.y = py
                                                        }

                                                    } else if (it.y < py) {
                                                        it.y += velocidadey
                                                        if (it.y > py) {
                                                            it.y = py
                                                        }
                                                    }
                                                    if (it.y == py && it.x == p) {
                                                        it.camada = -1
                                                        //  it.bloqueado = true
                                                    }
                                                    it.draw(canvas)

                                                }
                                            } catch (e: Exception) {
                                                e.stackTrace
                                            }


                                            var i = 0
                                            val velocidade = w * 0.15f
                                            try {
                                                selectedTiles.filter { it.camada <= -3 && it.camada >= -4 }
                                                    .forEach {
                                                        val py = (h * 0.6).toFloat()
                                                        val mediay = (it.y - py) / divisor
                                                        val velocidadey =
                                                            if ((mediay) > w * 0.01f) mediay else w * 0.1f



                                                        if (it.camada == -4) {
                                                            val mediax =
                                                                if ((100 + it.x) / divisor > w * 0.1f) (100 + it.x) / 2 else w * 0.1f
//                                                           if(it.w>1 && it.h>1) {
//                                                               it.w -= 40
//                                                               it.h -= 40
//                                                               if(it.w<=0 || it.h<=0) {
//                                                                   it.w = 1
//                                                                   it.h = 1
//                                                               }
//                                                           }else{
//                                                               it.w = 1
//                                                               it.h = 1
//                                                           }
                                                            it.x -= mediax
                                                            if (it.x < -100) {
                                                                it.camada = -5
                                                            }
                                                        }

                                                        if (it.camada > -4) {
//                                                            if (it.y > py + (h * 0.1)) {
//                                                                it.w += 30
//                                                                it.h += 30
//                                                            }

                                                            if (it.y > h * 0.0) {
                                                                it.y -= velocidadey

                                                            }

                                                            val px = (w * 0.5).toFloat()
                                                            val nivelado =
                                                                if (px - it.x > 0) px - it.x else (px - it.x) * -1
                                                            val velocidadee =
                                                                if ((nivelado / divisor) > w * 0.01f) (nivelado) / divisor else w * 0.1f


                                                            if (it.x < w / 2) {

                                                                it.x += velocidadee
                                                                if (it.x > w / 2) {
                                                                    it.x = (w / 2).toFloat()
                                                                    it.camada = -4
                                                                }
                                                            } else if (it.x > w / 2) {

                                                                it.x -= velocidadee
                                                                if (it.x < w / 2) {
                                                                    it.x = (w / 2).toFloat()
                                                                    it.camada = -4
                                                                }
                                                            }
                                                        }

                                                        it.draw(canvas)

                                                    }

                                                // }


                                                ////////////////
                                            } catch (e: Exception) {
                                                e.stackTrace
                                            }
                                        }

//                            launch(Dispatchers.Default) {
//                                eliminarSelecao()
//
//                            }

                                    }






                                    if (tiles.filter { !selectedTiles.contains(it) }
                                            .filter { it.camada > 0 }
                                            .isEmpty() && selectedTiles.filter { it.camada != -5 }
                                            .isEmpty()) {

                                        popularTiles()
                                    }

                                } else {
                                   // validarSelecao(selectedTiles.filter { it.camada !=-5}.toMutableList())
                                    if (selectedTiles.filter { it.camada > -2 }.size >= 7) {
                                        val paint = Paint()
                                        paint.textSize = 150f
                                        canvas.drawRGB(244, 128, 0) // Fundo verde
                                        paint.color = Color.Red.toArgb()

                                        canvas.drawText(
                                            "GAME OUVER",
                                            100f,
                                            (h * 0.5).toFloat(),
                                            paint
                                        )
                                        canvas.drawText(
                                            timet.toString(),
                                            (w * 0.5).toFloat(),
                                            (h * 0.7).toFloat(),
                                            paint
                                        )
                                        timet--
                                        Thread.sleep(700)
                                        if (timet == 0) {
                                            selectedTiles.clear()
                                            tiles.clear()
                                            popularTiles()
                                            timet = 5

                                        }
                                    }
                                }
                                surfaceHolder.unlockCanvasAndPost(canvas)


                            } catch (ew: Exception) {
                                ew.stackTrace
                            }
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

//kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk

            if(toque>0){
                toque--
            }

        }

    }

    private fun eliminarSelecao() {
        val listr: MutableList<MahjongTile> = mutableListOf()
        selectedTiles.forEach {

            listr.add(it)

        }

        val listr2 = listr.filter { it.camada == -5 }

        for (item in selectedTiles.toList()) { // Criando uma cópia para evitar a modificação direta
            if (listr2.contains(item)) {
                selectedTiles.remove(item) // Adiciona sem erro
            }
        }


        dica = false
    }

    private fun render() {


        val canvas = surfaceHolder.lockCanvas()
        if (canvas != null) {
            canvas.drawRGB(0, 0, 0) // Limpa a tela
            tiles.filter { !selectedTiles.contains(it) }.forEach { it.draw(canvas) }

        }
    }

    private fun handleInput() {
        if (isTouched) {

        }
    }

    init {
        val medida = this.h * 1.2f

        if (tiles.filter { !selectedTiles.filter { it.camada !=-5}.contains(it) }.isEmpty()) {
            popularTiles()
            walld.add(
                Bitmap.createScaledBitmap(
                    BitmapFactory.decodeResource(
                        context.resources,
                        R.drawable.neve
                    ), (w).toInt(), (medida).toInt(), false
                )
            )
            walld.add(
                Bitmap.createScaledBitmap(
                    BitmapFactory.decodeResource(
                        context.resources,
                        R.drawable.muralha
                    ), (w).toInt(), (medida).toInt(), false
                )
            )
            walld.add(
                Bitmap.createScaledBitmap(
                    BitmapFactory.decodeResource(
                        context.resources,
                        R.drawable.lago
                    ), (w).toInt(), (medida).toInt(), false
                )
            )
            walld.add(
                Bitmap.createScaledBitmap(
                    BitmapFactory.decodeResource(
                        context.resources,
                        R.drawable.verde
                    ), (w).toInt(), (medida).toInt(), false
                )
            )
            // walld.add(BitmapFactory.decodeResource(context.resources, R.drawable.neve))
            walld.shuffle()
        }


    }

    fun popularTiles() {
        val tileImage =
            BitmapFactory.decodeResource(context.resources, R.drawable.baleia)
        ajustarY = true
        selectedTiles.clear()
        tileImages.clear()
        tileImages.add(
            BitmapFactory.decodeResource(
                context.resources,
                R.drawable.abutre
            )
        )
        tileImages.add(
            BitmapFactory.decodeResource(
                context.resources,
                R.drawable.baleia
            )
        )
        tileImages.add(
            BitmapFactory.decodeResource(
                context.resources,
                R.drawable.camarao
            )
        )
        tileImages.add(
            BitmapFactory.decodeResource(
                context.resources,
                R.drawable.canguru
            )
        )
        tileImages.add(BitmapFactory.decodeResource(context.resources, R.drawable.cao))
        tileImages.add(
            BitmapFactory.decodeResource(
                context.resources,
                R.drawable.elefante
            )
        )
        tileImages.add(BitmapFactory.decodeResource(context.resources, R.drawable.galo))
        tileImages.add(BitmapFactory.decodeResource(context.resources, R.drawable.gato))
        tileImages.add(
            BitmapFactory.decodeResource(
                context.resources,
                R.drawable.hiena
            )
        )
        tileImages.add(
            BitmapFactory.decodeResource(
                context.resources,
                R.drawable.hipopotamo
            )
        )
        tileImages.add(
            BitmapFactory.decodeResource(
                context.resources,
                R.drawable.javali
            )
        )
        tileImages.add(
            BitmapFactory.decodeResource(
                context.resources,
                R.drawable.lagarto
            )
        )
        tileImages.add(BitmapFactory.decodeResource(context.resources, R.drawable.leao))
        tileImages.add(
            BitmapFactory.decodeResource(
                context.resources,
                R.drawable.macaco
            )
        )
        tileImages.add(BitmapFactory.decodeResource(context.resources, R.drawable.pato))
        tileImages.add(
            BitmapFactory.decodeResource(
                context.resources,
                R.drawable.porco
            )
        )
        tileImages.add(
            BitmapFactory.decodeResource(
                context.resources,
                R.drawable.raposa
            )
        )
        tileImages.add(BitmapFactory.decodeResource(context.resources, R.drawable.rato))
        tileImages.add(
            BitmapFactory.decodeResource(
                context.resources,
                R.drawable.tartaruga
            )
        )
        tileImages.add(
            BitmapFactory.decodeResource(
                context.resources,
                R.drawable.tigre
            )
        )
        tileImages.add(
            BitmapFactory.decodeResource(
                context.resources,
                R.drawable.tubarao
            )
        )
        tileImages.add(BitmapFactory.decodeResource(context.resources, R.drawable.vaca))
        tileImages.add(BitmapFactory.decodeResource(context.resources, R.drawable.urso))
        tileImages.add(
            BitmapFactory.decodeResource(
                context.resources,
                R.drawable.cobra
            )
        )
        tileImages.add(
            BitmapFactory.decodeResource(
                context.resources,
                R.drawable.zebra
            )
        )
        tileImages.add(
            BitmapFactory.decodeResource(
                context.resources,
                R.drawable.cavalo
            )
        )
        tileImages.shuffle()
        tamPadrao = tileImages[0].width
        tiles.clear()
        tamNovo = ((w * 0.9f) / 6).toInt()
        val espaco = w * 0.05f
        val h = tamNovo
        val w = tamNovo

        for (i in 0 until 7) {
            selectedTilesBase.add(
                Botao(
                    tileImages[0],
                    ((this.w * 0.9 / 8) * (i + 1)).toFloat(),
                    (this.h * 0.75).toFloat(),
                    (this.w * 0.9 / 8).toInt(),
                    (this.w * 0.9 / 8).toInt(),
                    0,
                    "4000"
                )
            )
        }
        // tileImages.shuffle()
        var initialize = 0
        var grau = 0
        var disponiveis: MutableList<Int> = mutableListOf()
        for (i in 0 until 79) {
            disponiveis.add(i)
        }
        var maximo = disponiveis.size
        for (i in 0 until 78) {
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
                in 0..35 -> {
                    camadaP = 0
                    when (disponiveis[index]) {
                        in 0..5 -> {
                            y = tamNovo.toFloat()
                        }

                        in 6..11 -> {
                            y = tamNovo.toFloat() * 2
                        }

                        in 12..17 -> {
                            y = tamNovo.toFloat() * 3
                        }

                        in 18..23 -> {
                            y = tamNovo.toFloat() * 4
                        }

                        in 24..29 -> {
                            y = tamNovo.toFloat() * 5
                        }

                        in 30..35 -> {
                            y = tamNovo.toFloat() * 6
                        }
                    }
                    when (disponiveis[index]) {
                        in listOf(0, 6, 12, 18, 24, 30) -> x = espaco
                        in listOf(1, 7, 13, 19, 25, 31) -> x =
                            espaco + tamNovo.toFloat() * 1

                        in listOf(2, 8, 14, 20, 26, 32) -> x =
                            espaco + tamNovo.toFloat() * 2

                        in listOf(3, 9, 15, 21, 27, 33) -> x =
                            espaco + tamNovo.toFloat() * 3

                        in listOf(4, 10, 16, 22, 28, 34) -> x =
                            espaco + tamNovo.toFloat() * 4

                        in listOf(5, 11, 17, 23, 29, 35) -> x =
                            espaco + tamNovo.toFloat() * 5
                    }
                }

                in 36..60 -> {
                    camadaP = 1

                    when (disponiveis[index]) {
                        in 36..40 -> {
                            y = tamNovo.toFloat() + (tamNovo.toFloat() / 2)
                        }

                        in 41..45 -> {
                            y = (tamNovo.toFloat() * 2) + (tamNovo.toFloat() / 2)
                        }

                        in 46..50 -> {
                            y = (tamNovo.toFloat() * 3) + (tamNovo.toFloat() / 2)
                        }

                        in 51..55 -> {
                            y = (tamNovo.toFloat() * 4) + (tamNovo.toFloat() / 2)
                        }

                        in 56..60 -> {
                            y = (tamNovo.toFloat() * 5) + (tamNovo.toFloat() / 2)
                        }
                    }
                    when (disponiveis[index]) {
                        in listOf(36, 41, 46, 51, 56) -> x =
                            espaco + (tamNovo.toFloat() / 2)

                        in listOf(37, 42, 47, 52, 57) -> x =
                            espaco + (tamNovo.toFloat() * 1) + (tamNovo.toFloat() / 2)

                        in listOf(38, 43, 48, 53, 58) -> x =
                            espaco + (tamNovo.toFloat() * 2) + (tamNovo.toFloat() / 2)

                        in listOf(39, 44, 49, 54, 59) -> x =
                            espaco + (tamNovo.toFloat() * 3) + (tamNovo.toFloat() / 2)

                        in listOf(40, 45, 50, 55, 60) -> x =
                            espaco + (tamNovo.toFloat() * 4) + (tamNovo.toFloat() / 2)
                    }
                }

                in 61..76 -> {
                    camadaP = 2
                    when (disponiveis[index]) {
                        in 61..64 -> {
                            y = tamNovo.toFloat() + tamNovo.toFloat()
                        }

                        in 65..68 -> {
                            y = (tamNovo.toFloat() * 2) + tamNovo.toFloat()
                        }

                        in 69..72 -> {
                            y = (tamNovo.toFloat() * 3) + tamNovo.toFloat()
                        }

                        in 73..76 -> {
                            y = (tamNovo.toFloat() * 4) + tamNovo.toFloat()
                        }
                    }
                    when (disponiveis[index]) {
                        in listOf(61, 65, 69, 73) -> x = espaco + tamNovo.toFloat()
                        in listOf(62, 66, 70, 74) -> x =
                            espaco + (tamNovo.toFloat() * 1) + tamNovo.toFloat()

                        in listOf(63, 67, 71, 75) -> x =
                            espaco + (tamNovo.toFloat() * 2) + tamNovo.toFloat()

                        in listOf(64, 68, 72, 76) -> x =
                            espaco + (tamNovo.toFloat() * 3) + tamNovo.toFloat()
                    }
                }

                else -> {
                    camadaP = 2
                    y = espaco + (tamNovo.toFloat() * 4) + tamNovo.toFloat()
                    x = espaco + (tamNovo.toFloat() * 4) + tamNovo.toFloat()
                }
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

        tiles.sortBy { it.camada }

    }

    private fun limparSelecionados() {
        if (selectedTiles.filter { it.camada != -5 }.isNotEmpty()) {
            var s: MutableList<MahjongTile> =
                selectedTiles.filter { it.id == selectedTiles.filter { it.camada != -5 }[0].id }
                    .toMutableList()
            var tilesx: MutableList<MahjongTile> =
                tiles.filter { !selectedTiles.filter { it.camada !=-5}.contains(it) }
                    .filter { it.camada == 0 && it.id == s[0].id }.toMutableList()
            var tilesx1: MutableList<MahjongTile> =
                tiles.filter { !selectedTiles.filter { it.camada !=-5}.contains(it) }
                    .filter { it.camada == 1 && it.id == s[0].id }.toMutableList()
            var tilesx2: MutableList<MahjongTile> =
                tiles.filter { !selectedTiles.filter { it.camada !=-5}.contains(it) }
                    .filter { it.camada == 2 && it.id == s[0].id }.toMutableList()
            var tilesx3: MutableList<MahjongTile> = mutableListOf()
            tilesx3.addAll(tilesx2)
            tilesx3.addAll(tilesx1)
            tilesx3.addAll(tilesx)

            if (s.size > 1) {
                achou(tilesx3[0])

            } else if( tilesx3.isNotEmpty()){
                for (i in 0 until 2) {
                    try {
                        achou(tilesx3[i])
                    } catch (e: Exception) {
                        e.stackTrace
                    }

                }
            }else{
                achou(s[0])

            }


        }


    }

    private fun embaralha() {
        var disponiveis: MutableList<MahjongTile> = mutableListOf()
        var tilesx: MutableList<MahjongTile> =
            tiles.filter { !selectedTiles.contains(it) }.filter { it.camada > -1 }.toMutableList()

        for (i in 0 until tilesx.size) {
            val m = MahjongTile(
                tilesx[i].image,
                tilesx[i].x,
                tilesx[i].y,
                wp, hp,
                tilesx[i].camada,
                tilesx[i].id
            )
            m.girando = true
            disponiveis.add(m)
        }
        disponiveis.shuffle()

        for (i in 0 until tilesx.size) {
            tilesx[i].id = disponiveis[i].id
            tilesx[i].image = disponiveis[i].image
            tilesx[i].girando = disponiveis[i].girando


        }
        tilesx.sortBy { it.camada }

    }


    fun validarCamadas(
        list1: MutableList<MahjongTile>,
        list2: MutableList<MahjongTile>,
        grau: Int
    ) {


        list1.forEach { it1 ->
            var liberado = true
            list2.forEach { it ->
                if (it1.containsCamada(it.x, it.y)) {
                    liberado = false

                }
            }

            if (liberado) {
                it1.camada = grau
            }

        }


    }


    fun validarSelecao(list: MutableList<MahjongTile>) {
        var selecionados = 0
        val listr: MutableList<MahjongTile> = mutableListOf()
        var grau = 0
        var tem = false
        list.forEach {
            if (!tem) {
                if (it.id == grau && it.camada == -1 && !it.bloqueado) {
                    listr.add(it)
                    selecionados++
                } else if (!it.bloqueado) {
                    grau = it.id
                    listr.clear()
                    listr.add(it)
                    selecionados = 0
                    selecionados++
                }
                if (selecionados == 3) {
                    tem = true
                }
            }

        }
        if (selecionados == 3) {
            onTocarEfeito(1)

            listr.forEach {
                it.camada = -3
                if (it.ref > -1) {
                    tiles[it.ref].bloqueado = true
                }
            }


        }

    }

    fun iaJogando() {
        val camada2 =
            tiles.filter { !selectedTiles.contains(it) }.filter { it.camada == 2 }.toMutableList()
        val tem3 = validarSelecaoIA(camada2, 3, 0)
        var tem2 = false
        var tem1 = false

        if (!tem3) {
            if (selectedTiles.filter { it.camada !=-5}.isEmpty()) {
                tem2 = validarSelecaoIA(camada2, 2, 0)
            }
            if (!tem2 && selectedTiles.filter { it.camada !=-5}.isNotEmpty()) {
                tem1 = validarSelecaoIA(selectedTiles, 2, 1)
                if (!tem1) {
                    //   validarSelecaoIA(camada2, 1, 2)
                    //   embaralha()
//TODO
                }
            }

        }

    }

    fun validarSelecaoIA(list: MutableList<MahjongTile>, min: Int, modo: Int): Boolean {
        val listr: MutableList<MahjongTile> = mutableListOf()
        val listr2: MutableList<MahjongTile> = mutableListOf()
        var listr3: MutableList<MahjongTile> = mutableListOf()

        if (modo == 0 || modo == 2) {
            list.forEach { it0 ->
                var listrd = list.filter { it.id == it0.id }.toMutableList()
                if (listrd.size >= min) {
                    listr.add(it0)
                }
            }
            listr.sortBy { it.id }
            if (listr.size > 7 - selectedTiles.size && min < 3) {
                val possou = listr.size - (7 - selectedTiles.size)

                for (j in 0..possou) {
                    if (listr.size <= 7 - selectedTiles.size || listr.size < 3) {
                        break
                    }
                    listr.removeAt(0)
                    listr.removeAt(0)


                }

            }
            if (listr.size >= min && modo == 0) {
                var i = 0

                if (i < min) {
                    listr.filter { it.id == listr[0].id }.forEach {
                        achou(it)
                        i++
                    }
                }

                return true

            }
        } else if (modo == 1) {

            list.forEach { it0 ->
                var listrd = list.filter { it.id == it0.id }.toMutableList()
                if (listrd.size >= min) {
                    listr.add(it0)
                }
            }

            var i = 0

            for (j in 0..listr.size - 1) {
                if (j % 2 == 0) {
                    listr2.add(listr[j])

                }
            }
            listr2.forEach { o ->
                listr3.addAll(tiles.filter { !selectedTiles.contains(it) }.filter {
                    it.id == o.id && it.camada == 2
                }.toMutableList())
            }



            if (i < 8 - selectedTiles.filter { it.camada !=-5}.size && listr3.isNotEmpty()) {
                listr3.forEach {
                    achou(it)
                    i++
                }
                return true
            } else {
                val listrt: MutableList<MahjongTile> = mutableListOf()
                val listrv: MutableList<MahjongTile> = mutableListOf()

                val listrr = tiles.filter { !selectedTiles.contains(it) }.filter { it.camada == 2 }
                    .toMutableList()
                listrr.forEach { it0 ->
                    var listrd = listrr.filter { it.id == it0.id }.toMutableList()
                    if (listrd.size == 2) {
                        listrt.add(it0)
                    }
                }

                listrt.forEach {
                    list.forEach { o ->

                        if (o.id == it.id) {
                            listrv.add(it)
                        }

                    }
                }
                var ii = 0
                if (ii < 8 - selectedTiles.size && listrv.isNotEmpty()) {
                    listrv.forEach {
                        achou(it)
                        ii++
                    }
                    return true
                }


            }


        }

        if (modo == 2) {

            var listrx: MutableList<MahjongTile> = mutableListOf()

            listr.forEach {
                selectedTiles.forEach { o ->
                    if (o.id == it.id) {
                        listrx.add(it)

                    }

                }
            }
            if (listrx.isNotEmpty()) {
                achou(listrx[0])
            } else {
                achou(listr[0])
            }


            return true

        }

        return false
    }

    fun achou(tile: MahjongTile) {
        val m: MahjongTile = MahjongTile(
            tile.image,
            tile.x,
            tile.y,
            120,
            120,
            -2,
            tile.id
        )
        m.y = tile.y
        val total = selectedTiles.filter { it.camada !=-5}.filter { it.id == tile.id && it.camada > -3 }.size + 1

        if (total > 3) {
            return
        }
        tile.camada = -1
        tile.w = (this.w * 0.9 / 8).toInt()
        tile.h = (this.w * 0.9 / 8).toInt()
        tile.x -= 5000f


        val novosItens = mutableListOf<MahjongTile>()

        for (item in selectedTiles.toList()) {
            novosItens.add(item)

        }
        novosItens.add(m)
        selectedTiles.clear()
        selectedTiles.addAll(novosItens)





        selectedTiles.sortBy { it.id }

    }

    fun onTocarEfeito(i: Int) {
        val coroutineScope = CoroutineScope(Dispatchers.Default)

        coroutineScope.launch {

            if (i == 0) {
                if (!efeitoSonoro.isPlaying) {
                    efeitoSonoro.setVolume(0.2f, 0.2f)
                    efeitoSonoro.seekTo(0)
                    efeitoSonoro.start()
                } else {
                    efeitoSonoro.pause()
                    efeitoSonoro.setVolume(0.2f, 0.2f)
                    efeitoSonoro.seekTo(0)
                    efeitoSonoro.start()
                }
            } else if (i == 1) {
                if (!efeitoSonoro2.isPlaying) {
                    efeitoSonoro2.setVolume(0.2f, 0.2f)
                    efeitoSonoro2.seekTo(0)
                    efeitoSonoro2.start()
                } else {
                    efeitoSonoro2.pause()
                    efeitoSonoro2.setVolume(0.2f, 0.2f)
                    efeitoSonoro2.seekTo(0)
                    efeitoSonoro2.start()
                }
            }


        }
    }


    fun onTouchEvent(event: MotionEvent) {
        try {

            if(toque>3){
                return
            }

            toque++
            eliminarSelecao()

            if (event.action == MotionEvent.ACTION_DOWN) {
                tiles.filter { !selectedTiles.contains(it) }.find {
                    it.containsTouch(
                        event.x,
                        event.y
                    ) && it.camada == 2 && selectedTiles.filter { it.camada > -3 }.size < 8
                }?.let { tile ->

                    val m: MahjongTile = MahjongTile(
                        tile.image,
                        tile.x,
                        tile.y,
                        (this.w * 0.9 / 8).toInt(),
                        (this.w * 0.9 / 8).toInt(),
                        -2,
                        tile.id
                    )
                    m.y = tile.y
                    tile.camada = -2
                    tile.w = (this.w * 0.9 / 8).toInt()
                    tile.h = (this.w * 0.9 / 8).toInt()
//                    val selectedTilesNovo = mutableListOf<MahjongTile>()
//                    selectedTilesNovo.addAll(selectedTiles)
                    tile.ref = tiles.indexOf(tile)

                    val novosItens = mutableListOf<MahjongTile>()

                    for (item in selectedTiles) {
                            novosItens.add(item)

                    }
                    novosItens.add(m)
                    selectedTiles.clear()
                    selectedTiles.addAll(novosItens)


//
//                    selectedTiles.clear()
//                    selectedTiles.addAll(selectedTilesNovo)

                     tile.camada = -1
                     tile.x -= 5000f
                    onTocarEfeito(0)
                    selectedTiles.sortBy { it.id }


                }

                if (bloquerBT && bloquerBT2) {
                    if (botao1.containsTouch(
                            event.x,
                            event.y
                        ) && time1 == 0 && time3 == 0 && time2 == 0
                    ) {
                        time1 = 10
                        botao1.isSelected = true
                        dica = true
                        iaJogando()
                    } else if (botao2.containsTouch(
                            event.x,
                            event.y
                        )
                        && time1 == 0 && time3 == 0 && time2 == 0
                    ) {
                        time2 = 10
                        botao2.isSelected = true

                        limparSelecionados()
                    } else if (botao3.containsTouch(
                            event.x,
                            event.y
                        )
                        && time1 == 0 && time3 == 0 && time2 == 0
                    ) {
                        time3 = 10
                        botao3.isSelected = true

                        embaralha()
                    }

                    bloquerBT2 = false


                }

            }

        } catch (e: Exception) {
            e.stackTrace
        }
    }

}