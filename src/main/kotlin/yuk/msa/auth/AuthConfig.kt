package yuk.msa.auth

import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer



@Configuration
class AuthConfig(private val authenticationManager: AuthenticationManager,
                 private val userDetailsService: UserDetailsService) : AuthorizationServerConfigurerAdapter(){

    override fun configure(clients: ClientDetailsServiceConfigurer) {
        clients.inMemory()
                .withClient("msa")
                .secret(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode("aa"))
                .authorizedGrantTypes("refresh_token","password","client_credentials")
                .scopes("web","mobile")
    }

    override fun configure(server: AuthorizationServerSecurityConfigurer) {
        server.tokenKeyAccess("permitAll()")
                .checkTokenAccess("permitAll()")
                .allowFormAuthenticationForClients()
    }

    override fun configure(endpoints: AuthorizationServerEndpointsConfigurer) {
        endpoints.authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService)
    }
}