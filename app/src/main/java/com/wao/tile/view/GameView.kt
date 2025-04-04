package com.wao.tile.view

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Handler
import android.os.Looper
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.wao.tile.egine.GameLoop
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.rewarded.RewardItem
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class GameView(context: Context) : SurfaceView(context), SurfaceHolder.Callback {
    lateinit var gameLoop: GameLoop
    private var tiles = mutableListOf<MahjongTile>()
    private var selectedTiles = mutableListOf<MahjongTile>()
    private var pontos = 0
    private var index = 0
    private var tutor = false
    private var rewardedAd: RewardedAd? = null
    private val networkReceiver = NetworkReceiver { loadRewardedAd() }
    private var carregado = false
    private var interstitialAd: InterstitialAd? = null
    init {
        // Inicializa o AdMob
        MobileAds.initialize(context) {
            loadRewardedAd() // Carrega o anúncio no início
            loadInterstitialAd()
        }
        holder.addCallback(this)

        // Registra o receiver para monitorar a conexão de rede
        val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        context.registerReceiver(networkReceiver, filter)
    }

    private fun loadInterstitialAd() {
        val adRequest = AdRequest.Builder().build()

        InterstitialAd.load(
            context,
            "ca-app-pub-1070048556704742/1994916985", // Substitua pelo seu ID real do AdMob
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(ad: InterstitialAd) {
                    interstitialAd = ad
                      println("AdMob: Anúncio intersticial carregado!")
                }

                override fun onAdFailedToLoad(error: LoadAdError) {
                    println("AdMob: Falha ao carregar anúncio - ${error.message}")
                }
            }
        )
    }

    private fun loadRewardedAd() {

        CoroutineScope(Dispatchers.Default).run  {

                val adRequest = AdRequest.Builder().build()

                RewardedAd.load(
                    context,
                    "ca-app-pub-1070048556704742/1458185776", // Substitua pelo seu ID real
                    adRequest,
                    object : RewardedAdLoadCallback() {
                        override fun onAdLoaded(ad: RewardedAd) {
                            rewardedAd = ad
                            carregado = true
                              println("AdMob: Anúncio premiado carregado!")
                        }

                        override fun onAdFailedToLoad(error: LoadAdError) {
                            println("AdMob: Erro ao carregar anúncio - ${error.message}")
                        }
                    }
                )


        }

    }


    fun showInterstitialAd(onAdClosed: () -> Unit) {
        val activity = context as? Activity

        if (interstitialAd != null && activity != null) {
            Handler(Looper.getMainLooper()).post {
                interstitialAd?.show(activity)

                // Callback para recarregar o anúncio após ser fechado
                interstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
                    override fun onAdDismissedFullScreenContent() {
                        println("AdMob: Anúncio intersticial fechado pelo usuário.")
                        onAdClosed() // Executa ação após o anúncio ser fechado
                        loadInterstitialAd() // Recarrega o anúncio para a próxima exibição
                    }

                    override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                        println("AdMob: Erro ao exibir o anúncio - ${adError.message}")
                    }
                }
            }
        } else {
            println("AdMob: Nenhum anúncio carregado. Tentando recarregar...")
            loadInterstitialAd()
            onAdClosed() // Se não houver anúncio, continua o fluxo do jogo
        }
    }

    fun showRewardedAd(onReward: () -> Unit, onAdClosed: () -> Unit) {
        rewardedAd?.let { ad ->
            val activity = context as? Activity

            if (activity != null) {
                Handler(Looper.getMainLooper()).post {
                    ad.show(activity) { rewardItem: RewardItem ->
                        println("Usuário recebeu recompensa de ${rewardItem.amount} ${rewardItem.type}")
                        onReward() // Dá a recompensa ao jogador
                    }

                    // Callback quando o anúncio for fechado
                    ad.fullScreenContentCallback = object : FullScreenContentCallback() {
                        override fun onAdDismissedFullScreenContent() {
                            println("Anúncio fechado pelo usuário.")
                            onAdClosed()
                            loadRewardedAd() // Recarrega o anúncio após ser fechado
                        }

                        override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                            println("Erro ao exibir o anúncio: ${adError.message}")

                            if (networkReceiver.temInternet) {
                                gameLoop.perdeuL.internetR = "Carregando..."

                            } else {
                                gameLoop.perdeuL.internetR = "Sem internet"
                                gameLoop.semInternet = true
                            }
                        }
                    }
                }
            } else {
                println("Erro: contexto não é uma Activity")
            }
        } ?: run {
            println("AdMob: Nenhum anúncio disponível, tentando recarregar...")
            gameLoop.semInternet = true
            if (networkReceiver.temInternet) {
                gameLoop.perdeuL.internetR = "Carregando..."

            } else {
                gameLoop.perdeuL.internetR = "Sem internet"
                gameLoop.semInternet = true

            }
            // loadRewardedAd()
        }
    }

    fun recarregarRewardedAd() {
        loadRewardedAd()
    }
    fun recarregarIntersticialAd() {
        loadInterstitialAd()
    }
    override fun surfaceCreated(holder: SurfaceHolder) {
        try {
            tiles = gameLoop.tiles
            pontos = gameLoop.pontos
            index = gameLoop.index
            tutor = gameLoop.tutor

            selectedTiles = gameLoop.selectedTiles
            selectedTiles.removeAll(gameLoop.removerDaLista)
            gameLoop.removerDaLista.clear()

        } catch (_: Exception) {
        }

        gameLoop = GameLoop(holder, context, this)
        if (this.tiles.isNotEmpty()) {
            gameLoop.tiles = this.tiles
            gameLoop.selectedTiles = this.selectedTiles
            gameLoop.pontos = this.pontos
            gameLoop.index = this.index
            gameLoop.tutor = this.tutor
        }
        gameLoop.startLoop()
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {


    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        gameLoop.stopLoop()
        context.unregisterReceiver(networkReceiver)

    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {

        gameLoop.onTouchEvent(event)


        return true
    }
}