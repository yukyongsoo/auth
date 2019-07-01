package yuk.msa.auth

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.security.oauth2.provider.token.DefaultTokenServices
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore

@Configuration
class TokenConfig {
    @Bean
    fun tokenStore() = JwtTokenStore(accessTokenConverter())

    @Bean
    @Primary
    fun tokenService(): DefaultTokenServices {
        val tokenService = DefaultTokenServices()
        tokenService.setTokenStore(tokenStore())
        tokenService.setSupportRefreshToken(true)
        return tokenService
    }

    @Bean
    fun accessTokenConverter(): JwtAccessTokenConverter {
        val converter = JwtAccessTokenConverter()
        converter.setSigningKey("asdfasdfasdf")
        return converter
    }
}