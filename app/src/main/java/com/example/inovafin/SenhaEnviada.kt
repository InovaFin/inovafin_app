package com.example.inovafin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.inovafin.databinding.ActivitySenhaEnviadaBinding

class SenhaEnviada : AppCompatActivity() {

    private lateinit var binding: ActivitySenhaEnviadaBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySenhaEnviadaBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btLogin.setOnClickListener {
            var navegacaoTelaLogin = Intent(this, Login::class.java)
            startActivity(navegacaoTelaLogin)
        }
    }
}