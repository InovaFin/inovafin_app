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

class ValorReceber : AppCompatActivity() {

    private lateinit var binding: ActivityValorReceberBinding

    private lateinit var autentificacao: FirebaseAuth

    private lateinit var firestore: FirebaseFirestore

    private lateinit var receberReyclerView: RecyclerView

    private lateinit var receberArrayList: ArrayList<RegistroValorReceber>

    private lateinit var adapter: MyAdapterReceber
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityValorReceberBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        autentificacao = ConfiguraBd.Firebaseautentificacao()
        firestore = ConfiguraBd.Firebasefirestore()

        receberReyclerView = binding.listaReceber
        receberReyclerView.layoutManager = LinearLayoutManager(this)
        receberReyclerView.setHasFixedSize(true)

        receberArrayList = arrayListOf()
        adapter = MyAdapterReceber(receberArrayList) { registro ->
            Toast.makeText(applicationContext, "Click", Toast.LENGTH_LONG).show()
        }

        setupRecyclerView()
        resgatarDados()

        binding.icFechar.setOnClickListener {
            onBackPressed()
        }

        binding.btAdicionar.setOnClickListener {
            val i = Intent (this, AddReceber::class.java)
            startActivity(i)
        }

        binding.btExcluir.setOnClickListener {
            val i = Intent (this, ExcluirReceber::class.java)
            startActivity(i)
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
//                        val vencimento = document.getString("vencimento").toString()
                        val vencimento = "datinha"
                        val valor = document.getDouble("valor").toString()


                        if (nome != null) {
                            val registro = RegistroValorReceber(nome, vencimento, valor)
                            receberArrayList.add(registro)
                        }
                    }

                    // Notifica o adaptador que os dados foram alterados
                    adapter.notifyDataSetChanged()
                }
            }
    }
}