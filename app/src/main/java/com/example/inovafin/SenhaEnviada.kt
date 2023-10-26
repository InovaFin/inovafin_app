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
            var i = Intent(this, Login::class.java)
            startActivity(i)
        }
    }

    override fun onBackPressed() {
        // Impede que o usuário volte para a tela anterior
    }
}