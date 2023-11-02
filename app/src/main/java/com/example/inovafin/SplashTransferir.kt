package com.example.inovafin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper

class SplashTransferir : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_transferir)

//        //      Lógica da Tela Splash
//        Handler(Looper.getMainLooper()).postDelayed({
//            val intent = Intent(this, MinhasContas::class.java)
//            startActivity(intent)
//            finish()
//        }, 1800)
    }
}