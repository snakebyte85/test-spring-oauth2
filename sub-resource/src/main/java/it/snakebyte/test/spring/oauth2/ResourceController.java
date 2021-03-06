package it.snakebyte.test.spring.oauth2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="resource")
public class ResourceController {

    private final static Logger log = LoggerFactory.getLogger(ResourceController.class);

    @RequestMapping(path="/test", method=RequestMethod.GET)
    public String getResource() {
        log.info("THE LOGGED USER IS {}",SecurityContextHolder.getContext().getAuthentication());

        OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();

        Jwt jwt = JwtHelper.decode(details.getTokenValue());

        log.info("THE ENCODED TOKEN IS {}, CLAIMS {}", details.getTokenValue(), jwt.getClaims());

        log.info(jwt.getEncoded());

        return "SUB-RESOURCE";
    }
}
