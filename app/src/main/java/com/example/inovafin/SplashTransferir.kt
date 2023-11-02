package com.example.inovafin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.inovafin.databinding.ActivitySplashTransferirBinding

class SplashTransferir : AppCompatActivity() {

    private lateinit var binding: ActivitySplashTransferirBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashTransferirBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val nomeRemetente = intent.getStringExtra("nomeRemetente")
        val nomeDestinatario = intent.getStringExtra("nomeDestinatario")
        val valorTransferido = intent.getStringExtra("valorTransferido")

        binding.nomeRemetente.text = nomeRemetente
        binding.nomeDestinatario.text = nomeDestinatario
        binding.valorTransferido.text = valorTransferido

        //      Lógica da Tela Splash
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MinhasContas::class.java)
            startActivity(intent)
            finish()
        }, 3800)
    }
}