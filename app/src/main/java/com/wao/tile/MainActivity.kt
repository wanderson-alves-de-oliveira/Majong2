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
import com.wao.tile.ferramentas.NotificationScheduler


class MainActivity : Activity() {
    private lateinit var gameView: GameView
    private lateinit var adView: AdView

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



        // Criando o AdView

//        adView = AdView(this).apply {
//              AdSize.BANNER
//            adUnitId = "ca-app-pub-3940256099942544/6300978111" // Substitua pelo seu ID real
//            loadAd(AdRequest.Builder().build())
//        }



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


        gameView = GameView(this)
        layout.addView(gameView, gameParams) // Adiciona o jogo
            layout.addView(this.adView ,adParams)




        // Adicionando o AdView ao
        setContentView(layout)

    }



    override fun onResume() {
        super.onResume()

    }


}