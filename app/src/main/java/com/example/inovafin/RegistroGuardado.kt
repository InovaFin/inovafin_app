package com.example.inovafin

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.inovafin.Util.ConfiguraBd
import com.example.inovafin.databinding.ActivityRegistroGuardadoBinding
import com.example.inovafin.databinding.ActivityRegistroReceberBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Locale

class RegistroGuardado : AppCompatActivity() {

    private lateinit var binding: ActivityRegistroGuardadoBinding

    private lateinit var autentificacao: FirebaseAuth

    private lateinit var firestore: FirebaseFirestore

    private var numberFormat = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))

    private var registroId: String = ""
    private var valorRegistro: Double = 0.0
    private var contaVinculada: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistroGuardadoBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        autentificacao = ConfiguraBd.Firebaseautentificacao()
        firestore = ConfiguraBd.Firebasefirestore()

        registroId = intent.getStringExtra("registroId").toString()

        resgatarDados()

        binding.icFechar.setOnClickListener {
            onBackPressed()
        }

        binding.btReceber.setOnClickListener {
            dialogConfirmacao()
        }

        binding.btEditar.setOnClickListener {
            val i = Intent(this, EditarGuardado::class.java)
            i.putExtra("registroId", registroId)
            startActivity(i)
        }

        binding.btExcluir.setOnClickListener {
            dialogConfirmacaoEx()
        }
    }

    fun dialogConfirmacao() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Confirmação")
        builder.setMessage("Você deseja somar o valor do registro à conta relacionada?")

        builder.setPositiveButton("Sim") { dialog, which ->
            receberRegistro()
        }

        builder.setNegativeButton("Não") { dialog, which ->
            // Usuário cancelou a saída
        }

        val dialog = builder.create()
        dialog.show()
    }

    fun dialogConfirmacaoEx() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Confirmação")
        builder.setMessage("Você deseja excluir esse registro?")

        builder.setPositiveButton("Sim") { dialog, which ->
            excluirRegistro()
        }

        builder.setNegativeButton("Não") { dialog, which ->
            // Usuário cancelou a saída
        }

        val dialog = builder.create()
        dialog.show()
    }

    private fun receberRegistro() {
        val usuarioId = autentificacao.currentUser!!.uid

        // resgata a conta relacionada
        firestore.collection("Usuarios").document(usuarioId)
            .collection("ContasBancarias").whereEqualTo("nome", contaVinculada)
            .get()
            .addOnSuccessListener { documents ->
                if (documents.isEmpty) {
                    // A conta não existe mais
                    Toast.makeText(applicationContext, "Conta relacionada não existe", Toast.LENGTH_LONG).show()
                } else {
                    for (document in documents) {
                        val contaId = document.id

                        val valorConta = document.getDouble("saldo")
                        val soma = valorConta?.plus(valorRegistro)

                        // soma o registro ao saldo da conta
                        firestore.collection("Usuarios").document(usuarioId)
                            .collection("ContasBancarias").document(contaId)
                            .update("saldo", soma)
                            .addOnSuccessListener {

                                excluirRegistro()
                            }
                    }
                }
            }
            .addOnFailureListener {
                Toast.makeText(applicationContext, "Erro ao buscar conta relacionada", Toast.LENGTH_LONG).show()
            }
    }

    private fun excluirRegistro() {
        val usuarioId = autentificacao.currentUser!!.uid

        firestore.collection("Usuarios").document(usuarioId)
            .collection("ValoresGuardado").document(registroId)
            .delete()
            .addOnSuccessListener {
                finish() // Encerra a atividade
            }
            .addOnFailureListener {
                Toast.makeText(applicationContext, "Erro ao excluir registro", Toast.LENGTH_LONG).show()
            }
    }

    private fun resgatarDados() {
        val usuarioId = autentificacao.currentUser!!.uid

        try {
            // Resgatar dados aqui!
            firestore.collection("Usuarios").document(usuarioId)
                .collection("ValoresGuardado").document(registroId)
                .addSnapshotListener { document, error ->
                    if (document != null && document.exists()) {
                        val nome = document.getString("nome")

                        val desc = document.getString("descricao")

                        val conta = document.getString("conta")
                        if (conta != null) {
                            contaVinculada = conta
                        }

                        val valorResgatado = document.getDouble("valor")
                        if (valorResgatado != null) {
                            valorRegistro = valorResgatado
                        }
                        val valorFormatado = numberFormat.format(valorResgatado)


                        binding.nomeRegistro.text = nome
                        binding.descRegistro.text = desc
                        binding.contaRegistro.text = conta
                        binding.valorRegistro.text = valorFormatado

                    }
                    else {
                        // Registro foi deletado
                        Toast.makeText(applicationContext, "Registro recebido e/ou excluído", Toast.LENGTH_LONG).show()
                        finish() // Encerra a atividade
                    }
                }
        } catch (e: Exception) {
            Toast.makeText(applicationContext, "Erro ao resgatar dados", Toast.LENGTH_LONG).show()
        }

    }
}