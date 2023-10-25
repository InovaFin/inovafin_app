package com.example.inovafin.Util

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

object UsuarioAltenticado {
    fun usuarioLogado(): FirebaseUser? {
        val usuario = ConfiguraBd.Firebaseautentificacao()
        return usuario.currentUser
    }
}