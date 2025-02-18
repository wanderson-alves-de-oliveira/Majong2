package com.example.majong.egine

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Paint
import android.util.DisplayMetrics
import android.view.MotionEvent
import android.view.SurfaceHolder
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.example.majong.R
import com.example.majong.view.Botao
import com.example.majong.view.MahjongTile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.random.Random

class GameLoop(private val surfaceHolder: SurfaceHolder, private val context: Context) : Thread() {
    private var running = false

    private var lastTime = System.nanoTime()
    private val targetFps = 60
    private var timet = 9
    private val optimalTime = 1_000_000_000 / targetFps
    private var touchX: Float = 0f
    private var touchY: Float = 0f
    private var isTouched = false
    var tiles = mutableListOf<MahjongTile>()
    var selectedTiles = mutableListOf<MahjongTile>()
    private val display: DisplayMetrics = context.resources.displayMetrics
    private val h = display.heightPixels
    private val w = display.widthPixels
    private val hp = 0
    private val wp = 0
    var tamPadrao = 0
    var tamNovo = 0
    var ajustarY = true

    var tileImage = BitmapFactory.decodeResource(context.resources, R.drawable.mahjongtile)
    var tileImages = mutableListOf<Bitmap>()
    val botao1 = Botao(tileImage, 100f, h * 0.75f, 0, "")
    val botao2 = Botao(tileImage, 220f, h * 0.75f, 1, "")
    val botao3 = Botao(tileImage, 340f, h * 0.75f, 2, "")

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

            update(deltaTime)




            render()
            handleInput()

