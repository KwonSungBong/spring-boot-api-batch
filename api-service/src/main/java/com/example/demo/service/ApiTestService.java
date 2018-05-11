package com.example.demo.service;

import com.example.demo.entity.Program;
import com.example.demo.entity.User;
import com.example.demo.repository.ProgramRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.text.MessageFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Service
public class ApiTestService {

    @Value("${api-url:https://jsonplaceholder.typicode.com/users?param1={0}&param2={1}&param3={2}}")
    private String apiUrl;

    @Autowired
    private ProgramRepository programRepository;

    @Autowired
    private RestTemplate restTemplate = new RestTemplate();

    public Set<Program> findByGrade(int size, int grade) {
        return programRepository.findByGrade(size, grade);
    }

    public Set<Program> findList(int size) {
        return programRepository.findSet(size);
    }

    public Page<Program> findAll(int page, int limit) {
        return programRepository.findAll(PageRequest.of(page, limit));
    }

    public Iterable<Program> findAll() {
        return programRepository.findAll();
    }

    public Map<Program, User[]> getProgramUserMap(Page<Program> programPage) {
        Map<Program, User[]> programMap = new ConcurrentHashMap<>();

        StreamSupport.stream(programPage.getContent().spliterator(), true).forEach(program -> {
                String requestUrl = MessageFormat.format(apiUrl, "param1","param2","param3");

                try {
                    User[] response = restTemplate.getForObject(requestUrl, User[].class);
                    programMap.put(program, response);
                } catch (Exception e) {
                    log.info("get http error : " + requestUrl + " : " + e);
                }
        });

        return programMap;
    }

    public User[] getUserByProgram(Program program) {
        String requestUrl = MessageFormat.format(apiUrl, "param1","param2","param3");

        try {
            User[] response = restTemplate.getForObject(requestUrl, User[].class);
            return response;
        } catch (Exception e) {
            log.info("get http error : " + requestUrl + " : " + e);
            return new User[0];
        }
    }

    @Transactional(readOnly = true)
    public Map<Program, User[]> getMap(int size) {
        Map<Program, User[]> programMap = new ConcurrentHashMap<>();

        final Executor excetor = Executors.newFixedThreadPool(30, new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread t = new Thread(r);
                t.setDaemon(true);
                return t;
            }
        });

        List<CompletableFuture<User[]>> futures = programRepository.findStream(size).
                map(program -> CompletableFuture.supplyAsync(() ->
                        getUserByProgram(program), excetor))
                .collect(Collectors.toList());

        List<User[]> resultList = futures.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());

        return programMap;
    }

    @Transactional(readOnly = true)
    public void getTest(int size) {
        Set<CompletableFuture<List<User[]>>> futures = new HashSet<>();
        int page = 0;
        int limit = 100;

        final Executor excetor = Executors.newFixedThreadPool(30, new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread t = new Thread(r);
                t.setDaemon(true);
                return t;
            }
        });

        while (true) {
            Page<Program> programPage = programRepository.findAll(PageRequest.of(page, limit));
            CompletableFuture future = CompletableFuture.supplyAsync(() -> {
                programPage.getContent().forEach(program -> {
                    String requestUrl = MessageFormat.format(apiUrl, "param1","param2","param3");
                    try {
                        User[] response = restTemplate.getForObject(requestUrl, User[].class);
                    } catch (Exception e) {
                        log.info("get http error : " + requestUrl + " : " + e);
                    }
                });
                return null;
            }, excetor);
            futures.add(future);

            if(programPage.getPageable().getPageNumber() == size/limit) break;
            page++;
        }

        List<List<User[]>> resultList = futures.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());

    }


}
