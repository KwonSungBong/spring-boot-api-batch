package com.example.demo.service;

import com.example.demo.entity.Program;
import com.example.demo.entity.User;
import com.example.demo.repository.ProgramRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class TestService {

    @Autowired
    private ProgramRepository programRepository;

    public void test1() {
        Iterable<Program> programIterable = programRepository.findAll();

        programIterable.iterator().forEachRemaining(program ->
                System.out.println("D : " + program.getId())
        );

        System.out.println("TSET");
    }

    public void test2() {
        RestTemplate restTemplate = new RestTemplate();

        String url = "https://jsonplaceholder.typicode.com/users";
        User[] response = restTemplate.getForObject(url, User[].class);

        log.debug("result : {}", "TSET");
    }

    public void test3() {
        RestTemplate restTemplate = new RestTemplate();

        String url = "https://jsonplaceholder.typicode.com/users";
        String response = restTemplate.getForObject(url, String.class);

        List<Map<Object,Object>> map = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();

        try {
            //convert JSON string to Map
            map = mapper.readValue(response, new TypeReference<List<Map<Object,Object>>>(){});
        } catch (Exception e) {
            log.info("Exception converting {} to map", response, e);
        }

        log.debug("result : {}", "TSET");
    }

    public void test4() throws Exception {
        String url = "https://jsonplaceholder.typicode.com/users";

        SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, (certificate, authType) -> true).build();
        CloseableHttpClient client = HttpClients.custom().setSSLContext(sslContext).setSSLHostnameVerifier(new NoopHostnameVerifier()).build();
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("Accept", "application/json");

        String response = IOUtils.toString(client.execute(httpGet).getEntity().getContent(), StandardCharsets.UTF_8.name());

        ObjectMapper mapper = new ObjectMapper();
        User[] responseUsers = mapper.readValue(response, new TypeReference<User[]>(){});

        log.debug("result : {}", "TSET");
    }

}
