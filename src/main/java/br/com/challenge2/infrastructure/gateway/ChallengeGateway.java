package br.com.challenge2.infrastructure.gateway;

import br.com.challenge2.infrastructure.exception.InternalServerErrorException;
import br.com.challenge2.infrastructure.exception.InvalidCepChallengeGatewayException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.stereotype.Component;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * Communicates with the challenge webservice, which provides the CEP validation service
 */

@Component
public class ChallengeGateway {

    private String challengeUrl = "localhost";
    private int challengePort = 9090;
    private String challengeCepUri = "/rs/cep/";

    public boolean validateCep(String cep) {
        DefaultHttpClient httpclient = new DefaultHttpClient();
        challengeUrl = "localhost";
        challengePort = 9090;
        HttpHost target = new HttpHost(challengeUrl, challengePort, "http");
        challengeCepUri = "/rs/cep/";
        HttpGet getRequest = new HttpGet(challengeCepUri + cep);
        HttpResponse httpResponse = null;

        try {
            httpResponse = httpclient.execute(target, getRequest);
        } catch (IOException e) {
            throw new InternalServerErrorException();
        }
        HttpEntity response = httpResponse.getEntity();

        int statusCode = httpResponse.getStatusLine().getStatusCode();

        if (statusCode == 200) {
            return true;
        } else {
            Map<String, Object> map = convertResponseJsonToMap(response);
            throw new InvalidCepChallengeGatewayException((String) map.get("code"), (String) map.get("message"));
        }
    }

    private Map<String, Object> convertResponseJsonToMap(HttpEntity response) {
        InputStream content = null;
        try {
            content = response.getContent();

            byte[] buffer = new byte[1024];
            int bytesRead = 0;
            BufferedInputStream bis = new BufferedInputStream(content);
            String jsonResponse = "";
            while ((bytesRead = bis.read(buffer)) != -1) {
                String s = new String(buffer, 0, bytesRead);
                jsonResponse += s;
            }

            ObjectMapper mapper = new ObjectMapper();

            Map<String, Object> map;

            map = mapper.readValue(jsonResponse, new TypeReference<Map<String, Object>>() {
            });
            return (Map<String, Object>) map.get("item");
        } catch (IOException e) {
            throw new InternalServerErrorException();
        }
    }
}
