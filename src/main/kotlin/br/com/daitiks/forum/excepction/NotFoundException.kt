package br.com.daitiks.forum.excepction

import java.lang.RuntimeException

class NotFoundException(message: String?) : RuntimeException(message) {
}