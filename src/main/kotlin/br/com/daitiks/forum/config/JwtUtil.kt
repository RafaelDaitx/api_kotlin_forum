package br.com.daitiks.forum.config

import br.com.daitiks.forum.service.UsuarioService
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.stereotype.Component
import java.lang.IllegalArgumentException
import java.util.*

@Component
class JwtUtil(
    private val usuarioService: UsuarioService
) {

    private val expiration : Long = 60000

    @Value("\${jwt.secret}")
    private lateinit var secret : String

    fun generateToken(username: String, authorities: MutableCollection<out GrantedAuthority>): String?{
        return Jwts.builder()
            .setSubject(username)
            .claim("role", authorities)
            .setExpiration(Date(System.currentTimeMillis() + expiration)) //Expiração do token
            .signWith(SignatureAlgorithm.HS512, secret.toByteArray()) //Tipo de assinatura do token
            .compact()
        //Gerando token JWT
        }

    fun isValid(jwt: String?) : Boolean{
        return try {
            Jwts.parser().setSigningKey(secret.toByteArray()).parseClaimsJws(jwt)
            true
        } catch (e: IllegalArgumentException) {
            false
        }
    }

    fun getAuthentication(jwt: String?) : Authentication {
        val username = Jwts.parser().setSigningKey(secret.toByteArray()).parseClaimsJws(jwt).body.subject
        val user = usuarioService.loadUserByUsername(username)
        return UsernamePasswordAuthenticationToken(username, user, null)
    }

}