            val sleepTime = optimalTime - (System.nanoTime() - now)
            if (sleepTime > 0) {
                Thread.sleep(sleepTime / 1_000_000)
            }
        }
    }

    private fun update(deltaTime: Double) {

        Thread.sleep(deltaTime.toLong())


        while (running) {
            val canvas = surfaceHolder.lockCanvas()

            if (canvas != null) {

                if (selectedTiles.filter { it.camada > -2 }.size < 10) {

                    canvas.drawRGB(0, 128, 0) // Fundo verde

                    runBlocking {
                        launch(Dispatchers.Default) {
                            tiles.forEach {

                                if (ajustarY) {
                                    val vel = (it.yp - it.y) / 2
                                    if (it.y < it.yp) {

                                        it.y += if (vel > w * 0.1f) vel else w * 0.1f

                                        if (it.y > it.yp) {
                                            it.y = it.yp
                                            it.ty = true
                                        }


                                    }
                                }

                                it.draw(canvas)
                                // }

                            }

                            if(tiles.filter { it.ty==false }.isEmpty()){
                                ajustarY = false

                            }

                        }
                    }

                    botao1.draw(canvas)
                    botao2.draw(canvas)
                    botao3.draw(canvas)


                    val novoFiltro2 = tiles.filter { it.camada == 2 }
                    val novoFiltro1 = tiles.filter { it.camada == 1 }
                    val novoFiltro0 = tiles.filter { it.camada == 0 }
                    runBlocking {
                        launch(Dispatchers.Default) {


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
                        }




                        val selectedTilesNovo = mutableListOf<MahjongTile>()
                        for (item in selectedTiles) {

                            selectedTilesNovo.add(item)

                        }
                        validarSelecao(selectedTilesNovo)
                        selectedTiles.clear()
                        selectedTiles.addAll(selectedTilesNovo)
                    }


                    try {

                        runBlocking {
                            launch(Dispatchers.Default) {
                                var i = 0
                                val velocidade = w * 0.15f
                                selectedTiles.forEach {
                                    //  if (it.camada > -2) {


                                    if (it.camada > -3) {
                                        var p = (120 * i).toFloat()
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
                                        p = (120 * i).toFloat()
                                        val py = (h * 0.9).toFloat()
                                        val mediay = (py - it.y) / 2
                                        val velocidadey =
                                            if ((mediay) > w * 0.01f) mediay else w * 0.1f

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

                                        }
                                    } else {
                                        val py = (h * 0.6).toFloat()
                                        val mediay = (it.y - py) / 2
                                        val velocidadey =
                                            if ((mediay) > w * 0.01f) mediay else w * 0.1f


                                        if (it.y > h * 0.0) {
                                            it.y -= velocidadey

                                        }

                                        val px = (w * 0.5).toFloat()
                                        val nivelado =
                                            if (px - it.x > 0) px - it.x else (px - it.x) * -1
                                        val velocidadee =
                                            if ((nivelado / 2) > w * 0.01f) (nivelado) / 2 else w * 0.1f
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

                                    // }


                                    it.draw(canvas)

                                }
                            }
                        }

                        eliminarSelecao()

                    } catch (e: Exception) {
                    }
                    if (tiles.filter { it.camada > 0 }.isEmpty() && selectedTiles.isEmpty()) {

                        popularTiles()
                    }

                } else {
                    val paint = Paint()
                    paint.textSize = 150f
                    canvas.drawRGB(244, 128, 0) // Fundo verde
                    paint.color = Color.Red.toArgb()

                    canvas.drawText("GAME OUVER", 100f, (h * 0.5).toFloat(), paint)
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
                        timet = 9

                    }

                }
                surfaceHolder.unlockCanvasAndPost(canvas)


            }


        }


    }

    private fun eliminarSelecao() {
        val listr: MutableList<MahjongTile> = mutableListOf()
        selectedTiles.forEach {

            listr.add(it)

        }
        val listr2 = listr.filter { it.camada == -4 }
        listr.removeAll(listr2)
        selectedTiles.clear()
        selectedTiles.addAll(listr)

    }

    private fun render() {


        val canvas = surfaceHolder.lockCanvas()
        if (canvas != null) {
            canvas.drawRGB(0, 0, 0) // Limpa a tela
            tiles.forEach { it.draw(canvas) }

        }
    }

    private fun handleInput() {
        if (isTouched) {

        }
    }

    init {


        if (tiles.isEmpty()) {
            popularTiles()
        }


    }

    fun popularTiles() {
        val tileImage = BitmapFactory.decodeResource(context.resources, R.drawable.baleia)
        ajustarY = true
        tileImages.clear()
        tileImages.add(BitmapFactory.decodeResource(context.resources, R.drawable.abutre))
        tileImages.add(BitmapFactory.decodeResource(context.resources, R.drawable.baleia))
        tileImages.add(BitmapFactory.decodeResource(context.resources, R.drawable.camarao))
        tileImages.add(BitmapFactory.decodeResource(context.resources, R.drawable.canguru))
        tileImages.add(BitmapFactory.decodeResource(context.resources, R.drawable.cao))
        tileImages.add(BitmapFactory.decodeResource(context.resources, R.drawable.elefante))
        tileImages.add(BitmapFactory.decodeResource(context.resources, R.drawable.galo))
        tileImages.add(BitmapFactory.decodeResource(context.resources, R.drawable.gato))
        tileImages.add(BitmapFactory.decodeResource(context.resources, R.drawable.hiena))
        tileImages.add(BitmapFactory.decodeResource(context.resources, R.drawable.hipopotamo))
        tileImages.add(BitmapFactory.decodeResource(context.resources, R.drawable.javali))
        tileImages.add(BitmapFactory.decodeResource(context.resources, R.drawable.lagarto))
        tileImages.add(BitmapFactory.decodeResource(context.resources, R.drawable.leao))
        tileImages.add(BitmapFactory.decodeResource(context.resources, R.drawable.macaco))
        tileImages.add(BitmapFactory.decodeResource(context.resources, R.drawable.pato))
        tileImages.add(BitmapFactory.decodeResource(context.resources, R.drawable.porco))
        tileImages.add(BitmapFactory.decodeResource(context.resources, R.drawable.raposa))
        tileImages.add(BitmapFactory.decodeResource(context.resources, R.drawable.rato))
        tileImages.add(BitmapFactory.decodeResource(context.resources, R.drawable.tartaruga))
        tileImages.add(BitmapFactory.decodeResource(context.resources, R.drawable.tigre))
        tileImages.add(BitmapFactory.decodeResource(context.resources, R.drawable.tubarao))
        tileImages.add(BitmapFactory.decodeResource(context.resources, R.drawable.vaca))
        tileImages.add(BitmapFactory.decodeResource(context.resources, R.drawable.urso))
        tileImages.add(BitmapFactory.decodeResource(context.resources, R.drawable.cobra))
        tileImages.add(BitmapFactory.decodeResource(context.resources, R.drawable.zebra))
        tileImages.add(BitmapFactory.decodeResource(context.resources, R.drawable.cavalo))
        tamPadrao = tileImages[0].width
        tiles.clear()
        tamNovo = ((w * 0.9f) / 6).toInt()
        val espaco = w * 0.05f
        val h = tamNovo
        val w = tamNovo
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
                        in listOf(1, 7, 13, 19, 25, 31) -> x = espaco + tamNovo.toFloat() * 1
                        in listOf(2, 8, 14, 20, 26, 32) -> x = espaco + tamNovo.toFloat() * 2
                        in listOf(3, 9, 15, 21, 27, 33) -> x = espaco + tamNovo.toFloat() * 3
                        in listOf(4, 10, 16, 22, 28, 34) -> x = espaco + tamNovo.toFloat() * 4
                        in listOf(5, 11, 17, 23, 29, 35) -> x = espaco + tamNovo.toFloat() * 5
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
                        in listOf(36, 41, 46, 51, 56) -> x = espaco + (tamNovo.toFloat() / 2)
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

            tiles.add(MahjongTile(tileImages[grau - 1], x, y, w, h, camadaP, grau)) // Criando pares
        }

        tiles.sortBy { it.camada }

    }

    fun onTouchEvent(event: MotionEvent) {
        if (event.action == MotionEvent.ACTION_DOWN) {
            tiles.find {
                it.containsTouch(
                    event.x,
                    event.y
                ) && it.camada == 2 && selectedTiles.filter { it.camada > -3 }.size < 10
            }?.let { tile ->

                val m: MahjongTile = MahjongTile(
                    tile.image,
                    tile.x,
                    tile.y,
                    120,
                    120,
                    -2,
                    tile.id
                )
                val selectedTilesNovo = mutableListOf<MahjongTile>()
                selectedTilesNovo.addAll(selectedTiles)
                selectedTilesNovo.add(m)
                selectedTiles.clear()
                selectedTiles.addAll(selectedTilesNovo)

                tile.camada = -1

                tile.x -= 5000f

                selectedTiles.sortBy { it.id }

            }
            if (botao1.containsTouch(
                    event.x,
                    event.y
                )
            ) {

            } else if (botao2.containsTouch(
                    event.x,
                    event.y
                )
            ) {
                limparSelecionados()
            } else if (botao3.containsTouch(
                    event.x,
                    event.y
                )
            ) {
                embaralha()
            }

        }
    }

    private fun limparSelecionados() {
        if (selectedTiles.isNotEmpty()) {
            var s: MutableList<MahjongTile> =
                selectedTiles.filter { it.id == selectedTiles[0].id }.toMutableList()

            tiles.forEach {
                if (it.id == s[0].id) {

                    it.x = it.x + 120f

                }
                selectedTiles.removeAll(s)
            }

        }


    }

    private fun embaralha() {
        var disponiveis: MutableList<MahjongTile> = mutableListOf()
        var tilesx: MutableList<MahjongTile> = tiles.filter { it.camada > -1 }.toMutableList()

        for (i in 0 until tilesx.size) {
            val m = MahjongTile(
                tilesx[i].image,
                tilesx[i].x,
                tilesx[i].y,
                wp, hp,
                tilesx[i].camada,
                tilesx[i].id
            )
            disponiveis.add(m)
        }
        disponiveis.shuffle()

        for (i in 0 until tilesx.size) {
            tilesx[i].id = disponiveis[i].id
            tilesx[i].image = disponiveis[i].image
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
                if (it.id == grau && it.camada == -1) {
                    listr.add(it)
                    selecionados++
                } else {
                    grau = it.id
                    listr.clear()
                    listr.add(it)
                    selecionados = 0
                    selecionados++
                }
                if (selecionados == 3) tem = true
            }

        }
        if (selecionados == 3) {
            //   list.removeAll(listr)

//            val iterator = list.iterator()
//
//            while (iterator.hasNext()) {
//                val item = iterator.next()
//                if (listr.contains(item)) {
//                    iterator.remove() // Correto!
//                }
//            }
            listr.forEach { it.camada = -3 }

        }

    }

}