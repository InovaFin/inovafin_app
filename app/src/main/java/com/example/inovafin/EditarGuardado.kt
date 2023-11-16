package com.example.inovafin

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import com.example.inovafin.Util.AnimacaoDeLoad
import com.example.inovafin.Util.ConfiguraBd
import com.example.inovafin.databinding.ActivityEditarGuardadoBinding
import com.example.inovafin.databinding.ActivityEditarReceberBinding
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.math.BigDecimal
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class EditarGuardado : AppCompatActivity() {

    private lateinit var binding: ActivityEditarGuardadoBinding

    private lateinit var autentificacao: FirebaseAuth

    private lateinit var firestore: FirebaseFirestore

    private lateinit var animacaoDeLoad: AnimacaoDeLoad

    private var numberFormat = NumberFormat.getCurrencyInstance()

    private var registroId: String = ""

    private var contaAlterada: Boolean = false
    private var valorAtualFinal: Double = 0.0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditarGuardadoBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        autentificacao = ConfiguraBd.Firebaseautentificacao()
        firestore = ConfiguraBd.Firebasefirestore()
        animacaoDeLoad = AnimacaoDeLoad(binding.btAnimacao, binding.btText, this)

        registroId =  intent.getStringExtra("registroId").toString()

        resgatarDados()
        resgatarContas()
        formatandoSaldo()

        binding.icFechar.setOnClickListener {
            onBackPressed()
        }

        binding.btAlterar.setOnClickListener {
            animacaoDeLoad.iniciarAnimacao()
            validarCampos()
        }
    }

    private fun resgatarDados() {
        val usuarioId = autentificacao.currentUser!!.uid

        firestore.collection("Usuarios").document(usuarioId)
            .collection("ValoresGuardado").document(registroId)
            .addSnapshotListener { document, error ->
                if (document != null) {
                    val nome = document.getString("nome")
                    val desc = document.getString("descricao")

                    val valorResgatado = document.getDouble("valor")
                    val valorFormatado = numberFormat.format(valorResgatado)


                    binding.nomeGuardado.setText(nome)
                    binding.descricao.setText(desc)
                    binding.valorGuardado.setText(valorFormatado)

                } else {
                    Toast.makeText(applicationContext, "Erro ao resgatar", Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun resgatarContas() {
        val usuarioId = autentificacao.currentUser!!.uid

        firestore.collection("Usuarios").document(usuarioId)
            .collection("ContasBancarias")
            .get()
            .addOnSuccessListener { documents ->
                val nomesContas = mutableListOf<String>()

                for (document in documents) {
                    val nomeConta = document.getString("nome")
                    if (nomeConta != null) {
                        nomesContas.add(nomeConta)
                    }
                }

                // Agora você tem a lista de nomes das contas bancárias
                configurarSpinner(nomesContas)
            }
            .addOnFailureListener { exception ->
                Toast.makeText(applicationContext, "Erro: $exception", Toast.LENGTH_LONG).show()
            }
    }

    private fun configurarSpinner(nomesContas: MutableList<String>) {
        val spinnerOP = mutableListOf("Selecione uma conta")
        spinnerOP.addAll(nomesContas)

        // Cria um adapter para o spinner com a lista de nomes
        val adapter = ArrayAdapter(this, R.layout.item_spinner_layout, spinnerOP)

        binding.spinner.adapter = adapter
    }

    private fun verificarSpinner() {
        val valorSpinner = binding.spinner.selectedItem

        contaAlterada = valorSpinner != "Selecione uma conta"
    }

    private fun formatandoSaldo() {
        val editTextMonetario = binding.valorGuardado

        // Defina o texto inicial como "R$ "
        editTextMonetario.setText("R$ 0,00")

        // Posicione o cursor no final do texto
        editTextMonetario.setSelection(editTextMonetario.text.length)

        // Adicionando o MoneyTextWatcher ao Saldo Atual
        editTextMonetario.addTextChangedListener(MoneyTextWatcher(editTextMonetario))
    }

    inner class MoneyTextWatcher(private val editText: EditText) : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            editText.removeTextChangedListener(this)

            try {
                val initialString = s.toString()

                // Limpar o formato monetário anterior (remover vírgulas, pontos e símbolos de moeda)
                val cleanString = initialString.replace(Regex("[^\\d]"), "")

                val parsed = cleanString.toBigDecimal().divide(BigDecimal(100))

                val formatted = numberFormat.format(parsed)
                editText.setText(formatted)
                editText.setSelection(formatted.length)
            } catch (e: Exception) {
                // Tratar exceção, se necessário
            }

            editText.addTextChangedListener(this)
        }
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            // Não é necessário implementar
        }

    }

    private fun verificarSaldo() {
        val valor = binding.valorGuardado.text.toString()

        // Remova o símbolo da moeda, nesse caso "R$ "
        val valorSemSimbolo = valor.replace(NumberFormat.getCurrencyInstance().currency.symbol, "")

        // Remova todos os caracteres que não são dígitos (0-9), incluindo pontos e vírgulas
        val valorSemFormatacao = valorSemSimbolo.replace(Regex("[^\\d]"), "")

        valorAtualFinal = valorSemFormatacao.toDouble() / 100.0
    }

    private fun validarCampos() {
        val nome = binding.nomeGuardado.text.toString()
        verificarSpinner()
        verificarSaldo()

        if (nome.isNotEmpty()) {
            if (contaAlterada) {
                if (valorAtualFinal > 0.0) {
                    alterarRegistro()
                } else {
                    animacaoDeLoad.pararAnimacao()
                    Toast.makeText(applicationContext, "O valor deve ser maior que 0", Toast.LENGTH_LONG).show()
                }
            } else {
                animacaoDeLoad.pararAnimacao()
                Toast.makeText(applicationContext, "Selecione uma conta", Toast.LENGTH_LONG).show()
            }
        } else {
            animacaoDeLoad.pararAnimacao()
            Toast.makeText(applicationContext, "Digite o nome do registro", Toast.LENGTH_LONG).show()
        }
    }

    private fun alterarRegistro() {
        val usuarioId = autentificacao.currentUser!!.uid
        val nome = binding.nomeGuardado.text.toString()
        val conta = binding.spinner.selectedItem.toString()
        val desc = binding.descricao.text.toString()

        val registroMasp = hashMapOf(
            "nome" to nome,
            "conta" to conta,
            "descricao" to desc,
            "valor" to valorAtualFinal
        )

        firestore.collection("Usuarios").document(usuarioId)
            .collection("ValoresGuardado").document(registroId)
            .update(registroMasp as Map<String, Any>)
            .addOnSuccessListener {
                animacaoDeLoad.pararAnimacao()
                onBackPressed()
            }
            .addOnFailureListener{
                animacaoDeLoad.pararAnimacao()
                Toast.makeText(applicationContext, "Erro ao alterar", Toast.LENGTH_LONG).show()
            }
    }
}