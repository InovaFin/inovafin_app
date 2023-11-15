package com.example.inovafin

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.inovafin.Util.ConfiguraBd
import com.example.inovafin.databinding.ActivityRegistroPagarBinding
import com.example.inovafin.databinding.ActivityRegistroReceberBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Locale

class RegistroPagar : AppCompatActivity() {

    private lateinit var binding: ActivityRegistroPagarBinding

    private lateinit var autentificacao: FirebaseAuth

    private lateinit var firestore: FirebaseFirestore

    private var numberFormat = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))

    private var registroId: String = ""
    private var valorRegistro: Double = 0.0
    private var contaVinculada: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistroPagarBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        autentificacao = ConfiguraBd.Firebaseautentificacao()
        firestore = ConfiguraBd.Firebasefirestore()

        registroId = intent.getStringExtra("registroId").toString()

        resgatarDados()

        binding.icFechar.setOnClickListener {
            onBackPressed()
        }

        binding.btPagar.setOnClickListener {
            dialogConfirmacao()
        }

        binding.btEditar.setOnClickListener {
            val i = Intent(this, EditarPagar::class.java)
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
        builder.setMessage("Você deseja subtrair o registro a conta relacionada?")

        builder.setPositiveButton("Sim") { dialog, which ->
            pagarRegistro()
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

    private fun pagarRegistro() {
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
                        val subtracao = valorConta?.minus(valorRegistro)

                        // soma o registro ao saldo da conta
                        firestore.collection("Usuarios").document(usuarioId)
                            .collection("ContasBancarias").document(contaId)
                            .update("saldo", subtracao)
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
            .collection("ContasPagar").document(registroId)
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
                .collection("ContasPagar").document(registroId)
                .addSnapshotListener { document, error ->
                    if (document != null && document.exists()) {
                        val nome = document.getString("nome")

                        val vencimento = document.getTimestamp("vencimento")
                        val vencimentoString = if (vencimento != null) {
                            val date = vencimento.toDate()
                            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                            dateFormat.format(date)
                        } else {
                            "" // Caso o timestamp seja nulo, não aparecerá nada
                        }

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
                        binding.vencimentoRegistro.text = vencimentoString
                        binding.descRegistro.text = desc
                        binding.contaRegistro.text = conta
                        binding.valorRegistro.text = valorFormatado

                    }
                    else {
                        // Registro foi deletado
                        Toast.makeText(applicationContext, "Registro pago e/ou excluído", Toast.LENGTH_SHORT).show()
                        finish() // Encerra a atividade
                    }
                }
        } catch (e: Exception) {
            Toast.makeText(applicationContext, "Erro ao resgatar dados", Toast.LENGTH_SHORT).show()
        }

    }
}