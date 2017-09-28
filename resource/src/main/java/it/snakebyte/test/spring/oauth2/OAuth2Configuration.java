package it.snakebyte.test.spring.oauth2;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.web.util.matcher.RequestMatcher;

@Configuration
@EnableResourceServer
public class OAuth2Configuration extends ResourceServerConfigurerAdapter {
    
    @Value("${security.oauth2.resource.id:RISORSA}")
    private String resourceId;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources
                .resourceId(resourceId);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.requestMatcher(new OAuthRequestedMatcher())
                .anonymous().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS).permitAll();
                /*.antMatchers("/api/hello").access("hasAnyRole('USER')")
                .antMatchers("/api/me").hasAnyRole("USER", "ADMIN")
                .antMatchers("/api/register").hasAuthority("ROLE_REGISTER");*/
    }

    private static class OAuthRequestedMatcher implements RequestMatcher {
        public boolean matches(HttpServletRequest request) {
            String auth = request.getHeader("Authorization");
            // Determine if the client request contained an OAuth Authorization
            boolean haveOauth2Token = (auth != null) && auth.startsWith("Bearer");
            boolean haveAccessToken = request.getParameter("access_token")!=null;
            return haveOauth2Token || haveAccessToken;
        }
    }
}
