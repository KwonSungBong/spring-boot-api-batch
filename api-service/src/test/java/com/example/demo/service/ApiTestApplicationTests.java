package com.example.demo.service;

import com.example.demo.entity.Program;
import com.example.demo.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApiTestApplicationTests {

    int size = 1000;

    @Autowired
    private ApiTestService apiTestService;

    @Test
    public void TEST1() {
//        Elapsed time: 643133
//        Elapsed time: 73588
//        Elapsed time: 72219

        long start = System.currentTimeMillis();

        Page<Program> programPage = apiTestService.findAll(0, size);

        Map<Program, User[]> result = apiTestService.getProgramUserMap(programPage);

        System.out.println("Elapsed time: " + (System.currentTimeMillis() - start));
    }

    @Test
    public void TEST2() {
//        Elapsed time: 19628

        long start = System.currentTimeMillis();

        Set<Program> programSet = apiTestService.findList(size);

        final Executor excetor = Executors.newFixedThreadPool(Math.min(programSet.size(), 30), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread t = new Thread(r);
                t.setDaemon(true);
                return t;
            }
        });

        List<CompletableFuture<User[]>> futures = programSet.stream()
                .map(program -> CompletableFuture.supplyAsync(() ->
                        apiTestService.getUserByProgram(program), excetor))
                .collect(Collectors.toList());

        List<User[]> resultList = futures.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());

        System.out.println("Elapsed time: " + (System.currentTimeMillis() - start));
    }

    @Test
    public void TEST3() {
//        Elapsed time: 23187

        long start = System.currentTimeMillis();

        Map<Program, User[]> result = apiTestService.getMap(size);

        System.out.println("Elapsed time: " + (System.currentTimeMillis() - start));
    }

    @Test
    public void TEST4() {
//        Elapsed time: 69733

        long start = System.currentTimeMillis();

        apiTestService.getTest(size);

        System.out.println("Elapsed time: " + (System.currentTimeMillis() - start));
    }

}
