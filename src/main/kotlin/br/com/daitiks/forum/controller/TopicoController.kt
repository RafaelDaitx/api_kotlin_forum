package br.com.daitiks.forum.controller

import br.com.daitiks.forum.dto.AtualizacaoTopicoForm
import br.com.daitiks.forum.dto.NovoTopicoForm
import br.com.daitiks.forum.dto.TopicoPorCategoriaDto
import br.com.daitiks.forum.dto.TopicoView
import br.com.daitiks.forum.model.Topico
import br.com.daitiks.forum.service.TopicoService
import org.hibernate.criterion.Order.desc
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.domain.Sort.Order.desc
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder
import javax.validation.Valid

//RestController devolve e envia dados
//Controller é para carregagamento de paginas web
@RestController
@RequestMapping("/topicos")
class TopicoController(private val service: TopicoService) {
        //Injeta o Service aqui dentro ^

    @GetMapping
    @Cacheable("topicosCache")
    fun listar(
            @RequestParam(required = false) nomeCurso: String?,
            @PageableDefault(size = 5, sort = ["dataCriacao"], direction = Sort.Direction.DESC) paginacao: Pageable
        ): Page<TopicoView>{
        return service.listar(nomeCurso, paginacao)
    }

    @GetMapping("/{id}")
    fun buscarPorId(@PathVariable id: Long): TopicoView{
        return service.buscarPorId(id)
    }

    @PostMapping
    @Transactional
    @CacheEvict(value = ["topicosCache"], allEntries = true)  //Limpar cache especificado
     fun cadastrar(
        @RequestBody @Valid form: NovoTopicoForm,
        uriBuilder: UriComponentsBuilder
    ): ResponseEntity<TopicoView>{
        val topicoView = service.cadastrar(form)
        //o uriBuilder, o Spring me manda qual a url que está sendo usado para passar o caminho completo,
        //para quando estiver em produção, pegar o endereço correto (www.url.com/topicos/id)
        val uri = uriBuilder.path("/topicos/${topicoView.id}").build().toUri()
        return ResponseEntity.created(uri).body(topicoView)
    }

    @PutMapping
    @Transactional
    @CacheEvict(value = ["topicosCache"], allEntries = true)  //Limpar cache especificado
    fun atualizar(@RequestBody @Valid form: AtualizacaoTopicoForm): ResponseEntity<TopicoView>{
        val topicoView = service.atualizar(form)
        return ResponseEntity.ok(topicoView)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Transactional
    @CacheEvict(value = ["topicosCache"], allEntries = true)  //Limpar cache especificado
    fun deletar(@PathVariable id: Long){
        service.deletar(id)
    }

    @GetMapping("/relatorio")
    fun relatorio(): List<TopicoPorCategoriaDto> {
        return service.relatorio()
    }

    @GetMapping("/relatorioNaoRespondido")
    fun relatorioTopicosNaoRespondidos(): List<Topico> {
        return service.relatorioTopicosNaoRespondidos()
    }
}