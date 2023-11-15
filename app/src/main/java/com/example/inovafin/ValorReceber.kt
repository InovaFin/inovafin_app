package com.example.inovafin

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.inovafin.Util.ConfiguraBd
import com.example.inovafin.Util.MyAdapterReceber
import com.example.inovafin.Util.RegistroValorReceber
import com.example.inovafin.databinding.ActivityValorReceberBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Locale

class ValorReceber : AppCompatActivity() {

    private lateinit var binding: ActivityValorReceberBinding

    private lateinit var autentificacao: FirebaseAuth

    private lateinit var firestore: FirebaseFirestore

    private lateinit var receberReyclerView: RecyclerView

    private lateinit var receberArrayList: ArrayList<RegistroValorReceber>

    private lateinit var adapter: MyAdapterReceber

    private var numberFormat = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))

    private var valorTotalReceber: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityValorReceberBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        autentificacao = ConfiguraBd.Firebaseautentificacao()
        firestore = ConfiguraBd.Firebasefirestore()

        receberReyclerView = binding.listaPagar
        receberReyclerView.layoutManager = LinearLayoutManager(this)
        receberReyclerView.setHasFixedSize(true)

        receberArrayList = arrayListOf()
        adapter = MyAdapterReceber(receberArrayList) { registro ->
            resgatarId(registro.nome) { documentoId ->
                val i = Intent(this, RegistroReceber::class.java)
                i.putExtra("registroId", documentoId)
                startActivity(i)
            }
        }

        setupRecyclerView()
        resgatarDados()

        binding.icFechar.setOnClickListener {
            onBackPressed()
        }

        binding.btAdicionar.setOnClickListener {
            verificarContas()
        }
    }

    private fun verificarContas() {
        val usuarioId = autentificacao.currentUser!!.uid

        firestore.collection("Usuarios").document(usuarioId)
            .collection("ContasBancarias")
            .get()
            .addOnSuccessListener { documents ->
                // Verifica se há algum documento na coleção
                if (documents.size() > 0) {
                    val i = Intent(this, AddReceber::class.java)
                    startActivity(i)
                } else {
                    Toast.makeText(applicationContext, "Antes, adicione uma Conta Bancária", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun setupRecyclerView() {
        receberReyclerView.adapter = adapter // Defina o adaptador no RecyclerView
    }

    private fun resgatarId(nome: String?, callback: (String) -> Unit) {
        val usuarioId = autentificacao.currentUser!!.uid

        firestore.collection("Usuarios").document(usuarioId)
            .collection("ValoresReceber")
            .whereEqualTo("nome", nome)
            .get()
            .addOnSuccessListener { querySnapshot ->
                if (!querySnapshot.isEmpty) {
                    // Recupera o ID do primeiro documento correspondente
                    val documentSnapshot = querySnapshot.documents[0]
                    val documentoId = documentSnapshot.id
                    callback(documentoId)
                } else {
                    // Tratar caso em que nenhum documento corresponde
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(applicationContext, "Erro ao buscar dados: $e", Toast.LENGTH_LONG).show()
            }
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
                        if (valorResgatado != null) {
                            valorTotalReceber += valorResgatado
                        }
                        val valorFormatado = numberFormat.format(valorResgatado)


                        if (nome != null) {
                            val registro = RegistroValorReceber(nome, vencimentoString, valorFormatado)
                            receberArrayList.add(registro)
                        }
                    }

                    val valorTotalFormatado = numberFormat.format(valorTotalReceber)

                    binding.valorTotalPagar.text = valorTotalFormatado

                    // Notifica o adaptador que os dados foram alterados
                    adapter.notifyDataSetChanged()
                }
            }
    }

    override fun onBackPressed() {
        val i = Intent(this, Home::class.java)
        startActivity(i)
    }
}