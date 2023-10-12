package com.example.inovafin

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
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

        binding.btCalculadora.setOnClickListener {
            try {
                val intent = Intent()
                intent.action = Intent.ACTION_MAIN
                intent.addCategory(Intent.CATEGORY_APP_CALCULATOR)
                startActivity(intent)
            } catch (e: Exception) {
                Toast.makeText(applicationContext, "Não foi possível abrir a calculadora.", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btAjuda.setOnClickListener {
            val navegarTelaAjuda = Intent(this, AjudaSaldoGeral::class.java)
            startActivity(navegarTelaAjuda)
        }

        binding.btSaldoGeralReceber.setOnClickListener {
            val navegarTelaSaldoGeralReceber = Intent(this, SaldoGeralReceber::class.java)
            startActivity(navegarTelaSaldoGeralReceber)
        }

        binding.btSaldoGeralGuardado.setOnClickListener {
            val navegarTelaSaldoGeralGuardado = Intent(this, SaldoGeralGuardado::class.java)
            startActivity(navegarTelaSaldoGeralGuardado)
        }

        binding.btSaldoGeralPagar.setOnClickListener {
            val navegarTelaSaldoGeralPagar = Intent(this, SaldoGeralPagar::class.java)
            startActivity(navegarTelaSaldoGeralPagar)
        }
    }
}