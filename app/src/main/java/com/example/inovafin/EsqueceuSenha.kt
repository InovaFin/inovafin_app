package com.example.inovafin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.inovafin.databinding.ActivityEsqueceuSenhaBinding

class EsqueceuSenha : AppCompatActivity() {

    private lateinit var binding: ActivityEsqueceuSenhaBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEsqueceuSenhaBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.icVoltar.setOnClickListener {
            val voltarTela = Intent(this, Login::class.java)
            startActivity(voltarTela)
        }
    }
}