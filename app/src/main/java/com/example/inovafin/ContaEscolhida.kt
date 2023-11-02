package com.example.inovafin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import com.example.inovafin.Util.ConfiguraBd
import com.example.inovafin.databinding.ActivityContaEscolhidaBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.text.NumberFormat
import java.util.Locale
import kotlinx.coroutines.*

class ContaEscolhida : AppCompatActivity() {

    private lateinit var binding: ActivityContaEscolhidaBinding

    private lateinit var autentificacao: FirebaseAuth

    private lateinit var firestore: FirebaseFirestore

    private var numberFormat = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))
    private var contaId: String = ""
    private var instituicao: String = ""
    private var saldoConta: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContaEscolhidaBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        autentificacao = ConfiguraBd.Firebaseautentificacao()
        firestore = ConfiguraBd.Firebasefirestore()

        contaId = intent.getStringExtra("contaId").toString()

        resgatarDados()

        binding.icFechar.setOnClickListener {
            onBackPressed()
        }

        binding.btEditar.setOnClickListener {
            val i = Intent(this, EditarConta::class.java)
            i.putExtra("contaId", contaId)
            i.putExtra("saldoConta", saldoConta)
            i.putExtra("instituicao", instituicao)
            startActivity(i)
        }

        binding.btTransferir.setOnClickListener {
            val i = Intent(this, TransferirValor::class.java)
            i.putExtra("contaId", contaId)
            startActivity(i)
        }

        binding.btExcluir.setOnClickListener {
            dialogConfirmacao()
        }
    }

    private fun resgatarDados() {
        val usuarioId = autentificacao.currentUser!!.uid

        try {
            // Resgatar dados aqui!
            firestore.collection("Usuarios").document(usuarioId)
                .collection("ContasBancarias").document(contaId)
                .addSnapshotListener { document, error ->
                    if (document != null) {
                        val saldoResgatado = document.getDouble("saldo")
                        val instituicaoResgatada = document.getString("instituicao")
                        val nomeResgatado = document.getString("nome")

                        val formatted = numberFormat.format(saldoResgatado)
                        saldoConta = formatted

                        instituicao = instituicaoResgatada.toString()

                        // Aplica os dados no layout
                        binding.titulo.text = nomeResgatado
                        binding.instituicao.text = "Saldo atual $instituicaoResgatada"
                        binding.saldo.text = formatted
                    } else {
                        Toast.makeText(applicationContext, "Erro ao resgatar", Toast.LENGTH_LONG).show()
                    }
                }
        } catch (e: Exception) {
            Toast.makeText(applicationContext, "Erro ao resgatar dados", Toast.LENGTH_LONG).show()
        }

    }

    fun dialogConfirmacao() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Confirmação")
        builder.setMessage("Tem certeza de que deseja excluir a conta bancaria?")

        builder.setPositiveButton("Sim") { dialog, which ->
            // Usuário confirmou a saída
            // Agora, inicie uma coroutine para excluir a conta
            lifecycleScope.launch {
                try {
                    excluirConta()
                } catch (e: Exception) {
                    // Lida com exceções que podem ocorrer na exclusão
                    Toast.makeText(applicationContext, "Erro ao excluir conta", Toast.LENGTH_LONG).show()
                }
            }

        }

        builder.setNegativeButton("Não") { dialog, which ->
            // Usuário cancelou a saída
        }

        val dialog = builder.create()
        dialog.show()
    }

    private suspend fun excluirConta() = CoroutineScope(Dispatchers.IO).launch {
        val usuarioId = autentificacao.currentUser!!.uid

        try {
            firestore.collection("Usuarios").document(usuarioId)
                .collection("ContasBancarias").document(contaId).delete().await()

            Toast.makeText(applicationContext, "Conta excluída", Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            Toast.makeText(applicationContext, "Erro ao excluir conta", Toast.LENGTH_LONG).show()
        }
    }
}