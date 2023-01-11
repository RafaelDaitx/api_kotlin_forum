package br.com.daitiks.forum.service

import br.com.daitiks.forum.model.Curso
import br.com.daitiks.forum.model.Usuario
import br.com.daitiks.forum.repository.UsuarioRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class UsuarioService(private val repository: UsuarioRepository) {

    fun buscarPorId(id: Long): Usuario {
        return repository.getOne(id)
    }
}