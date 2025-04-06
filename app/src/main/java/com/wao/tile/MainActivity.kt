package com.wao.tile

import android.app.Activity
import android.os.Bundle
import android.view.WindowManager
import android.widget.FrameLayout
import com.wao.tile.view.GameView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.wao.tile.ferramentas.BillingManager
import com.wao.tile.ferramentas.NotificationScheduler


@Suppress("DEPRECATION")
class MainActivity : Activity() {
    private lateinit var gameView: GameView
    private lateinit var adView: AdView
    private  var semanuncio = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        ) //coloca em fullscreen
        this.window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        NotificationScheduler.scheduleNotification(this)

        MobileAds.initialize(this@MainActivity) {}

        val layout = FrameLayout(this)

        val adView = AdView(this)
        adView.adUnitId = "ca-app-pub-1070048556704742/5734943336"
        adView.setAdSize(AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(this, 360))
        this.adView = adView

        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)




        val gameParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.WRAP_CONTENT
        )
        val adParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.WRAP_CONTENT
        ).apply {
            gravity = android.view.Gravity.BOTTOM // Fixa o banner na parte inferior
        }
        adParams.bottomMargin = 0  // Alinhar ao rodapé


        val billingManager = BillingManager(
            context = this,
            onCoinsPurchased = { qtd ->
                gameView.adicionarMoedas(qtd)
            },
            onRemoveAdsPurchased = {
                // Por exemplo, desativar banner ou rewarded ads
                gameView.removerAnuncios()
            }
        )

        if (billingManager.foiComprado("remove_ads")) {
            gameView.removerAnuncios()
            semanuncio = true
        }


        gameView = GameView(this, billingManager)

        layout.addView(gameView, gameParams) // Adiciona o jogo
        if(!semanuncio) {
            layout.addView(this.adView, adParams)
        }

        setContentView(layout)

    }


}