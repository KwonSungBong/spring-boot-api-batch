package com.example.demo.service;

import com.example.demo.entity.Program;
import com.example.demo.entity.ProgramResponseEntity;
import com.example.demo.entity.User;
import com.example.demo.repository.ProgramRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.text.MessageFormat;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ApiTestService2 {

    @Value("${api-url:https://jsonplaceholder.typicode.com/users?param1={0}&param2={1}&param3={2}}")
    private String apiUrl;

    @Autowired
    private ProgramRepository programRepository;

    private RestTemplate restTemplate;

    @PostConstruct
    public void init() {
        CloseableHttpClient httpClient
                = HttpClients.custom()
                .setSSLHostnameVerifier(new NoopHostnameVerifier())
                .build();
        HttpComponentsClientHttpRequestFactory requestFactory
                = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(httpClient);

        this.restTemplate = new RestTemplate(requestFactory);
    }

    public List<Program> findList(int size) {
        return programRepository.findList(size);
    }


    public List<ProgramResponseEntity> getProgramResponseEntityList(List<Program> programList) {
        return programList.stream().map(this::getProgramResponseEntity).collect(Collectors.toList());
    }

    private ProgramResponseEntity getProgramResponseEntity(Program program) {
        long startTime = System.currentTimeMillis();
        String requestUrl = MessageFormat.format(apiUrl, "param1","param2","param3");
        try {
            ResponseEntity<User[]> response = restTemplate.exchange(requestUrl, HttpMethod.GET, null, User[].class);
            return new ProgramResponseEntity(program, requestUrl, response.getStatusCode(), getElapsedSecond(startTime), response);
        } catch (final HttpClientErrorException e) {
            return new ProgramResponseEntity(program, requestUrl, e.getStatusCode(), getElapsedSecond(startTime), e);
        } catch (final HttpServerErrorException e) {
            return new ProgramResponseEntity(program, requestUrl, e.getStatusCode(), getElapsedSecond(startTime), e);
        }
    }

    private double getElapsedSecond(long startTime) {
        return ( System.currentTimeMillis() - startTime) / 1000.0;
    }

}
