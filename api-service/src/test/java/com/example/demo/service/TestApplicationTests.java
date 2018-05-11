package com.example.demo.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestApplicationTests {

    @Autowired
    private TestService testService;

    @Test
    public void TEST1() {
        testService.test1();
    }

    @Test
    public void TEST2() {
        testService.test2();
    }

    @Test
    public void TEST3() {
        testService.test3();
    }

    @Test
    public void TEST4() throws Exception {
        testService.test4();
    }

}
