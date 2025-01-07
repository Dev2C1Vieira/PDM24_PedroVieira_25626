package com.pedro.ecommerce.viewmodel.auth

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.pedro.ecommerce.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel que gere o estado de autenticação.
 * Interage com o AuthRepository para realizar operações de login, registo e logout.
 */
class AuthViewModel(private val authRepository: AuthRepository) : ViewModel() {

    // Estado que armazena o utilizador autenticado
    private val _authState = MutableStateFlow<FirebaseUser?>(null)
    val authState: StateFlow<FirebaseUser?> = _authState

    // Estado que armazena mensagens de erro
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    /**
     * Realiza o login do utilizador.
     * @param email Email do utilizador.
     * @param password Senha do utilizador.
     */
    fun login(email: String, password: String) {
        viewModelScope.launch {
            authRepository.login(email, password) { user, error ->
                if (user != null) {
                    _authState.value = user // Atualiza o estado com o utilizador autenticado
                } else {
                    _errorMessage.value = error // Atualiza o estado com a mensagem de erro
                }
            }
        }
    }

    /**
     * Regista um novo utilizador.
     * @param email Email do utilizador.
     * @param password Senha do utilizador.
     */
    fun registerUser(email: String, password: String, userName: String) {
        viewModelScope.launch {
            authRepository.registerUser(
                email = email,
                password = password,
                userName = userName
            ) { user, error ->
                if (user != null) {
                    // Sucesso no registo
                    Log.d("Auth", "Utilizador registado com sucesso: ${user.uid}")
                } else {
                    // Falha no registo
                    Log.e("Auth", "Erro ao registar utilizador: $error")
                }
            }
        }
    }

    /**
     * Retorna o id do utilizador logado
     * */
    fun getCurrentUser(): FirebaseUser? {
        return authRepository.getCurrentUser()
    }

    /**
     * Faz logout do utilizador.
     */
    fun logout() {
        authRepository.logout()
        _authState.value = null // Limpa o estado de autenticação
    }
}