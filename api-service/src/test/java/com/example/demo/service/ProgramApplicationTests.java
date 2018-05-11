package com.example.demo.service;

import com.example.demo.entity.Program;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProgramApplicationTests {

    int size = 1000;

    @Autowired
    private ProgramService programService;

    @Test
    public void TEST1() {
        int page = 0;
        int limit = 100;

        Page<Program> programPage = programService.findAll(0, 100);
//        programPage.nextPageable()

        System.out.println();
    }

}
