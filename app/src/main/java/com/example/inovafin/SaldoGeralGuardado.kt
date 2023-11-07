package com.example.inovafin

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.inovafin.databinding.ActivitySaldoGeralGuardadoBinding

class SaldoGeralGuardado : AppCompatActivity() {

    private lateinit var binding: ActivitySaldoGeralGuardadoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySaldoGeralGuardadoBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.icFechar.setOnClickListener {
            onBackPressed()
        }

        binding.btRegistro.setOnClickListener {
            val navegarTelaRegistro = Intent (this, RegistroReceber::class.java)
            startActivity(navegarTelaRegistro)
        }
    }
}