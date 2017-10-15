package it.snakebyte.test.spring.oauth2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class SchedulerBean {

    private final static Logger log = LoggerFactory.getLogger(SchedulerBean.class);

    @Autowired
    private RestTemplate restTemplate;

    @Scheduled(fixedRate=10000)
    private void call() {

        log.info("CALL");
        try {
            String result = restTemplate.getForObject("http://resource:8080/resource/test", String.class);
            log.info("RESULT IS {}", result);
        } catch(Exception e) {
            log.error("ERRORE!",e);
        }
    }

}
