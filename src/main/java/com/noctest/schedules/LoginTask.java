package com.noctest.schedules;

import com.noctest.auth.AuthResponse;
import com.noctest.auth.TokenRepoIF;
import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.ws.rs.core.UriBuilder;

/**
 * Created by admin on 2/05/2016.
 */
@Component
public class LoginTask {
    private static final Logger log = LoggerFactory.getLogger(LoginTask.class);

    @Value("${pvkey}")
    private String apiKey;

    @Value("${sharedsecret}")
    private String sharedSecret;

    private TokenRepoIF tokenRepo;

    @Autowired
    public void settokenrepo(TokenRepoIF tokenrepo) {
        this.tokenRepo = tokenrepo;
    }

    public void work() {

//        String plainCreds = "l7xx3916da54a38940bc91c03187eb2f077e:cd6bf4ff815a48c48e26da4fe1ce7f24";
        String plainCreds = apiKey+":"+sharedSecret;
        byte[] plainCredsBytes = plainCreds.getBytes();
        byte[] base64CredsBytes = Base64.encodeBase64(plainCredsBytes);
        String base64Creds = new String(base64CredsBytes);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Creds);

        HttpEntity<String> request = new HttpEntity<String>(headers);

        String myURL = UriBuilder.fromUri("https://api.transport.nsw.gov.au/auth/oauth/v2/token")
                .queryParam("grant_type", "client_credentials")
                .queryParam("scope", "user")
                .build().toString();

        System.out.println("Calling " + myURL);
        RestTemplate restTemplate = new RestTemplate();

        try {
            ResponseEntity<AuthResponse> response = restTemplate.exchange(myURL, HttpMethod.POST, request, AuthResponse.class);
            AuthResponse resp = response.getBody();
            log.info(resp.toString());
            tokenRepo.setToken(resp.getAccess_token());
        } catch (Exception ex) {
            tokenRepo.setToken("");
            log.error("Unable to retreive bearer token " + ex.toString());
        }
    }
}
