package br.com.daitiks.forum.dto

import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

data class NovoTopicoForm (

        @field:NotEmpty(message = "Título deve ter entre 5 e 180 caracteres.")
        @Size(min = 5, max = 180)
        val titulo: String,

        @field:NotEmpty(message = "Mensagem não pode ser em branco.")
        val mensagem: String,

        @field:NotNull
        val idCurso: Long,

        @field:NotNull
        val idAutor: Long
)

//Form é um DTO que chega, e View é um DTO que sai
