package com.example.inovafin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//      Lógica da Tela Splash
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, LoginCadastro::class.java)
            startActivity(intent)
            finish()
        }, 2800)
    }
}