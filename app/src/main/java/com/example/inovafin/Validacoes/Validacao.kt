package com.example.inovafin.Validacoes

import android.util.Patterns

class Validacao {
    companion object {
        fun validarEmail(email: String): Boolean {
            val pattern = Patterns.EMAIL_ADDRESS
            return pattern.matcher(email).matches()
        }

        fun validarNome(nome: String): Boolean {
            return nome.isNotEmpty() && nome.length >= 3
        }

        fun validarSenha(senha: String?, confirmSenha: String?): Int? {
            if (senha != null && confirmSenha != null) {
                if (senha.length >= 8) {
                    if (senha == confirmSenha) {
                        return null // Senha válida
                    } else {
                        return 1 // Senhas não coincidem
                    }
                } else {
                    return 2 // Senha muito curta
                }
            } else {
                return 3 // Senha ou confirmação de senha nulas
            }
        }
    }
}
