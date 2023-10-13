package com.example.inovafin

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.inovafin.databinding.ActivitySaldoGeralReceberBinding

class SaldoGeralReceber : AppCompatActivity() {

    private lateinit var binding: ActivitySaldoGeralReceberBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySaldoGeralReceberBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.icFechar.setOnClickListener {
            onBackPressed()
        }

        binding.btRegistro.setOnClickListener {
            val navegarTelaRegistro = Intent (this, InfoRegistro::class.java)
            startActivity(navegarTelaRegistro)
        }
    }
}