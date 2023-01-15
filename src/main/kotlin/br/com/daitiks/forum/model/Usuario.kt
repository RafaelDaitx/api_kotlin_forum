package br.com.daitiks.forum.model

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.management.monitor.StringMonitor
import javax.persistence.*

@Entity
data class Usuario (
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val nome: String,
    val email: String,
    val password: String,

    @JsonIgnore //Não mostrar lista de Roles para o usuario
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "usuario.role")
    val role:List<Role> = mutableListOf()
    )
