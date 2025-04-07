package com.wao.tile.egine

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.RectF
import android.media.MediaPlayer
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.MotionEvent
import android.view.SurfaceHolder
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.wao.majong.R
import com.wao.tile.db.BDTile
import com.wao.tile.db.Base
import com.wao.tile.view.BotaoM
import com.wao.tile.view.GameView
import com.wao.tile.view.MahjongTile
import com.wao.tile.view.MainView
import com.wao.tile.view.Venceu
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class GameLoop(
    private val surfaceHolder: SurfaceHolder,
    private val context: Context,
    private val gameView: GameView
) : Thread() {
    private var running = false

    private var lastTime = System.nanoTime()
    private val targetFps = 60
    private var toque = 0
    private var preload = 0

    private val optimalTime = 1_000_000_000 / targetFps
    private var isTouched = false
    private var dica = false


    private var avaliar3 = false

    private var tipodePremio: Int = 0
    private var efeitoSonoro: MediaPlayer = MediaPlayer.create(this.context, R.raw.dim)
    private var efeitoSonoro2: MediaPlayer = MediaPlayer.create(this.context, R.raw.finalyy)
    var tiles = mutableListOf<MahjongTile>()
    var selectedTiles = mutableListOf<MahjongTile>()
    var removerDaLista = mutableListOf<MahjongTile>()
    private var selectedTilesRes = mutableListOf<MahjongTile>()
    private var limpando = false
    private val paint = Paint()
    private var itenImpossivel = false
    private val display: DisplayMetrics = context.resources.displayMetrics
    private val h = display.heightPixels
    private val w = display.widthPixels
    private val hp = 0
    private val wp = 0
    var semanuncio = false
    private var tamPadrao = 0
    var tamNovo = 0
    private var ajustarY = true
    private var bloquerBT = false
    private var bloquerBT2 = false
    private var embaralhando = false
    var pontos = 0
    private var pontosCont = 0

    private var venceu = false
    private var falhou = false

    private var corrigirfalha = false

    private var limparComCredito = false
    var tutor = false

    private var fase = 0

    private var time1 = 0
    private var time2 = 0
    private var time3 = 0
    var index = 0
    private var cLocked = false

    private val divisor = 3
    private var carrinho = BitmapFactory.decodeResource(context.resources, R.drawable.carrinho)

    private var coin = BitmapFactory.decodeResource(context.resources, R.drawable.moeda)
    private val coinP = Bitmap.createScaledBitmap(
        coin,
        ((w * 0.1f)).toInt(),
        ((w * 0.1f)).toInt(),
        false
    )


    var compraBT = MahjongTile(
        carrinho,
        this.context,
        (w * 0.45f ),
        h * 0.75f,
        ((w * 1.0f) / 6).toInt(),
        ((w * 0.9f) / 6).toInt(),
        2,
        2000

    )


    private var lampada = BitmapFactory.decodeResource(context.resources, R.drawable.lampada)
    private var ima = BitmapFactory.decodeResource(context.resources, R.drawable.ima)
    private var giro = BitmapFactory.decodeResource(context.resources, R.drawable.giro)
    private val b: Bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)

    private var main = MainView(this.context, (w * 1.1f).toInt(), (h * 1.1f).toInt())
    private var venceuP = Venceu(this.context, (w), (h), 0)
    var perdeuL = Venceu(this.context, (w), (h), 1)
    var lojaWAO = Loja(this.context, gameView,(w), (h))

    var semInternet = false

    private var credLuz = Venceu(this.context, (w), (h), 2)
    private var credIma = Venceu(this.context, (w), (h), 3)
    private var credSufle = Venceu(this.context, (w), (h), 4)
    private var premiar = false


    private var objX = credLuz
    private var timePress = 0
    private var creditoRecorsus = false

    //var b2: Bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
    private val b3: Bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)

    private var pontuacaoNova = 0
    private var score = 0
    private var limitar = 7
    private var valorminimo = 300

    private var luzP = 0
    private var imaP = 0
    private var sufleP = 0

    private var ultimaFase = 0

    private var btm = BotaoM(
        this.context,
        ((this.w * 0.9 / 4) * (1)).toFloat(),
        (this.h * 0.6).toFloat(),
        (this.w * 0.6).toInt(),
        (this.h * 0.1).toInt(),
        0,
        ultimaFase.toString()
    )

    var walld: MutableList<Bitmap> = mutableListOf()
    var tileImages = mutableListOf<Bitmap>()
    private val botao1 = MahjongTile(
        lampada,
        this.context,
        (((w * 0.02f) + (w * 0.9f) / 6) * 1.5f),
        h * 0.83f,
        ((w * 0.8f) / 6).toInt(),
        ((w * 0.9f) / 6).toInt(),
        2,
        5000
    )
    private val botao2 = MahjongTile(
        ima,
        this.context,
        (((w * 0.02f) + (w * 0.9f) / 6) * 2.5f),
        h * 0.83f,
        ((w * 0.8f) / 6).toInt(),
        ((w * 0.9f) / 6).toInt(),
        2,
        2000

    )
    private val botao3 = MahjongTile(
        giro,
        this.context,
        (((w * 0.02f) + (w * 0.9f) / 6) * 3.5f),
        h * 0.83f,
        ((w * 0.8f) / 6).toInt(),
        ((w * 0.9f) / 6).toInt(),
        2,
        3000

    )
    private val ialimite: Int = 50
    private var timeValidarIA = ialimite

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
            (now - lastTime) / 1_000_000_000.0

            // Convertendo para segundos
            lastTime = now
            try {
                update()

            } catch (e: Exception) {
                e.stackTrace
            }




            render()
            handleInput()

            val sleepTime = optimalTime - (System.nanoTime() - now)
            if (sleepTime > 0) {
                sleep(sleepTime / 1_000_000)
            }
        }
    }

    private var canvas: Canvas? = null
    private fun update() {


        while (running) {

            //  this.canvas = null
            if (!cLocked) {
                canvas = this.surfaceHolder.lockCanvas()
                cLocked = true
            }

            try {

                if (timePress > 0) {
                    timePress--

                }
                if (index == 0) {

                    if (ultimaFase == 0) {
                        val bd = BDTile(context)
                        val base = bd.buscar()
                        ultimaFase = base.nivel
                        fase = ultimaFase


                    }
                    btm.stt = "Nível $ultimaFase"

                    if (canvas != null) {

                        try {
                            if(lojaWAO.atualizar) {
                                val bd = BDTile(context)
                                val base = bd.buscar()
                                luzP = base.luz
                                imaP = base.ima
                                sufleP = base.sufle
                                score = bd.buscar().pontos.toInt()
                                lojaWAO.atualizar=false

                            }


                            if(!lojaWAO.abrirLoja) {
                                main.draw(this.canvas!!)

                                if (preload >= 100) {
                                    compraBT.draw(canvas!!)
                                    btm.draw(this.canvas!!)
                                } else {
                                    paint.color = Color.Green.toArgb()
                                    canvas!!.drawRoundRect(
                                        RectF(
                                            (w * 0.25f),
                                            h * 0.45f,
                                            ((w * 0.45f) + (100 * (w * 0.003f))),
                                            ((h * 0.5)).toFloat()
                                        ), 60f, 60f, paint
                                    )
                                    paint.color = Color.Magenta.toArgb()
                                    canvas!!.drawRoundRect(
                                        RectF(
                                            (w * 0.25f),
                                            h * 0.45f,
                                            ((w * 0.45f) + (preload * (w * 0.003f))),
                                            ((h * 0.5)).toFloat()
                                        ), 60f, 60f, paint
                                    )

                                    paint.textSize = spToPx((this.w * 0.014f))
                                    paint.color = Color.White.toArgb()
                                    canvas!!.drawText(
                                        "LOADING...",
                                        (w * 0.36f),
                                        h * 0.48f,
                                        paint
                                    )

                                    sleep(250)
                                    preload += 20
                                }

                                if (btm.liberar > 3) {
                                    index = 1
                                    btm.liberar = 0
                                    compraBT.y=h*0.06f
                                    compraBT.x=w*0.78f

                                }

                            }else{
                                lojaWAO.semanuncio=semanuncio
                                lojaWAO.draw(canvas!!)

                            }
                ///////////////////////////////////////////


                        } catch (ew: Exception) {
                            ew.stackTrace
                        }



                    }




                    if (cLocked) {
                        surfaceHolder.unlockCanvasAndPost(canvas)
                        cLocked = false
                    }


                } else {


                    if (canvas != null) {
                        //     try {
                        //  sleep(10)
                        validarSelecao(selectedTiles.filter { it.camada != -5 }
                            .toMutableList())


                        bloquerBT =
                            selectedTiles.none { it.camada < -2 && it.camada >= -4 }

                        if(lojaWAO.atualizar) {
                            val bd = BDTile(context)
                            val base = bd.buscar()
                            luzP = base.luz
                            imaP = base.ima
                            sufleP = base.sufle
                            score = bd.buscar().pontos.toInt()
                            lojaWAO.atualizar=false
                        }

                        if(!lojaWAO.abrirLoja) {
                            if (!corrigirfalha && !creditoRecorsus || limparComCredito && !creditoRecorsus) {


                                val limitador =
                                    selectedTiles.filter { it.camada > -2 }.toMutableList()

                                for (i in 0 until selectedTiles.filter { it.camada > -2 }.size) {
                                    val limitadorX =
                                        selectedTiles.filter { it.camada > -2 && it.id == selectedTiles[i].id }

                                    if (limitadorX.size >= 3) {
                                        limitador.removeAll(limitadorX)
                                    }

                                }

                                if (!limparComCredito && !(limitador.size < limitar ||
                                            limitador.size > limitar && dica)
                                ) {
                                    corrigirfalha = true
                                } else {
                                    corrigirfalha = false
                                    if ((selectedTiles.filter { it.camada > -2 }.size < 7)) {
                                        limparComCredito = false
                                    }
                                }

//
                                try {
//

                                    if (limparComCredito) {
                                        dica = true
                                        limitar = 20
                                        if (selectedTilesRes.isEmpty()) {
                                            selectedTilesRes =
                                                selectedTiles.filter { it.id == selectedTiles[0].id }
                                                    .toMutableList()
                                            for (i in 0 until 3 - selectedTilesRes.size) {
                                                // achou(tiles.filter { it.id == selectedTilesRes[0].id && it.camada == 1}[i])
                                                limparSelecionados()

                                            }

                                        }

//                                    if(selectedTiles.filter { it.camada == -1 }.size < 7 + (selectedTilesRes.size)) {
//                                    }
                                        selectedTilesRes.clear()


                                    }


//
//
//
//                                }


                                    if (!ajustarY) {
                                        timeValidarIA--
                                        if (timeValidarIA < 0) {
                                            timeValidarIA = ialimite
                                        }
                                    }
                                    // canvas.drawRGB(0, 128, 0) // Fundo verde
                                    canvas!!.drawBitmap(walld[0], 0f, 0f, paint)

                                    if (!venceu) {
                                        canvas!!.drawBitmap(b3, 0f, 0f, paint)
                                    }
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
                                        } else {
                                            bloquerBT2 = true
                                            if (luzP <= 0) {
                                                botao1.camada = 1
                                                botao1.isSelected = true
                                            }
                                        }
                                        if (time2 > 0) {
                                            botao2.camada = 1
                                            botao2.isSelected = true

                                            time2--
                                        } else {
                                            bloquerBT2 = true

                                            if (selectedTiles.isEmpty() || imaP <= 0) {
                                                botao2.camada = 1
                                                botao2.isSelected = true
                                            }


                                        }
                                        if (time3 > 0) {
                                            botao3.camada = 1
                                            botao3.isSelected = true

                                            time3--
                                        } else {
                                            bloquerBT2 = true
                                            if (sufleP <= 0) {
                                                botao3.camada = 1
                                                botao3.isSelected = true
                                            }
                                        }

                                        if (!venceu) {

                                            botao1.draw(canvas!!)
                                            paint.color = Color.Magenta.toArgb()
                                            paint.alpha = 255

                                            canvas!!.drawRoundRect(
                                                RectF(
                                                    (w * 0.35f),
                                                    h * 0.83f,
                                                    (w * 0.4f),
                                                    ((h * 0.855)).toFloat()
                                                ), 60f, 60f, paint
                                            )

                                            paint.textSize = spToPx((this.w * 0.012f))
                                            paint.color = Color.White.toArgb()
                                            canvas!!.drawText(
                                                luzP.toString(),
                                                (w * 0.36f),
                                                h * 0.85f,
                                                paint
                                            )




                                            botao2.draw(canvas!!)

                                            paint.color = Color.Magenta.toArgb()
                                            paint.alpha = 255

                                            canvas!!.drawRoundRect(
                                                RectF(
                                                    ((w * 0.52f)),
                                                    h * 0.83f,
                                                    (w * 0.57f),
                                                    ((h * 0.855)).toFloat()
                                                ), 60f, 60f, paint
                                            )

                                            paint.textSize = spToPx((this.w * 0.012f))
                                            paint.color = Color.White.toArgb()
                                            canvas!!.drawText(
                                                imaP.toString(),
                                                ((w * 0.535f)),
                                                h * 0.85f,
                                                paint
                                            )



                                            botao3.draw(canvas!!)


                                            paint.color = Color.Magenta.toArgb()
                                            paint.alpha = 255

                                            canvas!!.drawRoundRect(
                                                RectF(
                                                    ((w * 0.69f)),
                                                    h * 0.83f,
                                                    (w * 0.74f),
                                                    ((h * 0.855)).toFloat()
                                                ), 60f, 60f, paint
                                            )

                                            paint.textSize = spToPx((this.w * 0.012f))
                                            paint.color = Color.White.toArgb()
                                            canvas!!.drawText(
                                                sufleP.toString(),
                                                ((w * 0.7f)),
                                                h * 0.85f,
                                                paint
                                            )

                                            compraBT.draw(canvas!!)

//                                            if (compraBT.liberar > 3) {
//                                                compraBT.liberar = 0
//
//                                                lojaWAO.abrirLoja = true
//
//                                            }


                                        }

                                    } catch (e: Exception) {
                                        e.stackTrace
                                    }


//                            runBlocking {
//                                launch(Dispatchers.Default) {

//                                }
//                            }

//////////////////////////////////////////////

                                    runBlocking {


                                        launch(Dispatchers.Default) {


                                            if (isTouched || ajustarY || embaralhando || limpando) {
                                                val canvas2 = Canvas(b)
                                                limpando = false
                                                canvas2.drawColor(
                                                    Color.Transparent.toArgb(),
                                                    PorterDuff.Mode.CLEAR
                                                )

                                                var countEmbaralhar = 0
                                                //  tiles.forEach {
                                                for (itty in 0 until tiles.size) {

                                                    val it = tiles[itty]
                                                    if (embaralhando) {
                                                        if (it.girando) {
                                                            countEmbaralhar++
                                                        }
                                                    }

                                                    if (!(it.w <= 0 || it.h <= 0) && it.camada >= 0) {
                                                        it.draw(canvas2)
                                                        // canvas2=canvas
                                                    }
                                                    // }

                                                }
                                                if (embaralhando) {
                                                    if (countEmbaralhar <= 0) {
                                                        embaralhando = false
                                                    }
                                                }
                                                if (tiles.none { it.ty }
                                                ) {
                                                    ajustarY = false

                                                }

                                                isTouched = false
                                                launch(Dispatchers.Default) {
                                                    canvas!!.drawBitmap(b, 0f, 0f, paint)
                                                    //      canvas.drawBitmap(b2, 0f, 0f, paint)
                                                }

                                            } else {
                                                canvas!!.drawBitmap(b, 0f, 0f, paint)
                                                //  canvas.drawBitmap(b2, 0f, 0f, paint)
                                                launch(Dispatchers.Default) {

                                                    try {
                                                        carregarCamadas()

                                                    } catch (eeee: Exception) {
                                                        eeee.stackTrace
                                                        //  b2 = bk
                                                    }

                                                }
                                            }


                                        }


                                    }


//
//                                runBlocking {
//
//
//                                    launch(Dispatchers.Default) {


                                    try {


                                        var i = 0

                                        if (tutor) {


                                            paint.color = Color.Black.toArgb()
                                            paint.alpha = 230
                                            //  val canvasG = Canvas(binit)
                                            canvas!!.drawRoundRect(
                                                RectF(
                                                    0f,
                                                    0f,
                                                    w.toFloat(),
                                                    h * 1.3f
                                                ), 0f, 0f, paint
                                            )
                                            //   canvas.drawBitmap(binit, 0f, 0f, paint)

                                            paint.color = Color.Green.toArgb()
                                            paint.alpha = 255
                                            canvas!!.drawRoundRect(
                                                RectF(
                                                    (w * 0.1).toFloat(),
                                                    (h * 0.1).toFloat(),
                                                    (w * 0.9).toFloat(),
                                                    ((h * 0.3)).toFloat()
                                                ), 60f, 60f, paint
                                            )

                                            paint.textSize = spToPx((this.w * 0.017f))
                                            paint.color = Color.Magenta.toArgb()
                                            canvas!!.drawText(
                                                "Selecione três peças iguais ",
                                                w * 0.15f,
                                                h * 0.15f,
                                                paint
                                            )


                                            canvas!!.drawText(
                                                "para limpar",
                                                w * 0.2f,
                                                h * 0.2f,
                                                paint
                                            )



                                            for (ii in tiles.size - 3 until tiles.size) {
                                                tiles[ii].draw(canvas!!)
                                            }





                                            paint.alpha = 255
                                        }


                                        // selectedTiles.forEach {

                                        for (itt in 0 until selectedTiles.size) {

                                            val it = selectedTiles[itt]
                                            if (it.camada > -3) {
                                                i++
                                                val velocidade = w * 0.15f

                                                val p = ((w * 0.9 / 8) * i).toFloat()


                                                val py = (h * 0.75).toFloat()
                                                //val mediay = (h) * 0.05

                                                //  val tempoQueda = calculoVelocidade(it,py)// Tempo em segundos
                                                val resources = context.resources
                                                val velocidadey =
                                                    (it.h / 2).toFloat().toDp(resources)
                                                // var velocidadey = calculoVelocidade(it,py)


//
//                                                    if (velocidadey.toFloat() < it.h) {
//                                                        velocidadey = it.h
//                                                    }


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
                                                } else if (it.y == py && it.x == p) {
                                                    it.camada = -1
                                                    //  it.bloqueado = true
                                                }

                                            } else {
                                                if (it.camada == -3 || it.camada == -4) {
                                                    val py = (h * 0.6).toFloat()
                                                    val mediay = (it.y - py) / divisor
                                                    val velocidadey =
                                                        if ((mediay) > w * 0.01f) mediay else w * 0.1f



                                                    if (it.camada == -4) {
                                                        val mediax = it.h
                                                        if (it.x < (w * 0.70f)) {
                                                            it.x += mediax

                                                        }
                                                        if (it.y > (h * 0f)) {
                                                            it.y -= mediax

                                                        }
                                                        if (it.x >= (w * 0.7f) && it.y <= 20f) {
                                                            it.camada = -5
                                                            limitar = 7
                                                            tutor = false
                                                            pontosCont++
                                                            if (pontosCont >= 3) {
                                                                pontos++
                                                                pontosCont = 0

                                                            }

                                                        }
                                                    }

                                                    if (it.camada > -4) {
                                                        //
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

                                                }
                                            }
                                            if (it.y > h * 0.05f) {
                                                it.draw(canvas!!)
                                            }


                                        }
                                    } catch (e: Exception) {
                                        e.stackTrace
                                        //  b2 = bk
                                    }
                                    try {

                                        eliminarSelecao()
                                    } catch (eeee: Exception) {
                                        eeee.stackTrace
                                        //  b2 = bk
                                    }

                                    paint.color = Color.Magenta.toArgb()
                                    paint.alpha = 150


                                    canvas!!.drawRoundRect(
                                        RectF(
                                            (w * 0.72).toFloat(),
                                            ((h * 0.02).toFloat() - (w * 0.025).toFloat()),
                                            (w * 0.955).toFloat(),
                                            ((h * 0.02)).toFloat() + ((((w * 0.05)).toInt()) * 1.22f)
                                        ), 40f, 40f, paint
                                    )
                                    paint.color = Color.LightGray.toArgb()
                                    paint.alpha = 150

                                    canvas!!.drawRoundRect(
                                        RectF(
                                            (w * 0.72).toFloat(),
                                            ((h * 0.02).toFloat() - (w * 0.02).toFloat()),
                                            (w * 0.95).toFloat(),
                                            ((h * 0.02).toFloat()) + ((((w * 0.05)).toInt()) * 1.15f)
                                        ), 40f, 40f, paint
                                    )

                                    paint.textSize = spToPx((this.w * 0.016f))
                                    paint.color = Color.White.toArgb()
                                    canvas!!.drawText(
                                        pontos.toString(),
                                        w * 0.81f,
                                        h * 0.04f,
                                        paint
                                    )
                                    val expandir = (false.toString().length) * 10
                                    paint.color = Color.Magenta.toArgb()
                                    paint.alpha = 150
                                    canvas!!.drawRoundRect(
                                        RectF(
                                            (w * 0.12).toFloat(),
                                            ((h * 0.02).toFloat() - (w * 0.025).toFloat()),
                                            (w * 0.35).toFloat() + expandir,
                                            ((h * 0.02)).toFloat() + ((((w * 0.05)).toInt()) * 1.22f)
                                        ), 40f, 40f, paint
                                    )
                                    paint.color = Color.LightGray.toArgb()
                                    paint.alpha = 150
                                    canvas!!.drawRoundRect(
                                        RectF(
                                            (w * 0.125).toFloat(),
                                            ((h * 0.02).toFloat() - (w * 0.02).toFloat()),
                                            (w * 0.345).toFloat() + expandir,
                                            ((h * 0.02).toFloat()) + ((((w * 0.05)).toInt()) * 1.15f)
                                        ), 40f, 40f, paint
                                    )
                                    paint.color = Color.White.toArgb()
                                    canvas!!.drawText(
                                        "Nível $fase",
                                        w * 0.15f,
                                        h * 0.04f,
                                        paint
                                    )

                                    paint.alpha = 255
                                    canvas!!.drawBitmap(coinP, w * 0.7f, 0f, paint)


                                    //  }
//                                launch(Dispatchers.Default) {
//                                    Thread.sleep(30)
//
//
//                                }
                                    // }


                                    if (tiles.none { it.camada > 0 } && selectedTiles.none { it.camada != -5 }
                                        && !venceu
                                    ) {


                                        venceu = true
                                        val bd = BDTile(context)

                                        pontuacaoNova = bd.buscar().pontos.toInt()
                                        score = pontuacaoNova + pontos

                                    }
                                } catch (exx: Exception) {
                                    exx.stackTrace
                                }

                                if (venceu) {
                                    if (pontuacaoNova < score && venceuP.liberado) {
                                        pontuacaoNova++
                                    }

                                    venceuP.fase = ultimaFase + 1
                                    venceuP.pontos = pontuacaoNova

                                    venceuP.draw(canvas!!)


                                    if (venceuP.btm.liberar > 3) {
                                        //    finalizarFase()

                                        if (fase % 5 == 0 && fase != 0 && !semanuncio) {
                                            adsi()
                                        } else {
                                            finalizarFase()
                                        }

                                    } else if (venceuP.btmCoin.liberar > 3) {
                                        tipodePremio = 4
                                        adsr()

                                    }

                                }


/////////////////////////////////////////////////////////////////////////////
                            } else {
                                // validarSelecao(selectedTiles.filter { it.camada !=-5}.toMutableList())

                                try {


                                    val paint = Paint()
                                    paint.textSize = 150f
                                    canvas!!.drawBitmap(walld[0], 0f, 0f, paint)
                                    falhou = true

                                    if (corrigirfalha) {

                                        perdeuL.pontos = score
                                        perdeuL.fase = ultimaFase + 1
                                        perdeuL.pontos = score

                                        perdeuL.draw(canvas!!)
                                        if (semInternet) {
                                            semInternet = false
                                            perdeuL.btm.liberar = 0
                                            perdeuL.btmCoin.liberar = 0
                                            perdeuL.semInternet = true
                                        }

                                        if (perdeuL.btm.liberar > 3) {
                                            //    finalizarFase()
                                            tipodePremio = 0
                                            adsr()
                                        } else if (perdeuL.btmCoin.liberar > 3 && score >= valorminimo) {
                                            //    finalizarFase()
                                            val bd = BDTile(context)
                                            score -= valorminimo
                                            bd.atualizar(Base(fase, score.toLong()))

                                            perdeuL =
                                                Venceu(this.context, (w), (h), 1)
                                            limparComCredito = true
                                            perdeuL.btmCoin.liberar = 0

                                        }

                                    } else if (creditoRecorsus) {
                                        if (objX.tipo == 2) {

                                            criditar(credLuz, canvas!!)


                                            if (credLuz.btmCoin.liberar > 3 && score >= valorminimo) {
                                                posCredito(credLuz)
                                                credLuz =
                                                    Venceu(this.context, (w), (h), 2)
                                                luzP += 3
                                                credLuz.btmCoin.liberar = 0
                                                creditoRecorsus = false

                                            } else if (credLuz.btm.liberar > 3) {
                                                credLuz =
                                                    Venceu(this.context, (w), (h), 2)
                                                credLuz.btm.liberar = 0
                                                creditoRecorsus = false
                                                tipodePremio = 1
                                                adsr()


                                            }

                                        } else if (objX.tipo == 3) {

                                            criditar(credIma, canvas!!)


                                            if (credIma.btmCoin.liberar > 3 && score >= valorminimo) {
                                                posCredito(credIma)
                                                Venceu(this.context, (w), (h), 3).also {
                                                    credIma = it
                                                }
                                                imaP += 3
                                                credIma.btmCoin.liberar = 0
                                                creditoRecorsus = false

                                            } else if (credIma.btm.liberar > 3) {
                                                credIma =
                                                    Venceu(this.context, (w), (h), 3)
                                                credIma.btm.liberar = 0
                                                creditoRecorsus = false
                                                tipodePremio = 2
                                                adsr()

                                            }
                                        } else if (objX.tipo == 4) {

                                            criditar(credSufle, canvas!!)


                                            if (credSufle.btmCoin.liberar > 3 && score >= valorminimo) {
                                                posCredito(objX)
                                                credSufle =
                                                    Venceu(this.context, (w), (h), 4)
                                                sufleP += 3
                                                credSufle.btmCoin.liberar = 0
                                                creditoRecorsus = false

                                            } else if (credSufle.btm.liberar > 3) {
                                                credSufle =
                                                    Venceu(this.context, (w), (h), 4)
                                                credSufle.btm.liberar = 0
                                                creditoRecorsus = false
                                                tipodePremio = 3
                                                adsr()

                                            }

                                        }


                                    }



                                    paint.color = Color.Red.toArgb()


                                } catch (exx: Exception) {
                                    exx.stackTrace
                                }
                            }
                        }else{
                            lojaWAO.semanuncio=semanuncio
                            lojaWAO.draw(canvas)
                        }

                        try {


                            if (cLocked) {
                                surfaceHolder.unlockCanvasAndPost(canvas)
                                cLocked = false
                            }
                        } catch (exxg: Exception) {
                            exxg.stackTrace

                        }
//                        } catch (ew: Exception) {
//                            ew.stackTrace
//                        }
                    }


                }


            } catch (e: Exception) {
                e.printStackTrace()


            }

//kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk

            if (toque > 0) {
                toque--
            }

        }

    }



    private fun adsr() {
        gameView.showRewardedAd(
            onReward = {
                premiar = true
            },
            onAdClosed = {
                if (premiar) {
                    receberPremio()
                }
                gameView.recarregarRewardedAd()
            }
        )
    }

    private fun adsi() {
        finalizarFase()
        gameView.showInterstitialAd(

            onAdClosed = {

                gameView.recarregarIntersticialAd()

            }

        )
    }

    private fun receberPremio() {
        premiar = false

        when (tipodePremio) {
            0 -> reviver()
            1 -> luzP += 3
            2 -> imaP += 3
            3 -> sufleP += 3
            4 -> {
                score += 50

                venceuP.btmCoin.liberar = 0
            }
        }
    }

    private fun reviver() {
        perdeuL =
            Venceu(this.context, (w), (h), 1)
        limparComCredito = true
        perdeuL.btm.liberar = 0
    }

    private fun criditar(obj: Venceu, canvas: Canvas) {


        obj.pontos = score
        obj.fase = ultimaFase + 1
        obj.pontos = score

        obj.draw(canvas)


    }

    private fun posCredito(obj: Venceu) {
        val bd = BDTile(context)
        score -= valorminimo
        bd.atualizar(Base(fase, score.toLong()))

        limparComCredito = true
        obj.btmCoin.liberar = 0
    }

    private fun finalizarFase() {

        fase++

        venceu = false
        venceuP = Venceu(this.context, (w), (h), 0)
        val bd = BDTile(context)
        bd.atualizar(Base(fase, score.toLong(), luzP, imaP, sufleP))

        ultimaFase = fase

        popularTiles()


    }

    private fun eliminarSelecao() {
        val listr: MutableList<MahjongTile> = mutableListOf()
        selectedTiles.forEach {

            listr.add(it)

        }

        val listr2 = listr.filter { it.camada == -5 }.toMutableList()

        if (listr2.size > 3 && !limparComCredito) {
            val listrddd: MutableList<MahjongTile> = mutableListOf()
            var o = 0
            for (item in listr2.toList()) { // Criando uma cópia para evitar a modificação direta
                if (o < 3) {
                    listrddd.add(item)
                    o++
                } else {
                    break
                }
            }
            listr2.clear()
            listr2.addAll(listrddd)
        }
        if (listr2.size == 3 || limparComCredito) {
            for (item in selectedTiles.toList()) { // Criando uma cópia para evitar a modificação direta
                if (listr2.contains(item)) {
                    selectedTiles.remove(item)
                    // Adiciona sem erro
                }

            }


//            var i = 0
//            while (i < selectedTiles.size) {
//                val lValue: MahjongTile = selectedTiles[i]
//                if (listr2.contains(lValue)) {
//                   // selectedTiles.remove(lValue)
//                    selectedTiles[i].camada=-6
//                    i--
//                    // Adiciona sem erro
//                }
//                i++
//            }

            avaliar3 = false
        }


        dica = false
    }

    private fun render() {


        val canvas = surfaceHolder.lockCanvas()
        if (canvas != null) {
            canvas.drawRGB(0, 0, 0) // Limpa a tela
            tiles.forEach { it.draw(canvas) }

        }
    }

    private fun handleInput() {

    }

    init {
        val medida = this.h * 1.2f




        if (tiles.none { it.camada >= 0 }) {
            popularTiles()
            walld.add(
                Bitmap.createScaledBitmap(
                    BitmapFactory.decodeResource(
                        context.resources,
                        R.drawable.neve
                    ), (w), (medida).toInt(), false
                )
            )
            walld.add(
                Bitmap.createScaledBitmap(
                    BitmapFactory.decodeResource(
                        context.resources,
                        R.drawable.muralha
                    ), (w), (medida).toInt(), false
                )
            )
            walld.add(
                Bitmap.createScaledBitmap(
                    BitmapFactory.decodeResource(
                        context.resources,
                        R.drawable.lago
                    ), (w), (medida).toInt(), false
                )
            )
            walld.add(
                Bitmap.createScaledBitmap(
                    BitmapFactory.decodeResource(
                        context.resources,
                        R.drawable.verde
                    ), (w), (medida).toInt(), false
                )
            )
            // walld.add(BitmapFactory.decodeResource(context.resources, R.drawable.neve))
            walld.shuffle()
        }


    }

    private fun popularTiles() {
        avaliar3 = false
        ajustarY = true
        pontos = 0
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




        try {


            val canvasB = Canvas(b3)
            canvasB.drawColor(
                Color.Transparent.toArgb(),
                PorterDuff.Mode.CLEAR
            )
            paint.color = Color.Black.toArgb()
            paint.alpha = 150


            canvasB.drawRoundRect(
                RectF(
                    (this.w * 0.08).toFloat(),
                    ((this.h * 0.75).toFloat() - (this.w * 0.03).toFloat()),
                    (this.w * 0.93).toFloat(),
                    ((this.h * 0.75)).toFloat() + (((this.w * 0.9 / 8).toInt()) * 1.22f)
                ), 30f, 30f, paint
            )
            paint.color = Color(0xFF2F4F4F).toArgb()
            paint.alpha = 150

            canvasB.drawRoundRect(
                RectF(
                    (this.w * 0.09).toFloat(),
                    ((this.h * 0.75).toFloat() - (this.w * 0.02).toFloat()),
                    (this.w * 0.92).toFloat(),
                    ((this.h * 0.75).toFloat()) + (((this.w * 0.9 / 8).toInt()) * 1.15f)
                ), 30f, 30f, paint
            )
            paint.color = Color(0xFFE6E6FA).toArgb()
            paint.alpha = 150

            canvasB.drawRoundRect(
                RectF(
                    (this.w * 0.09).toFloat(),
                    ((this.h * 0.755).toFloat() - (this.w * 0.02).toFloat()),
                    (this.w * 0.92).toFloat(),
                    ((this.h * 0.755).toFloat()) + (((this.w * 0.9 / 8).toInt()) * 1.0f)
                ), 30f, 30f, paint
            )
            paint.color = Color.Black.toArgb()
            paint.alpha = 255

        } catch (ett: Exception) {
            ett.stackTrace
        }


        val disponiveis: MutableList<Int> = mutableListOf()


        val bd = BDTile(context)
        ultimaFase = bd.buscar().nivel


        val base = bd.buscar()

        luzP = base.luz
        imaP = base.ima
        sufleP = base.sufle
        score = bd.buscar().pontos.toInt()

        fase = ultimaFase




        when (fase) {
            0 -> {

                tiles = Quadrado000().quadrado(this.context, w, disponiveis, tileImages)
                tutor = true

            }

            1 -> {

                tiles = Quadrado0B().quadradoB(this.context, w, disponiveis, tileImages)

            }

            2 -> {

                tiles = Quadrado0A().quadradoA(this.context, w, disponiveis, tileImages)

            }

            3 -> {

                tiles = Quadrado01().quadrado(this.context, w, disponiveis, tileImages)

            }

            4 -> {

                tiles = Quadrado0C().quadradoC(this.context, w, disponiveis, tileImages)

            }

            5 -> {
                tiles = Plus0A().plus(this.context, w, disponiveis, tileImages)


            }

            6 -> {

                tiles = Quadrado0I().quadradoI(this.context, w, disponiveis, tileImages)

            }

            7 -> {

                tiles = Cabeca0A().cabeca(this.context, w, disponiveis, tileImages)

            }

            8 -> {

                tiles = Coracao0A().coracao(this.context, w, disponiveis, tileImages)

            }

            9 -> {

                tiles = Square0A().quadrado(this.context, w, disponiveis, tileImages)

            }

            10 -> {

                tiles = QuadradoA().quadradoA(this.context, w, disponiveis, tileImages)

            }

            11 -> {
                tiles = QuadradoB().quadradoB(this.context, w, disponiveis, tileImages)

            }

            12 -> {

                tiles = QuadradoC().quadradoC(this.context, w, disponiveis, tileImages)

            }

            13 -> {
                tiles = Plus().plus(this.context, w, disponiveis, tileImages)


            }

            14 -> {

                tiles = QuadradoI().quadradoI(this.context, w, disponiveis, tileImages)

            }

            15 -> {

                tiles = QuadradoT().quadradoT(this.context, w, disponiveis, tileImages)

            }

            16 -> {

                tiles = QuadradoF().quadrado(this.context, w, disponiveis, tileImages)

            }

            17 -> {

                tiles = QuadradoO().quadrado(this.context, w, disponiveis, tileImages)

            }

            18 -> {

                tiles = Arvore().arvore(this.context, w, disponiveis, tileImages)

            }

            19 -> {

                tiles = Cabeca().cabeca(this.context, w, disponiveis, tileImages)

            }

            20 -> {

                tiles = Coracao().coracao(this.context, w, disponiveis, tileImages)

            }

            21 -> {

                tiles = Peixe().quadrado(this.context, w, disponiveis, tileImages)

            }

            22 -> {

                tiles = SquareA().quadrado(this.context, w, disponiveis, tileImages)

            }

            23 -> {

                tiles = Square().quadrado(this.context, w, disponiveis, tileImages)

            }

            24 -> {

                tiles = QuadradoCC().quadradoC(this.context, w, disponiveis, tileImages)

            }

            else -> {

                tiles = QuadradoAleatorio().quadrado(this.context, w, disponiveis, tileImages)

            }
        }

        walld.shuffle()
        tiles.sortBy { it.camada }

    }

    private fun limparSelecionados() {
        if (selectedTiles.any { it.camada != -5 }) {
            val s: MutableList<MahjongTile> =
                selectedTiles.filter { it -> it.id == selectedTiles.filter { it.camada != -5 }[0].id }
                    .toMutableList()
            val tilesx: MutableList<MahjongTile> =
                tiles
                    .filter { it.camada == 0 && it.id == s[0].id }.toMutableList()
            val tilesx1: MutableList<MahjongTile> =
                tiles
                    .filter { it.camada == 1 && it.id == s[0].id }.toMutableList()
            val tilesx2: MutableList<MahjongTile> =
                tiles
                    .filter { it.camada == 2 && it.id == s[0].id }.toMutableList()
            val tilesx3: MutableList<MahjongTile> = mutableListOf()
            tilesx3.addAll(tilesx2)
            tilesx3.addAll(tilesx1)
            tilesx3.addAll(tilesx)
            limitar = 20
            if (s.size > 1) {
                achou(tilesx3[0])

            } else if (tilesx3.isNotEmpty()) {
                for (i in 0 until 2) {
                    try {
                        achou(tilesx3[i])
                    } catch (e: Exception) {
                        e.stackTrace
                    }

                }
            } else {
                achou(s[0])

            }


        }

        limpando = true
    }

    private fun embaralha() {
        val disponiveis: MutableList<MahjongTile> = mutableListOf()
        val tilesx: MutableList<MahjongTile> =
            tiles.filter { !selectedTiles.contains(it) }.filter { it.camada > -1 }.toMutableList()

        for (i in 0 until tilesx.size) {
            val m = MahjongTile(
                tilesx[i].image,
                this.context,
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


    private fun validarCamadas(
        list1: MutableList<MahjongTile>,
        list2: MutableList<MahjongTile>,
        grau: Int
    ) {

        var avaliar = false

        list1.forEach { it1 ->
            var liberado = true
            list2.forEach {
                if (it1.containsCamada(it.x, it.y)) {
                    liberado = false

                }
            }

            if (liberado) {
                it1.camada = grau
                avaliar = true
            }

        }
        if (avaliar) {
            isTouched = true
        }

    }


    private fun validarSelecao(list: MutableList<MahjongTile>) {
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
        if (selecionados == 3 && !avaliar3) {
            onTocarEfeito(1)
            avaliar3 = true
            listr.forEach {
                it.camada = -3
                if (it.ref > -1) {
                    tiles[it.ref].bloqueado = true
                }
            }
            removerDaLista.clear()
            removerDaLista.addAll(listr)

        }

    }

    private fun iaJogando() {
        val camada2 =
            tiles.filter { !selectedTiles.contains(it) }.filter { it.camada == 2 }.toMutableList()
        val tem3 = validarSelecaoIA(camada2, 3, 0)
        var tem2 = false
        itenImpossivel = false
        limitar = 20

        if (!tem3) {
            if (selectedTiles.none { it.camada != -5 }) {
                tem2 = validarSelecaoIA(camada2, 2, 0)
            }
            if (!tem2 && selectedTiles.any { it.camada != -5 }) {
                itenImpossivel = false

                validarSelecaoIA(selectedTiles, 2, 1)

            }
            limparSelecionados()

        }

    }

    private fun validarSelecaoIA(list: MutableList<MahjongTile>, min: Int, modo: Int): Boolean {
        val listr: MutableList<MahjongTile> = mutableListOf()
        val listr2: MutableList<MahjongTile> = mutableListOf()
        val listr3: MutableList<MahjongTile> = mutableListOf()

        if (modo == 0 || modo == 2) {
            list.forEach { it0 ->
                val listrd = list.filter { it.id == it0.id }.toMutableList()
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

                val temEspaco = 7 - selectedTiles.filter { it.camada != -5 }.size

                if (temEspaco < listr.size) {
                    itenImpossivel = true
                    return false
                }


                listr.filter { it.id == listr[0].id }.forEach {
                    achou(it)
                    i++
                }


                return true

            }
        } else if (modo == 1) {

            list.forEach { it0 ->
                val listrd = list.filter { it.id == it0.id }.toMutableList()
                if (listrd.size >= min) {
                    listr.add(it0)
                }
            }

            var i = 0

            for (j in 0..<listr.size) {
                if (j % 2 == 0) {
                    listr2.add(listr[j])

                }
            }
            listr2.forEach { o ->
                listr3.addAll(tiles.filter { !selectedTiles.contains(it) }.filter {
                    it.id == o.id && it.camada == 2
                }.toMutableList())
            }


            if (listr3.size > 1) {
                val m = listr3[0]
                listr3.clear()
                listr3.add(m)
            }

            if (i < 7 - selectedTiles.filter { it.camada != -5 }.size && listr3.isNotEmpty()) {
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
                    val listrd = listrr.filter { it.id == it0.id }.toMutableList()
                    if (listrd.size == 2) {
                        listrt.add(it0)
                    }
                }
                val temEspaco = 7 - selectedTiles.filter { it.camada != -5 }.size

                if (temEspaco < listrv.size) {
                    itenImpossivel = true

                    return false
                }
                listrt.forEach {
                    list.forEach { o ->

                        if (o.id == it.id) {
                            listrv.add(it)
                        }

                    }
                }
                //  var ii = 0

                if (temEspaco < listrv.size) {
                    itenImpossivel = true
                    return false
                }

                //if (ii < 7 - selectedTiles.size && listrv.isNotEmpty()) {
                listrv.forEach {
                    achou(it)
                    // ii++
                }
                return true
                // }


            }


        }

        if (modo == 2) {

            val listrx: MutableList<MahjongTile> = mutableListOf()

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

    private fun achou(tile: MahjongTile) {
        val m = MahjongTile(
            tile.image,
            this.context,
            tile.x,
            tile.y,
            120,
            120,
            -2,
            tile.id

        )
        m.y = tile.y
        m.ty = false
        m.naLista = true

        tile.ty = false
        val total = selectedTiles.filter { it.camada != -5 }
            .filter { it.id == tile.id && it.camada > -3 }.size + 1

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

    private fun onTocarEfeito(i: Int) {
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

            if (!lojaWAO.abrirLoja) {


                if (toque > 3) {
                    return
                }
                isTouched = true
                toque++

                if (event.action == MotionEvent.ACTION_DOWN && !limparComCredito) {


                    tiles.filter { !selectedTiles.contains(it) }.find {
                        it.containsTouch(
                            event.x,
                            event.y
                        ) && it.camada == 2 && selectedTiles.filter { it.camada > -3 }.size < 7
                    }?.let { tile ->

                        // carregarCamadas()

                        val m = MahjongTile(
                            tile.image,
                            this.context,
                            tile.x,
                            tile.y,
                            (this.w * 0.9 / 8).toInt(),
                            (this.w * 0.9 / 8).toInt(),
                            -2,
                            tile.id

                        )
                        m.y = tile.y
                        m.ty = false
                        m.naLista = true
                        tile.ty = false
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

                    if (bloquerBT && bloquerBT2 && !corrigirfalha && !venceu) {
                        if (botao1.containsTouch(
                                event.x,
                                event.y
                            ) && time1 == 0 && time3 == 0 && time2 == 0
                        ) {

                            if (luzP > 0 && !botao1.isSelected) {
                                time1 = 10
                                botao1.isSelected = true
                                dica = true
                                iaJogando()
                                luzP--
                            } else {
                                objX = credLuz
                                creditoRecorsus = true
                            }

                        } else if (botao2.containsTouch(
                                event.x,
                                event.y
                            )
                            && time1 == 0 && time3 == 0 && time2 == 0
                        ) {
                            if (imaP > 0 && !botao2.isSelected) {
                                time2 = 10
                                botao2.isSelected = true
                                limparSelecionados()
                                imaP--
                            } else if (selectedTiles.isNotEmpty()) {
                                objX = credIma
                                creditoRecorsus = true
                            }


                        } else if (botao3.containsTouch(
                                event.x,
                                event.y
                            )
                            && time1 == 0 && time3 == 0 && time2 == 0
                        ) {

                            if (sufleP > 0 && !botao3.isSelected) {
                                time3 = 10
                                botao3.isSelected = true
                                embaralhando = true
                                embaralha()
                                sufleP--
                            } else {
                                objX = credSufle
                                creditoRecorsus = true
                            }


                        }

                        bloquerBT2 = false

                    }

                    if (timePress == 0) {
                        if (venceuP.btm.containsTouch(
                                event.x,
                                event.y
                            )
                            && venceu && venceuP.btm.t == 1f
                        ) {
                            venceuP.btm.animar = true
                        } else if (venceuP.btmCoin.containsTouch(
                                event.x,
                                event.y
                            )
                            && venceu && venceuP.btmCoin.t == 1f
                        ) {
                            venceuP.btmCoin.animar = true
                        } else if (perdeuL.btm.containsTouch(
                                event.x,
                                event.y
                            )
                            && falhou && perdeuL.btm.t == 1f
                        ) {
                            perdeuL.btm.animar = true
                        } else if (perdeuL.btmCoin.containsTouch(
                                event.x,
                                event.y
                            )
                            && falhou && perdeuL.btmCoin.t == 1f
                        ) {
                            perdeuL.btmCoin.animar = true
                        } else if (credLuz.btm.containsTouch(
                                event.x,
                                event.y
                            )
                            && creditoRecorsus && credLuz.btm.t == 1f
                        ) {
                            credLuz.btm.animar = true
                        } else if (credLuz.btmCoin.containsTouch(
                                event.x,
                                event.y
                            )
                            && creditoRecorsus
                        ) {
                            credLuz.btmCoin.animar = true
                        } else if (credIma.btm.containsTouch(
                                event.x,
                                event.y
                            )
                            && creditoRecorsus && credIma.btm.t == 1f
                        ) {
                            credIma.btm.animar = true
                        } else if (credIma.btmCoin.containsTouch(
                                event.x,
                                event.y
                            )
                            && creditoRecorsus && credIma.btmCoin.t == 1f
                        ) {
                            credIma.btmCoin.animar = true
                        } else if (credSufle.btm.containsTouch(
                                event.x,
                                event.y
                            )
                            && creditoRecorsus && credSufle.btm.t == 1f
                        ) {
                            credSufle.btm.animar = true
                        } else if (credSufle.btmCoin.containsTouch(
                                event.x,
                                event.y
                            )
                            && creditoRecorsus && credSufle.btmCoin.t == 1f
                        ) {
                            credSufle.btmCoin.animar = true
                        }




                        if (btm.containsTouch(
                                event.x,
                                event.y
                            )
                        ) {

                            btm.animar = true

                        } else if (compraBT.containsTouch(
                                event.x,
                                event.y
                            )
                        ) {

                            lojaWAO.moedas=-2
                            lojaWAO.abrirLoja = true


                        }

                        timePress = 10
                    }


                }
            }else{
                lojaWAO.onTouchEvent(event)
            }

        } catch (e: Exception) {
            e.stackTrace
        }
    }




    private fun carregarCamadas() {

        val novoFiltro2 =
            tiles
                .filter { it.camada == 2 }
        val novoFiltro1 =
            tiles
                .filter { it.camada == 1 }
        val novoFiltro0 =
            tiles
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


    }

    private fun Float.toDp(resources: Resources): Float {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            this,
            resources.displayMetrics
        )
    }


    private fun spToPx(sp: Float): Float {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP,
            sp,
            context.resources.displayMetrics
        )
    }

}