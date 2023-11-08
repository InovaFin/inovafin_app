package com.example.inovafin

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.inovafin.Util.ConfiguraBd
import com.example.inovafin.databinding.ActivityRegistroReceberBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Locale

class RegistroReceber : AppCompatActivity() {

    private lateinit var binding: ActivityRegistroReceberBinding

    private lateinit var autentificacao: FirebaseAuth

    private lateinit var firestore: FirebaseFirestore

    private var numberFormat = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))

    private var registroId: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistroReceberBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        autentificacao = ConfiguraBd.Firebaseautentificacao()
        firestore = ConfiguraBd.Firebasefirestore()

        registroId = intent.getStringExtra("registroId").toString()

        resgatarDados()

        binding.icFechar.setOnClickListener {
            onBackPressed()
        }
    }

    private fun resgatarDados() {
        val usuarioId = autentificacao.currentUser!!.uid

        try {
            // Resgatar dados aqui!
            firestore.collection("Usuarios").document(usuarioId)
                .collection("ValoresReceber").document(registroId)
                .addSnapshotListener { document, error ->
                    if (document != null) {
                        val nome = document.getString("nome")

                        val vencimento = document.getTimestamp("vencimento")
                        val vencimentoString = if (vencimento != null) {
                            val date = vencimento.toDate()
                            val dateFormat = SimpleDateFormat("dd/MM/yyyy 'às' HH:mm", Locale.getDefault())
                            dateFormat.format(date)
                        } else {
                            "" // Caso o timestamp seja nulo, não aparecerá nada
                        }

                        val desc = document.getString("descricao")

                        val conta = document.getString("conta")

                        val valorResgatado = document.getDouble("valor")
                        val valorFormatado = numberFormat.format(valorResgatado)


                        binding.nomeRegistro.text = nome
                        binding.vencimentoRegistro.text = vencimentoString
                        binding.descRegistro.text = desc
                        binding.contaRegistro.text = conta
                        binding.valorRegistro.text = valorFormatado

                    } else {
                        Toast.makeText(applicationContext, "Erro ao resgatar", Toast.LENGTH_LONG).show()
                    }
                }
        } catch (e: Exception) {
            Toast.makeText(applicationContext, "Erro ao resgatar dados", Toast.LENGTH_LONG).show()
        }

    }
}