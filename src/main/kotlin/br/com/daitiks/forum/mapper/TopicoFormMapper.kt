package br.com.daitiks.forum.mapper

import br.com.daitiks.forum.dto.NovoTopicoForm
import br.com.daitiks.forum.model.Topico
import br.com.daitiks.forum.service.CursoService
import br.com.daitiks.forum.service.UsuarioService
import org.springframework.stereotype.Component

@Component
class TopicoFormMapper(
    //Injetando dependências, assim nçao precisa ficar instanciando a classe
    private var cursoService: CursoService,
    private var usuarioService: UsuarioService,
): Mapper<NovoTopicoForm, Topico> {

    override fun map(t: NovoTopicoForm): Topico {
        return Topico(
            titulo = t.titulo,
            mensagem = t.mensagem,
            curso = cursoService.buscarPorId(t.idCurso),
            autor = usuarioService.buscarPorId(t.idAutor)
        )
    }

}
