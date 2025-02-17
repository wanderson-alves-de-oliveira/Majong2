package com.example.majong.egine

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Paint
import android.util.DisplayMetrics
import android.util.Size
import android.view.MotionEvent
import android.view.SurfaceHolder
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.example.majong.R
import com.example.majong.view.Botao
import com.example.majong.view.GameObject
import com.example.majong.view.MahjongTile
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


        while (running) {
            val canvas = surfaceHolder.lockCanvas()
            if (canvas != null) {

                if (selectedTiles.filter { it.camada>-2 }.size < 9) {

                    canvas.drawRGB(0, 128, 0) // Fundo verde
                    try {

                        tiles.forEach {

                            if (it.camada > -1) {
                                it.draw(canvas)
                            }

                        }

                        selectedTiles.forEach { it.draw(canvas) }

                        botao1.draw(canvas)
                        botao2.draw(canvas)
                        botao3.draw(canvas)
                    } catch (e: Exception) {
                        e.printStackTrace()


                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                    if (tiles.filter { it.camada > 0 }.isEmpty()) {

                        popularTiles()
                    }

                    val novoFiltro2 = tiles.filter { it.camada == 2 }
                    val novoFiltro1 = tiles.filter { it.camada == 1 }
                    val novoFiltro0 = tiles.filter { it.camada == 0 }

                    validarCamadas(novoFiltro1.toMutableList(), novoFiltro2.toMutableList(), 2)
                    validarCamadas(novoFiltro0.toMutableList(), novoFiltro1.toMutableList(), 1)
                    var i = 0
                    val velocidade = w * 0.15f
                    val velocidadey = h * 0.15f

                    selectedTiles.forEach {
                        if (it.camada > -2) {
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



                        } else {
                            var p = (120 * 8).toFloat()
                            var py = (h * 0.9).toFloat()

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


                        }
                    }
                        validarSelecao(selectedTiles)



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
                if(grau>((maximo/3)/2).toInt()){
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
                            y = 120f
                        }

                        in 6..11 -> {
                            y = 120f * 2
                        }

                        in 12..17 -> {
                            y = 120f * 3
                        }

                        in 18..23 -> {
                            y = 120f * 4
                        }

                        in 24..29 -> {
                            y = 120f * 5
                        }

                        in 30..35 -> {
                            y = 120f * 6
                        }
                    }
                    when (disponiveis[index]) {
                        in listOf(0, 6, 12, 18, 24, 30) -> x = 120f
                        in listOf(1, 7, 13, 19, 25, 31) -> x = 120f * 2
                        in listOf(2, 8, 14, 20, 26, 32) -> x = 120f * 3
                        in listOf(3, 9, 15, 21, 27, 33) -> x = 120f * 4
                        in listOf(4, 10, 16, 22, 28, 34) -> x = 120f * 5
                        in listOf(5, 11, 17, 23, 29, 35) -> x = 120f * 6
                    }
                }

                in 36..60 -> {
                    camadaP = 1

                    when (disponiveis[index]) {
                        in 36..40 -> {
                            y = 120f + 60
                        }

                        in 41..45 -> {
                            y = (120f * 2) + 60
                        }

                        in 46..50 -> {
                            y = (120f * 3) + 60
                        }

                        in 51..55 -> {
                            y = (120f * 4) + 60
                        }

                        in 56..60 -> {
                            y = (120f * 5) + 60
                        }
                    }
                    when (disponiveis[index]) {
                        in listOf(36, 41, 46, 51, 56) -> x = 120f + 60
                        in listOf(37, 42, 47, 52, 57) -> x = (120f * 2) + 60
                        in listOf(38, 43, 48, 53, 58) -> x = (120f * 3) + 60
                        in listOf(39, 44, 49, 54, 59) -> x = (120f * 4) + 60
                        in listOf(40, 45, 50, 55, 60) -> x = (120f * 5) + 60
                    }
                }

                in 61..76 -> {
                    camadaP = 2
                    when (disponiveis[index]) {
                        in 61..64 -> {
                            y = 120f + 120f
                        }

                        in 65..68 -> {
                            y = (120f * 2) + 120f
                        }

                        in 69..72 -> {
                            y = (120f * 3) + 120f
                        }

                        in 73..76 -> {
                            y = (120f * 4) + 120f
                        }
                    }
                    when (disponiveis[index]) {
                        in listOf(61, 65, 69, 73) -> x = 120f + 120f
                        in listOf(62, 66, 70, 74) -> x = (120f * 2) + 120f
                        in listOf(63, 67, 71, 75) -> x = (120f * 3) + 120f
                        in listOf(64, 68, 72, 76) -> x = (120f * 4) + 120f
                    }
                }

                else -> {
                    camadaP = 2
                    y = (120f * 5) + 120f
                    x = (120f * 5) + 120f
                }
            }


            disponiveis.remove(disponiveis[index])

            tiles.add(MahjongTile(tileImages[grau - 1], x, y, camadaP, grau)) // Criando pares
        }

        tiles.sortBy { it.camada }

    }

    fun onTouchEvent(event: MotionEvent) {
        if (event.action == MotionEvent.ACTION_DOWN) {
            tiles.find {
                it.containsTouch(
                    event.x,
                    event.y
                ) && it.camada == 2 && selectedTiles.size < 10
            }?.let { tile ->

                val m: MahjongTile = MahjongTile(
                    tile.image,
                    tile.x,
                    tile.y,
                    -2,
                    tile.id
                )

                selectedTiles.add(m)


                tile.camada = -1


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

                    it.camada = -1

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
            if(!tem) {
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
                if(selecionados==3)tem = true
            }

        }
        if (selecionados == 3) {
            list.removeAll(listr)
        }

    }

}