package com.example.inovafin

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.inovafin.Util.AnimacaoDeLoad
import com.example.inovafin.Util.ConfiguraBd
import com.example.inovafin.Util.MyAdapterReceber
import com.example.inovafin.Util.MyAdapterSaldoGReceber
import com.example.inovafin.Util.RegistroSaldoGReceber
import com.example.inovafin.Util.RegistroValorReceber
import com.example.inovafin.databinding.ActivitySaldoGeralReceberBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Locale

class SaldoGeralReceber : AppCompatActivity() {

    private lateinit var binding: ActivitySaldoGeralReceberBinding

    private lateinit var autentificacao: FirebaseAuth

    private lateinit var firestore: FirebaseFirestore

    private lateinit var animacaoDeLoad: AnimacaoDeLoad

    private lateinit var receberReyclerView: RecyclerView

    private lateinit var receberArrayList: ArrayList<RegistroSaldoGReceber>

    private lateinit var adapter: MyAdapterSaldoGReceber

    private var numberFormat = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))

    private var saldo: Double = 0.0

    private val itensSelecionados: MutableList<RegistroValorReceber> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySaldoGeralReceberBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        autentificacao = ConfiguraBd.Firebaseautentificacao()
        firestore = ConfiguraBd.Firebasefirestore()
        animacaoDeLoad = AnimacaoDeLoad(binding.btAnimacao, binding.btText, this)

        saldo = intent.getDoubleExtra("saldo", 0.0)

        receberReyclerView = binding.listaReceber
        receberReyclerView.layoutManager = LinearLayoutManager(this)
        receberReyclerView.setHasFixedSize(true)

        receberArrayList = arrayListOf()
        adapter = MyAdapterSaldoGReceber(receberArrayList) { registro ->
            if (registro.isSelected) {
                // Adicione o item à lista de itens selecionados
                itensSelecionados.add(RegistroValorReceber(registro.valor))
            } else {
                // Remova o item da lista de itens selecionados, se necessário
                val itemRemover = RegistroValorReceber(registro.valor)
                itensSelecionados.remove(itemRemover)
            }
        }

        setupRecyclerView()
        resgatarDados()

        binding.icFechar.setOnClickListener {
            onBackPressed()
        }

        binding.btAdicionar.setOnClickListener {
            somarRegistros()
        }
    }

    private fun somarRegistros() {
        // Lista para armazenar os valores sem o símbolo da moeda
        val valoresSemSimbolo = mutableListOf<Double>()

        for (item in itensSelecionados) {
            // Verifica se item.valor não é nulo ou vazio
            item.nome?.let { valor ->
                // Reverta a formatação antes de adicionar à lista
                val valorSemSimbolo = valor.replace(NumberFormat.getCurrencyInstance().currency.symbol, "")
                val valorSemFormatacao = valorSemSimbolo.replace(Regex("[^\\d]"), "")
                valoresSemSimbolo.add((valorSemFormatacao.toDouble() / 100.0))
            }
        }

        // Somar os valores sem o símbolo da moeda
        val somaValores = valoresSemSimbolo.sum()

//        // Aqui você pode usar a soma dos valores conforme necessário
//        Toast.makeText(applicationContext, "Soma dos Valores: $somaValores", Toast.LENGTH_SHORT).show()
        somarComSaldo(somaValores)
    }

    private fun somarComSaldo(somaValores: Double) {
        val usuarioId = autentificacao.currentUser!!.uid

        val soma = saldo + somaValores

        val registroMasp = hashMapOf(
            "saldoGeral" to soma
        )

        firestore.collection("Usuarios").document(usuarioId)
            .collection("saldoGeralTemporario")
            .document("temporario")
            .update(registroMasp as Map<String, Any>)
            .addOnSuccessListener {
                Toast.makeText(applicationContext, "alteração realizada", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener{
                Toast.makeText(applicationContext, "alteração não realizada", Toast.LENGTH_SHORT).show()
            }
    }

    private fun setupRecyclerView() {
        receberReyclerView.adapter = adapter // Defina o adaptador no RecyclerView
    }

    private fun resgatarDados() {
        val usuarioId = autentificacao.currentUser!!.uid

        firestore.collection("Usuarios").document(usuarioId)
            .collection("ValoresReceber")
            .addSnapshotListener { querySnapshot, error ->
                if (error != null) {
                    Toast.makeText(applicationContext, "Erro ao buscar dados: $error", Toast.LENGTH_LONG).show()
                }

                if (querySnapshot != null) {
                    receberArrayList.clear() // Limpa a lista antes de adicionar os novos dados

                    for (document in querySnapshot) {
                        val nome = document.getString("nome")
                        val vencimento = document.getTimestamp("vencimento")
                        val vencimentoString = if (vencimento != null) {
                            val date = vencimento.toDate()
                            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                            dateFormat.format(date)
                        } else {
                            "" // Caso o timestamp seja nulo, não aparecerá nada
                        }

                        val valorResgatado = document.getDouble("valor")

                        val valorFormatado = numberFormat.format(valorResgatado)


                        if (nome != null) {
                            val registro = RegistroSaldoGReceber(nome, vencimentoString, valorFormatado)
                            receberArrayList.add(registro)
                        }
                    }

                    // Notifica o adaptador que os dados foram alterados
                    adapter.notifyDataSetChanged()
                }
            }
    }
}