package it.snakebyte.test.spring.oauth2;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.JdbcApprovalStore;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableAuthorizationServer
public class OAuth2Configuration extends AuthorizationServerConfigurerAdapter {

    @Value("${access_token.validity_period:10}")
    private int accessTokenValiditySeconds;

    @Value("${refresh_token.validity_period:3600}")
    private int refreshTokenValiditySeconds;

    @Autowired
    private DataSource dataSource;

    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;


    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.approvalStore(approvalStore())
                    .tokenStore(tokenStore())
                    .authenticationManager(authenticationManager)
                    .reuseRefreshTokens(true)
                    .accessTokenConverter(accessTokenConverter());
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        oauthServer.tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()");
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

        clients.inMemory()
                .withClient("client1")
                  .authorizedGrantTypes("client_credentials")
                  .secret("secret")
                  .scopes("read")
                  .authorities("SAMPLE")
                  .accessTokenValiditySeconds(accessTokenValiditySeconds)
                  .refreshTokenValiditySeconds(refreshTokenValiditySeconds)
                  .resourceIds("test")
                .and()
                .withClient("client2")
                .authorizedGrantTypes(
                        "password", "refresh_token")
                  .secret("secret")
                  .scopes("read", "write")
                  .accessTokenValiditySeconds(accessTokenValiditySeconds)
                  .refreshTokenValiditySeconds(refreshTokenValiditySeconds)
                .and()
                .withClient("client3")
                .authorizedGrantTypes(
                        "authorization_code","refresh_token")
                  .scopes("read", "write")
                  .redirectUris("http://localhost:9000")
                  .autoApprove(true)
                  .accessTokenValiditySeconds(accessTokenValiditySeconds)
                  .refreshTokenValiditySeconds(refreshTokenValiditySeconds);
    }

    @Bean
    public TokenStore tokenStore() {
        JwtTokenStore tokenStore = new MyJwtTokenStore(accessTokenConverter(), approvalStore());
        return tokenStore;
    }

    @Bean
    public ApprovalStore approvalStore() {
        return new JdbcApprovalStore(dataSource);
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey("123");
        return converter;
    }
}