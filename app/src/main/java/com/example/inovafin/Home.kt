package com.example.inovafin

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.inovafin.Util.ConfiguraBd
import com.example.inovafin.databinding.ActivityHomeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class Home : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    private lateinit var autentificacao: FirebaseAuth

    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        autentificacao = ConfiguraBd.Firebaseautentificacao()
        firestore = ConfiguraBd.Firebasefirestore()

        binding.btUsuario.setOnClickListener {
            var navegarTelaConfiguracoes = Intent(this, Configuracoes::class.java)
            startActivity(navegarTelaConfiguracoes)
        }

        binding.nomeUsuario.setOnClickListener {
            Toast.makeText(applicationContext, "Nome Usuario", Toast.LENGTH_SHORT).show()
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

        binding.btNotificacao.setOnClickListener {
            var navegarTelaNotificacoes = Intent(this, Notificacoes::class.java)
            startActivity(navegarTelaNotificacoes)
        }

        binding.btSaldoGeral.setOnClickListener {
            var navegarTelaSaldoGeral = Intent(this, SaldoGeral::class.java)
            startActivity(navegarTelaSaldoGeral)
        }

        binding.btMinhasContas.setOnClickListener {
            var navegarTelaMinhasContas = Intent(this, MinhasContas::class.java)
            startActivity(navegarTelaMinhasContas)
        }

        binding.btValorReceber.setOnClickListener {
            var navegarTelaValorReceber = Intent(this, ValorReceber::class.java)
            startActivity(navegarTelaValorReceber)
        }

        binding.btValorPagar.setOnClickListener {
            val navegarTelaValorPagar = Intent(this, ValorPagar::class.java)
            startActivity(navegarTelaValorPagar)
        }

        binding.btValorGuardado.setOnClickListener {
            var navegarTelaValorGuardado = Intent(this, ValorGuardado::class.java)
            startActivity(navegarTelaValorGuardado)
        }
    }

    override fun onStart() {
        super.onStart()
        val usuarioAuth = autentificacao.currentUser

        if (usuarioAuth != null) {

            val usuarioId = autentificacao.currentUser!!.uid

            // Resgatar dados aqui!
            firestore.collection("Usuarios").document(usuarioId)
                .addSnapshotListener { document, error ->
                    if (document != null) {
                        binding.nomeUsuario.text = document.getString("nome")
                    }
                }
        }
    }
}