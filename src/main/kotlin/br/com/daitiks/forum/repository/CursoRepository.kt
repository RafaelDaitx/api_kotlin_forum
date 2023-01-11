package br.com.daitiks.forum.repository

import br.com.daitiks.forum.model.Curso
import org.springframework.data.jpa.repository.JpaRepository

interface CursoRepository: JpaRepository<Curso, Long> {
}