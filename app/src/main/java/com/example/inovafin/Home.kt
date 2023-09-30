package com.example.inovafin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import com.example.inovafin.databinding.ActivityHomeBinding

class Home : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btPerfil.setOnClickListener {
            Toast.makeText(applicationContext, "Foto Perfil", Toast.LENGTH_SHORT).show()
        }

        binding.nomeUsuario.setOnClickListener {
            Toast.makeText(applicationContext, "Nome Usuario", Toast.LENGTH_SHORT).show()
        }

        binding.btCalculadora.setOnClickListener {
            Toast.makeText(applicationContext, "Calculadora", Toast.LENGTH_SHORT).show()
        }

        binding.btNotificacao.setOnClickListener {
            Toast.makeText(applicationContext, "Notificações", Toast.LENGTH_SHORT).show()
        }

        binding.btConfig.setOnClickListener {
            Toast.makeText(applicationContext, "Configurações", Toast.LENGTH_SHORT).show()
        }
    }
}