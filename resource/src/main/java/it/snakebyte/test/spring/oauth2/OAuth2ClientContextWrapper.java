package it.snakebyte.test.spring.oauth2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.token.AccessTokenRequest;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;

@Component
//@Scope(scopeName="prototype")
public class OAuth2ClientContextWrapper implements OAuth2ClientContext {

    @Autowired
    private ApplicationContext context;

    private OAuth2ClientContext getCurrentClientContext() {

        if(RequestContextHolder.getRequestAttributes() != null) {
            return (OAuth2ClientContext)context.getBean("sessionScopeClientContext");
        }
        else {
            return (OAuth2ClientContext)context.getBean("singletonScopeClientContext");
        }
    }

    @Override
    public OAuth2AccessToken getAccessToken() {
        return getCurrentClientContext().getAccessToken();
    }

    @Override
    public void setAccessToken(OAuth2AccessToken accessToken) {
        getCurrentClientContext().setAccessToken(accessToken);
    }

    @Override
    public AccessTokenRequest getAccessTokenRequest() {
        return getCurrentClientContext().getAccessTokenRequest();
    }

    @Override
    public void setPreservedState(String stateKey, Object preservedState) {
        getCurrentClientContext().setPreservedState(stateKey, preservedState);
    }

    @Override
    public Object removePreservedState(String stateKey) {
        return getCurrentClientContext().removePreservedState(stateKey);
    }



}
