package yuk.msa.auth

import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer
import org.springframework.security.oauth2.provider.token.AccessTokenConverter
import org.springframework.security.oauth2.provider.token.TokenStore

@Configuration
class AuthConfig(private val authenticationManager: AuthenticationManager,
                 private val userDetailsService: UserDetailsService,
                 private val tokenStore: TokenStore,
                 private val accessTokenConverter: AccessTokenConverter) : AuthorizationServerConfigurerAdapter() {

    override fun configure(clients: ClientDetailsServiceConfigurer) {
        clients.inMemory()
                .withClient("msa")
                .secret(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode("aa"))
                .authorizedGrantTypes("refresh_token", "password", "client_credentials")
                .scopes("web", "mobile")
    }

    override fun configure(server: AuthorizationServerSecurityConfigurer) {
        server.tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()")
                .allowFormAuthenticationForClients()
    }

    override fun configure(endpoints: AuthorizationServerEndpointsConfigurer) {
        endpoints
                .authenticationManager(authenticationManager)
                .tokenStore(tokenStore)
                .userDetailsService(userDetailsService)
                .accessTokenConverter(accessTokenConverter)
    }
}