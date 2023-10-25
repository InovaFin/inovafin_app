package com.example.inovafin.Util

import android.content.Context
import android.view.View
import com.airbnb.lottie.LottieAnimationView

class AnimacaoDeLoad(
    private val animacaoView: LottieAnimationView,
    private val textoView: View,
    private val context: Context
) {
    fun iniciarAnimacao() {
        // Tornar a animação visível e iniciar
        animacaoView.visibility = View.VISIBLE
        animacaoView.playAnimation()

        // Ocultar o texto do botão
        textoView.visibility = View.GONE
    }

    fun pararAnimacao() {
        animacaoView.cancelAnimation()
        animacaoView.visibility = View.GONE

        textoView.visibility = View.VISIBLE
    }
}
