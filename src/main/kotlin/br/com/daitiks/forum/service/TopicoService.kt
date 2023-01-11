package br.com.daitiks.forum.service

import br.com.daitiks.forum.dto.*
import br.com.daitiks.forum.excepction.NotFoundException
import br.com.daitiks.forum.mapper.TopicoFormMapper
import br.com.daitiks.forum.mapper.TopicoViewMapper
import br.com.daitiks.forum.model.Topico
import br.com.daitiks.forum.repository.TopicoRepository
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.stream.Collectors
import javax.persistence.EntityManager
import kotlin.collections.ArrayList

@EnableAutoConfiguration
@Service
class TopicoService(
    private val repository: TopicoRepository,
    //Injetando dependências, assim nçao precisa ficar instanciando a classe
    private val topicoViewMapper: TopicoViewMapper,
    private val topicoFormMapper: TopicoFormMapper,
    private val notFoundMessage: String = "Tópico não encontrado!",
    private val em: EntityManager
) {

    fun listar(
        nomeCurso: String?,
        paginacao: Pageable
    ): Page<TopicoView> {
        print(em)
        val topicos = if (nomeCurso == null){
            repository.findAll(paginacao)
        } else{
            repository.findByCursoNome(nomeCurso, paginacao)
        }

        return topicos.map {
            t -> topicoViewMapper.map(t)
        }
    }

    fun buscarPorId(id: Long): TopicoView {
        val topico = repository.findById(id).orElseThrow{NotFoundException(notFoundMessage)}
        return topicoViewMapper.map(topico)
    }

    fun cadastrar(form: NovoTopicoForm): TopicoView {
        val topico = topicoFormMapper.map(form)
        repository.save(topico)
        return topicoViewMapper.map(topico)
    }

    fun atualizar(form: AtualizacaoTopicoForm): TopicoView {
        val topico = repository.findById(form.id).orElseThrow{NotFoundException(notFoundMessage)}
        //Recupera o topico, e remove o topico antigo da lista e adiciona um novo
        //Passando as novas informações (form.) para ele e buscando as antigas que
        //não podem ser mudadas (topico.)
        topico.titulo = form.titulo
        topico.mensagem = form.mensagem

        return topicoViewMapper.map(topico)
    }

    fun deletar(id: Long) {
        repository.deleteById(id)
        /*
        *     val topico = topicos.stream().filter { t ->
            t.id == id
        }.findFirst().orElseThrow{NotFoundException(notFoundMessage)}
        //Minus remove o topico, e nós atualizamos a lista de topicos
        topicos = topicos.minus(topico)*/

    }

    fun relatorio(): List<TopicoPorCategoriaDto> {
        return repository.relatorio()
    }

    fun relatorioTopicosNaoRespondidos(): List<Topico> {
        return repository.topicosNaoRespondidos()
    }
}