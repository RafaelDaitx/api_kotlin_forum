package br.com.daitiks.forum.repository

import br.com.daitiks.forum.dto.TopicoPorCategoriaDto
import br.com.daitiks.forum.model.Topico
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query


interface TopicoRepository: JpaRepository<Topico, Long> {


    fun findByCursoNome(nomeCurso :String, paginacao: Pageable): Page<Topico>

    @Query("SELECT new br.com.daitiks.forum.dto.TopicoPorCategoriaDto(curso.categoria, count(t)) FROM Topico t JOIN t.curso curso GROUP BY curso.categoria")
    fun relatorio(): List<TopicoPorCategoriaDto>

    @Query("select t from Topico t where t.respostas is empty")
    fun topicosNaoRespondidos(): List<Topico>
}