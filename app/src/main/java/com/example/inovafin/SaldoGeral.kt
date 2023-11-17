package com.example.inovafin

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.inovafin.Util.ConfiguraBd
import com.example.inovafin.databinding.ActivitySaldoGeralBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SaldoGeral : AppCompatActivity() {

    private lateinit var binding: ActivitySaldoGeralBinding

    private lateinit var autentificacao: FirebaseAuth

    private lateinit var firestore: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySaldoGeralBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        autentificacao = ConfiguraBd.Firebaseautentificacao()
        firestore = ConfiguraBd.Firebasefirestore()

        resgatarDados()

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
            val i = Intent(this, AjudaSaldoGeral::class.java)
            startActivity(i)
        }

        binding.btSaldoGeralReceber.setOnClickListener {
            val i = Intent(this, SaldoGeralReceber::class.java)
            startActivity(i)
        }

        binding.btSaldoGeralGuardado.setOnClickListener {
            val i = Intent(this, SaldoGeralGuardado::class.java)
            startActivity(i)
        }

        binding.btSaldoGeralPagar.setOnClickListener {
            val i = Intent(this, SaldoGeralPagar::class.java)
            startActivity(i)
        }
    }

    private fun resgatarDados() {
        val usuarioId = autentificacao.currentUser!!.uid

        firestore.collection("Usuarios").document(usuarioId)
            .collection("ContasBancarias")
            .whereEqualTo("saldo", true)
            .get()
            .addOnSuccessListener {
                
            }
    }
}