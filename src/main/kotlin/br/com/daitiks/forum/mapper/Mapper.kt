package br.com.daitiks.forum.mapper

interface Mapper<T, U> {

    fun map(t: T): U

}
