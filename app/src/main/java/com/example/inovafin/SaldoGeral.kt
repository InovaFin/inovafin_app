package com.example.inovafin

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.inovafin.databinding.ActivitySaldoGeralBinding

class SaldoGeral : AppCompatActivity() {

    private lateinit var binding: ActivitySaldoGeralBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySaldoGeralBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.icFechar.setOnClickListener {
            onBackPressed()
        }

        binding.btAjuda.setOnClickListener {
            val navegarTelaAjuda = Intent(this, AjudaSaldoGeral::class.java)
            startActivity(navegarTelaAjuda)
        }
    }
}