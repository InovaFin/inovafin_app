package com.example.inovafin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.inovafin.databinding.ActivityLoginCadastroBinding

class LoginCadastro : AppCompatActivity() {

    private lateinit var binding: ActivityLoginCadastroBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginCadastroBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}