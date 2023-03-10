package br.com.daitiks.forum.dto

import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size
import kotlin.math.min

data class AtualizacaoTopicoForm (
    @field:NotNull
    val id: Long,

    @field:NotEmpty
    @field:Size(min = 5, max =180)
    val titulo: String,

    @field:NotEmpty
    val mensagem: String
)
