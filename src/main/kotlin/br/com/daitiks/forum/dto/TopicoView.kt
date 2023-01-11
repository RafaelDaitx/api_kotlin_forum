package br.com.daitiks.forum.dto

import br.com.daitiks.forum.model.StatusTopico
import java.time.LocalDateTime

data class TopicoView (
    val id: Long?,
    val titulo: String,
    val mensagem: String,
    val status: StatusTopico,
    val dataCriacao: LocalDateTime
)

//Form é um DTO que chega, e View é um DTO que sai
