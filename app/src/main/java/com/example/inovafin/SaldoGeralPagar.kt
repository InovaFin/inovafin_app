package com.example.inovafin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.inovafin.databinding.ActivitySaldoGeralPagarBinding

class SaldoGeralPagar : AppCompatActivity() {

    private lateinit var binding: ActivitySaldoGeralPagarBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySaldoGeralPagarBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(R.layout.activity_saldo_geral_pagar)

        binding.icFechar.setOnClickListener {
            onBackPressed()
        }
    }
}