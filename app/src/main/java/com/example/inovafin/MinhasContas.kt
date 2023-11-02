package com.example.inovafin

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.inovafin.Util.ConfiguraBd
import com.example.inovafin.Util.ContaBancaria
import com.example.inovafin.Util.MyAdapter
import com.example.inovafin.databinding.ActivityMinhasContasBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MinhasContas : AppCompatActivity() {

    private lateinit var binding: ActivityMinhasContasBinding

    private lateinit var autentificacao: FirebaseAuth

    private lateinit var firestore: FirebaseFirestore

    private lateinit var contaReyclerView: RecyclerView

    private lateinit var contaArrayList: ArrayList<ContaBancaria>

    private lateinit var adapter: MyAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMinhasContasBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        autentificacao = ConfiguraBd.Firebaseautentificacao()
        firestore = ConfiguraBd.Firebasefirestore()

        contaReyclerView = binding.listaContas
        contaReyclerView.layoutManager = LinearLayoutManager(this)
        contaReyclerView.setHasFixedSize(true)

        contaArrayList = arrayListOf()
        adapter = MyAdapter(contaArrayList) { conta ->
            resgatarId(conta.nome) { documentoId ->
                val i = Intent(this, ContaEscolhida::class.java)
                i.putExtra("contaId", documentoId)
                startActivity(i)
            }
        }

        setupRecyclerView()

        binding.btAddContas.setOnClickListener{
            val i = Intent(this, NovaConta::class.java)
            startActivity(i)
        }

        binding.icFechar.setOnClickListener {
            val i = Intent(this, Home::class.java)
            startActivity(i)
        }

//        binding.btExcluirConta.setOnClickListener {
//            val i = Intent(this, ExcluirConta::class.java)
//            startActivity(i)
//        }
    }

    private fun resgatarId(nome: String?, callback: (String) -> Unit) {
        val usuarioId = autentificacao.currentUser!!.uid

        firestore.collection("Usuarios").document(usuarioId)
            .collection("ContasBancarias")
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
                // Tratar erro, se necessário
            }
    }

    private fun resgatarDados() {

        val usuarioId = autentificacao.currentUser!!.uid

        firestore.collection("Usuarios").document(usuarioId)
            .collection("ContasBancarias")
            .addSnapshotListener { querySnapshot, error ->
                if (error != null) {
                    Toast.makeText(applicationContext, "Erro ao buscar dados: $error", Toast.LENGTH_LONG).show()
                }

                if (querySnapshot != null) {
                    contaArrayList.clear() // Limpa a lista antes de adicionar os novos dados

                    for (document in querySnapshot) {
                        val nome = document.getString("nome")

                        if (nome != null) {
                            val conta = ContaBancaria(nome)
                            contaArrayList.add(conta)
                        }
                    }

                    // Notifica o adaptador que os dados foram alterados
                    adapter.notifyDataSetChanged()
                }
            }
    }

    private fun setupRecyclerView() {
        contaReyclerView.adapter = adapter // Defina o adaptador no RecyclerView
    }

    override fun onStart() {
        super.onStart()
        val usuarioAuth = autentificacao.currentUser

        if (usuarioAuth != null) {
            resgatarDados()
        }
    }
}
