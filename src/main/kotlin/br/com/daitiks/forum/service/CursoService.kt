package br.com.daitiks.forum.service

import br.com.daitiks.forum.model.Curso
import br.com.daitiks.forum.repository.CursoRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class CursoService(private val repository: CursoRepository) {

    fun buscarPorId(id: Long): Curso{
        return repository.getOne(id)
    }
}
