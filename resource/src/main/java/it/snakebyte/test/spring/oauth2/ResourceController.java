package it.snakebyte.test.spring.oauth2;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="resource")
public class ResourceController {

    @RequestMapping(path="/test", method=RequestMethod.GET)
    public String getResource() {
        return "RESOURCE";
    }
}
