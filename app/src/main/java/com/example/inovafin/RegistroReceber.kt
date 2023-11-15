package com.example.inovafin

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.inovafin.Util.ConfiguraBd
import com.example.inovafin.databinding.ActivityRegistroReceberBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Locale

class RegistroReceber : AppCompatActivity() {

    private lateinit var binding: ActivityRegistroReceberBinding

    private lateinit var autentificacao: FirebaseAuth

    private lateinit var firestore: FirebaseFirestore

    private var numberFormat = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))

    private var registroId: String = ""
    private var valorRegistro: Double = 0.0
    private var contaVinculada: String = ""
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

        binding.btReceber.setOnClickListener {
            dialogConfirmacao()
        }

        binding.btEditar.setOnClickListener {
            val i = Intent(this, EditarReceber::class.java)
            i.putExtra("registroId", registroId)
            startActivity(i)
        }
    }

    fun dialogConfirmacao() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Confirmação")
        builder.setMessage("Você deseja atribuir o registro à conta relacionada?")

        builder.setPositiveButton("Sim") { dialog, which ->
            // Usuário confirmou a saída
            // Agora, inicie uma coroutine para excluir a conta
            lifecycleScope.launch {
                try {
                    navegar()
                    receberRegistro()
                } catch (e: Exception) {
                    // Lida com exceções que podem ocorrer na exclusão
                    Toast.makeText(applicationContext, "Erro: $e", Toast.LENGTH_LONG).show()
                }
            }

        }

        builder.setNegativeButton("Não") { dialog, which ->
            // Usuário cancelou a saída
        }

        val dialog = builder.create()
        dialog.show()
    }

    private fun navegar() {
        val i = Intent(this, ValorReceber::class.java)
        startActivity(i)
    }

    private fun receberRegistro() = CoroutineScope(Dispatchers.IO).launch{
        val usuarioId = autentificacao.currentUser!!.uid

        // resgata a conta relacionada
        firestore.collection("Usuarios").document(usuarioId)
            .collection("ContasBancarias").whereEqualTo("nome", contaVinculada)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val contaId = document.id

                    val valorConta = document.getDouble("saldo")
                    val soma = valorConta?.plus(valorRegistro)

                    // soma o registro ao saldo da conta
                    firestore.collection("Usuarios").document(usuarioId)
                        .collection("ContasBancarias").document(contaId)
                        .update("saldo", soma)
                        .addOnSuccessListener {

                            // exclui registro
                            firestore.collection("Usuarios").document(usuarioId)
                                .collection("ValoresReceber").document(registroId)
                                .delete()
                        }
                }
            }
    }

    private fun resgatarDados() {
        val usuarioId = autentificacao.currentUser!!.uid

        try {
            // Resgatar dados aqui!
            firestore.collection("Usuarios").document(usuarioId)
                .collection("ValoresReceber").document(registroId)
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
                        // Se o documento não existe, você pode lidar com isso aqui
                        Toast.makeText(applicationContext, "Registro não encontrado", Toast.LENGTH_LONG).show()
                        finish() // Encerra a atividade, já que o registro não existe mais
                    }
                }
        } catch (e: Exception) {
            Toast.makeText(applicationContext, "Erro ao resgatar dados", Toast.LENGTH_LONG).show()
        }

    }
}