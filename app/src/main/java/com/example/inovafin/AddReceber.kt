package com.example.inovafin

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.inovafin.Util.ConfiguraBd
import com.example.inovafin.databinding.ActivityAddReceberBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.Calendar

class AddReceber : AppCompatActivity() {

    private lateinit var binding: ActivityAddReceberBinding

    private lateinit var autentificacao: FirebaseAuth

    private lateinit var firestore: FirebaseFirestore

    private var timestamp: Timestamp? = null
    private var selectedDate: Calendar = Calendar.getInstance()
    private var contaAlterada: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddReceberBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        autentificacao = ConfiguraBd.Firebaseautentificacao()
        firestore = ConfiguraBd.Firebasefirestore()

        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            selectedDate.set(year, month, dayOfMonth)

            showTimePicker()

        }, year, month, day)

        // Defina uma data mínima (opcional)
        val minDate = Calendar.getInstance()
        minDate.set(2023, 0, 1) // Janeiro de 2023
        datePickerDialog.datePicker.minDate = minDate.timeInMillis

        resgatarContas()

        binding.icFechar.setOnClickListener {
            onBackPressed()
        }

        binding.btCalendario.setOnClickListener {
            datePickerDialog.show()
        }

        binding.criarRegistro.setOnClickListener {
            validarCampos()
        }
    }

    private fun showTimePicker() {
        val hour = selectedDate.get(Calendar.HOUR_OF_DAY)
        val minute = selectedDate.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
            selectedDate.set(Calendar.HOUR_OF_DAY, hourOfDay)
            selectedDate.set(Calendar.MINUTE, minute)
            selectedDate.set(Calendar.SECOND, 0)

            timestamp = Timestamp(selectedDate.time)

            // Exibir a data e hora selecionadas no TextView
            binding.txtData.text = SimpleDateFormat("dd/MM/yyyy 'às' HH:mm").format(selectedDate.time)
        }, hour, minute, true)

        timePickerDialog.show()
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

    private fun validarCampos() {

    }

    private fun adicionarRegistro() {
        val usuarioId = autentificacao.currentUser!!.uid

        val registroMasp = hashMapOf(
            "nome" to "Olx",
            "conta" to "Conta Corrente",
            "vencimento" to timestamp,
            "descricao" to "Bla bla bla",
            "valor" to 100
        )

        firestore.collection("Usuarios").document(usuarioId)
            .collection("ValoresReceber").document()
            .set(registroMasp)
            .addOnSuccessListener {
                Toast.makeText(applicationContext, "Boa Zero6", Toast.LENGTH_LONG).show()
            }
            .addOnFailureListener{
                Toast.makeText(applicationContext, "Rapa menó", Toast.LENGTH_LONG).show()
            }
    }
}