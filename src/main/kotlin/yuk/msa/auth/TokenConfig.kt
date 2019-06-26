package yuk.msa.auth

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore

@Configuration
class TokenConfig {
    @Bean
    fun tokenStore() = JwtTokenStore()


}